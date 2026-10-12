import java.awt.Color;
import java.awt.Font;

public final class Style {

    private Style() {}

    // --- kolory ---
    public static final Color BG = new Color(28, 18, 46);
    public static final Color TITLE = Color.WHITE;
    public static final Color BODY_BG = new Color(0, 0, 0, 40);
    public static final Color BODY_BORDER = new Color(120, 70, 150);

    public static final Color TILE_BG = new Color(44, 30, 66);
    public static final Color TILE_BG_ACTIVE = new Color(120, 70, 150);
    public static final Color TILE_BORDER = new Color(80, 60, 100);
    public static final Color TILE_BORDER_ACTIVE = new Color(255, 120, 200);
    public static final Color TILE_LABEL = new Color(170, 150, 190);
    public static final Color TILE_LABEL_ACTIVE = new Color(120, 220, 255);
    public static final Color TILE_ITEM = new Color(245, 235, 250);

    // --- fonty ---
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 48);
    public static final Font FONT_TILE_LABEL = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_TILE_ITEM = new Font("SansSerif", Font.BOLD, 22);

    public static final String[] TILE_ORDER = {"okrycie", "gora", "dol", "dodatki", "buty"};

    public static String tileLabel(String cat) {
        return switch (cat) {
            case "gora" -> "GÓRA";
            case "dol" -> "DÓŁ";
            case "okrycie" -> "OKRYCIE";
            case "buty" -> "BUTY";
            case "dodatki" -> "DODATKI";
            default -> cat.toUpperCase();
        };
    }
}