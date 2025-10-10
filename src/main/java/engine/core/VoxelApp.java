package engine.core;

import engine.api.VoxelGame;
import engine.voxels.World;
import engine.voxels.WorldImpl;

/** Entry point to launch the engine with a VoxelGame. */
public final class VoxelApp {
    private VoxelApp() {}

    public static VoxelRuntime launch(VoxelGame game, AppSettings settings) {
        if (game == null) throw new IllegalArgumentException("game == null");
        if (settings == null) throw new IllegalArgumentException("settings == null");

        World world = new WorldImpl(); // renderless world for now
        VoxelRuntime rt = new VoxelRuntime(settings, game, world);
        game.onStart();
        rt.markRunning(true);
        return rt;
    }

    public static void tickOnce(VoxelRuntime rt, float dt) {
        if (rt == null || !rt.isRunning()) return;
        rtTick(rt, dt);
    }

    public static void stop(VoxelRuntime rt) {
        if (rt == null || !rt.isRunning()) return;
        rt.markRunning(false);
        getGame(rt).onStop();
    }

    private static void rtTick(VoxelRuntime rt, float dt) {
        getGame(rt).onTick(dt);
        getGame(rt).onRender();
    }
    private static VoxelGame getGame(VoxelRuntime rt) {
        try {
            var f = VoxelRuntime.class.getDeclaredField("game");
            f.setAccessible(true);
            return (VoxelGame) f.get(rt);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to access game reference", e);
        }
    }
}
