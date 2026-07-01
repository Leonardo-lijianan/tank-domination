package ui;

import database.GameRecordDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GameRecordDialog extends JDialog {
    public GameRecordDialog() {
        setTitle("游戏纪录");
        setModal(true);
        setSize(500, 400);
        setLocationRelativeTo(null);
        initDialog();
    }

    private void initDialog() {
        setLayout(new BorderLayout());

        String[] columns = {"排名", "玩家姓名", "分数", "日期"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        GameRecordDAO dao = new GameRecordDAO();
        List<Object[]> records = dao.getTopRecords(10);
        for (int i = 0; i < records.size(); i++) {
            Object[] record = records.get(i);
            model.addRow(new Object[]{i + 1, record[0], record[1], record[2]});
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton closeBtn = new JButton("关闭");
        closeBtn.addActionListener(e -> dispose());
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }
}
