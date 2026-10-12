import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

// Wczytywanie obrazkow + cache + skalowanie z zachowaniem proporcji.
public class Images {

    private final Map<String, BufferedImage> cache = new HashMap<>();

    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e) {
            return null;
        }
    }

    public BufferedImage get(String path) {
        if (path == null) return null;
        return cache.computeIfAbsent(path, Images::load);
    }

    public static double fitRatio(BufferedImage img, int maxW, int maxH) {
        return Math.min((double) maxW / img.getWidth(), (double) maxH / img.getHeight());
    }

    public static Rectangle fitCentered(BufferedImage img, int areaX, int areaY, int areaW, int areaH) {
        double ratio = fitRatio(img, areaW, areaH);
        int w = (int) (img.getWidth() * ratio);
        int h = (int) (img.getHeight() * ratio);
        int x = areaX + (areaW - w) / 2;
        int y = areaY + (areaH - h) / 2;
        return new Rectangle(x, y, w, h);
    }
}