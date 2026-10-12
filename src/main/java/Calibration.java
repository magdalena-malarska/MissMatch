import java.util.HashMap;
import java.util.Map;

public class Calibration {

    public record Layout(double x, double y, double scale) {}

    private final Map<String, Layout> layout = new HashMap<>(Map.of(
            "dodatki", new Layout(0.50, 0.72, 1.00),
            "dol", new Layout(0.50, 0.66, 1.00),
            "buty", new Layout(0.50, 0.93, 1.00),
            "gora", new Layout(0.50, 0.40, 1.00),
            "okrycie", new Layout(0.50, 0.42, 1.05)
    ));

    public Layout get(String category) {
        return layout.get(category);
    }
}