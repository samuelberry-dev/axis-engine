package engine.core;

import engine.api.VoxelGame;

public final class VoxelRuntime {
    private final AppSettings settings;
    private final VoxelGame game;
    private boolean running;

    VoxelRuntime(AppSettings settings, VoxelGame game) {
        this.settings = settings;
        this.game = game;
    }

    public AppSettings settings() { return settings; }
    public boolean isRunning() { return running; }

    void markRunning(boolean r) { this.running = r; }
}
