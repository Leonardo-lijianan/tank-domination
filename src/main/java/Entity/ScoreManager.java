package Entity;

import java.util.HashMap;

public class ScoreManager {
    private static final HashMap<String, Integer> scoreMap;

    static {
        scoreMap = new HashMap<>();
        scoreMap.put("blue", 100);
        scoreMap.put("pink", 200);
        scoreMap.put("red", 300);
        scoreMap.put("green", 400);
        scoreMap.put("navy", 1000);
    }

    public static int getScore(String tankType) {
        return scoreMap.getOrDefault(tankType, 100);
    }

    public static String getTankTypeName(String color) {
        switch (color) {
            case "blue": return "浅蓝色坦克";
            case "pink": return "粉色坦克";
            case "red": return "红色坦克";
            case "green": return "绿色坦克";
            case "navy": return "深蓝色坦克";
            default: return "普通坦克";
        }
    }
}
