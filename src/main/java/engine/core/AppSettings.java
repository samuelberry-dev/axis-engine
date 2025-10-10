package engine.core;

public final class AppSettings {
    public final String title;
    public final int width;
    public final int height;
    public final boolean vSync;
    public final int targetFps;
    public final long worldSeed;

    private AppSettings(String title, int width, int height, boolean vSync, int targetFps, long worldSeed) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.targetFps = targetFps;
        this.worldSeed = worldSeed;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String title = "AxisEngine";
        private int width = 1280;
        private int height = 720;
        private boolean vSync = true;
        private int targetFps = 60;
        private long worldSeed = 0L;

        public Builder title(String t) { this.title = t; return this; }
        public Builder width(int w) { this.width = w; return this; }
        public Builder height(int h) { this.height = h; return this; }
        public Builder vSync(boolean v) { this.vSync = v; return this; }
        public Builder targetFps(int f) { this.targetFps = f; return this; }
        public Builder worldSeed(long s) { this.worldSeed = s; return this; }
        public AppSettings build() { return new AppSettings(title, width, height, vSync, targetFps, worldSeed); }
    }
}
