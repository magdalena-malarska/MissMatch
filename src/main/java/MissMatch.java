import javax.swing.*;
import java.awt.*;

public class MissMatch extends JPanel {

    private final Wardrobe wardrobe = new Wardrobe();
    private final String[] tileOrder = {"okrycie", "gora", "dol", "dodatki", "buty"};

    @Override
    protected void paintComponent(Graphics g) {
        super .paintComponent(g);
        g.setColor(new Color(28, 18, 46));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 48));
        g.drawString("Miss Match", 60, 80);

        int x = 620, tileW = 374, tileH = 80, gap = 11, startY = 90;

        for (int i = 0; i < tileOrder.length; i++) {
            int y = startY + i * (tileH + gap);
            String cat = tileOrder[i];

            g.setColor(new Color(44, 30, 66));
            g.fillRoundRect(x, y, tileW, tileH, 10, 10);

            g.setColor(new Color(80, 60, 100));
            g.drawRoundRect(x, y, tileW, tileH, 10, 10);

            g.setColor(new Color(170, 150, 190));
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            g.drawString(tileLabel(cat), x + 14, y + 28);
        }
    }

    private String tileLabel(String cat) {
        return switch (cat) {
            case "gora" -> "GÓRA";
            case "dol" -> "DÓŁ";
            case "okrycie" -> "OKRYCIE";
            case "buty" -> "BUTY";
            case "dodatki" -> "DODATKI";
            default -> cat.toUpperCase();
        };
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Miss Match");
        MissMatch panel = new MissMatch();
        panel.setPreferredSize(new Dimension(1024, 600));
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
