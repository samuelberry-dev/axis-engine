package engine.core;

import engine.api.VoxelGame;
import engine.voxels.World;

public final class VoxelRuntime {
    private final AppSettings settings;
    private final VoxelGame game;
    private final World world;
    private boolean running;

    VoxelRuntime(AppSettings settings, VoxelGame game, World world) {
        this.settings = settings;
        this.game = game;
        this.world = world;
    }

    public AppSettings settings() { return settings; }
    public World world() { return world; }
    public boolean isRunning() { return running; }
    void markRunning(boolean r) { this.running = r; }
}
