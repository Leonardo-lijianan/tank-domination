package database;

import com.healthmarketscience.jackcess.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameRecordDAO {
    private static final String DB_PATH = "TGame.accdb";
    private static final String TABLE_NAME = "GameRecord";

    public GameRecordDAO() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            ensureDatabaseExists();
        } catch (ClassNotFoundException e) {
            System.err.println("UCanAccess驱动未找到: " + e.getMessage());
        }
    }

    private String getConnectionUrl() {
        return "jdbc:ucanaccess://" + new File(DB_PATH).getAbsolutePath();
    }

    private void ensureDatabaseExists() {
        File dbFile = new File(DB_PATH);
        if (!dbFile.exists()) {
            try {
                // Use Jackcess to create a real Access database
                Database db = DatabaseBuilder.create(Database.FileFormat.V2010, dbFile);
                TableBuilder tableBuilder = new TableBuilder(TABLE_NAME)
                        .addColumn(new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true))
                        .addColumn(new ColumnBuilder("PlayerName", DataType.TEXT).setLength(50))
                        .addColumn(new ColumnBuilder("Score", DataType.LONG))
                        .addColumn(new ColumnBuilder("RecordDate", DataType.SHORT_DATE_TIME));
                tableBuilder.toTable(db);
                db.close();
                System.out.println("数据库创建成功: " + dbFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("创建数据库失败: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("数据库已存在: " + dbFile.getAbsolutePath());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getConnectionUrl());
    }

    public List<Object[]> getTopRecords(int limit) {
        List<Object[]> records = new ArrayList<>();
        String sql = "SELECT TOP " + limit + " PlayerName, Score, RecordDate FROM " + TABLE_NAME + " ORDER BY Score DESC";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                records.add(new Object[]{
                        rs.getString("PlayerName"),
                        rs.getInt("Score"),
                        rs.getDate("RecordDate")
                });
            }
        } catch (SQLException e) {
            System.err.println("查询记录失败: " + e.getMessage());
        }
        return records;
    }

    public boolean isNewRecord(int score) {
        String sql = "SELECT COUNT(*) as cnt FROM " + TABLE_NAME;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next() && rs.getInt("cnt") == 0) {
                return true;
            }
        } catch (SQLException e) {
            return true;
        }

        String sql2 = "SELECT MIN(Score) as MinScore FROM (SELECT TOP 10 Score FROM " + TABLE_NAME + " ORDER BY Score DESC)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql2)) {
            if (rs.next()) {
                int minScore = rs.getInt("MinScore");
                return score > minScore;
            }
            return true;
        } catch (SQLException e) {
            return true;
        }
    }

    public void saveRecord(String playerName, int score) {
        String sql = "INSERT INTO " + TABLE_NAME + " (PlayerName, Score, RecordDate) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("保存记录失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
