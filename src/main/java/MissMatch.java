import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import  java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissMatch extends JPanel {

    private final Wardrobe wardrobe = new Wardrobe();
    private final String[] tileOrder = {"okrycie", "gora", "dol", "dodatki", "buty"};
    private int activeTile = 0;
    private final Map<String, Integer> selection = new HashMap<>();

    public MissMatch() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKey(e.getKeyCode());
            }
        });
    }

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
            boolean active = (i == activeTile);

            g.setColor(active ? new Color(120, 70, 150) : new Color(44, 30, 66));
            g.fillRoundRect(x, y, tileW, tileH, 10, 10);

            g.setColor(active ? new Color(255, 120, 200) : new Color(80, 60, 100));
            g.drawRoundRect(x, y, tileW, tileH, 10, 10);

            g.setColor(active ? new Color(120, 220, 255) : new Color(170, 150, 190));
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            g.drawString(tileLabel(cat), x + 14, y + 26);

            g.setColor(new Color(245, 235, 250));
            g.setFont(new Font("SansSerif", Font.BOLD, 22));
            g.drawString(currentItemName(cat), x + 14, y + 58);
        }
    }

    private void handleKey(int key) {
        if (key == KeyEvent.VK_UP) changeTitle(-1);
        else if (key == KeyEvent.VK_DOWN) changeTitle(1);
        else if (key == KeyEvent.VK_LEFT) changeItem(-1);
        else if (key == KeyEvent.VK_RIGHT) changeItem(1);
        repaint();
    }

    private void changeTitle(int delta) {
        activeTile = (activeTile + delta + tileOrder.length) % tileOrder.length;
    }

    private void changeItem(int delta) {
        String cat = tileOrder[activeTile];
        int n = wardrobe.getItems(cat).size();
        int cur = selection.getOrDefault(cat, 0);
        selection.put(cat, (cur + delta + n) % n);
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

    private String currentItemName(String cat) {
        List<String> items = wardrobe.getItems(cat);
        int idx = selection.getOrDefault(cat, 0) % items.size();
        String path = items.get(idx);
        if (path == null) return "nie wybrano";
        String file = new File(path).getName();
        int dot = file.lastIndexOf('.');
        if (dot > 0) file = file.substring(0, dot);
        return file.replace('_', ' ');
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
        panel.requestFocusInWindow();
    }
}
