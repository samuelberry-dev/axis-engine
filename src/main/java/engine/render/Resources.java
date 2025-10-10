package engine.render;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

final class Resources {
    static String readTextResource(String path) {
        var in = Resources.class.getResourceAsStream(path.startsWith("/") ? path : "/" + path);
        if (in == null) throw new IllegalArgumentException("Resource not found: " + path);
        try (var r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed reading resource: " + path, e);
        }
    }

    private Resources() {}
}

