import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class Calibration {

    public record Layout(double x, double y, double scale) {}

    private static final Path LAYOUT_FILE = Path.of("data", "layout.json");

    private static final Map<String, Layout> DEFAULTS = Map.of(
            "dodatki", new Layout(0.50, 0.72, 1.00),
            "dol", new Layout(0.50, 0.66, 1.00),
            "buty", new Layout(0.50, 0.93, 1.00),
            "gora", new Layout(0.50, 0.40, 1.00),
            "okrycie", new Layout(0.50, 0.42, 1.05)
    );

    private static final Layout FALLBACK = new Layout(0.50, 0.50, 1.00);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Type MAP_TYPE = new TypeToken<Map<String, Layout>>() {}.getType();

    private final Map<String, Layout> layout = new TreeMap<>();

    public Calibration() {
        load();
    }

    public void load() {
        layout.clear();
        layout.putAll(DEFAULTS);
        if (!Files.exists(LAYOUT_FILE)) return;

        try (Reader reader = Files.newBufferedReader(LAYOUT_FILE, StandardCharsets.UTF_8)) {
            Map<String, Layout> data = GSON.fromJson(reader, MAP_TYPE);
            if (data == null) return;
            data.forEach((key, lay) -> {
                if (lay != null) layout.put(key, lay);
            });
        } catch (IOException | JsonParseException e) {
            System.err.println("Nie udalo sie wczytac " + LAYOUT_FILE + ": " + e.getMessage());
        }
    }

    public void save() {
        try {
            Files.createDirectories(LAYOUT_FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(LAYOUT_FILE, StandardCharsets.UTF_8)) {
                GSON.toJson(layout, MAP_TYPE, writer);
            }
        } catch (IOException e) {
            System.err.println("Nie udalo sie zapisac " + LAYOUT_FILE + ": " + e.getMessage());
        }
    }

    public Layout get(String category, String itemPath) {
        if (itemPath != null) {
            Layout own = layout.get(key(category, itemPath));
            if (own != null) return own;
        }
        return get(category);
    }

    public Layout get(String category) {
        return layout.getOrDefault(category, FALLBACK);
    }

    public boolean hasOwn(String category, String itemPath) {
        return itemPath != null && layout.containsKey(key(category, itemPath));
    }

    public void set(String category, String itemPath, Layout lay) {
        layout.put(key(category, itemPath), lay);
    }

    public static String key(String category, String itemPath) {
        int cut = Math.max(itemPath.lastIndexOf('/'), itemPath.lastIndexOf('\\'));
        return category + "/" + itemPath.substring(cut + 1);
    }
}