package engine.render;

public final class RenderHarness {
    public static void main(String[] args) {
        try (Window window = new Window()) {
            window.create(1280, 720, "AxisEngine - Render Harness", true);

            long start = System.nanoTime();
            while (!window.shouldClose()) {
                float t = (System.nanoTime() - start) / 1_000_000_000.0f;
                float r = 0.1f + 0.1f * (float)Math.sin(t * 0.9);
                float g = 0.2f + 0.2f * (float)Math.sin(t * 1.3);
                float b = 0.3f + 0.3f * (float)Math.sin(t * 1.7);

                window.clear(r, g, b, 1.0f);
                window.swap();
                window.pollEvents();
            }
        }
    }
}
