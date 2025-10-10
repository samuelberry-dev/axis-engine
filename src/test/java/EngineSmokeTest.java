import engine.core.AppSettings;
import engine.core.VoxelApp;
import engine.core.VoxelRuntime;
import engine.api.VoxelGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EngineSmokeTest {
    @Test
    void lifecycleStartsAndStops() {
        VoxelGame game = new VoxelGame() {
            boolean started = false, stopped = false;
            public void onStart() { started = true; }
            public void onTick(float dt) {}
            public void onRender() {}
            public void onStop() { stopped = true; }

            boolean isStarted() { return started; }
            boolean isStopped() { return stopped; }
        };

        var settings = AppSettings.builder().title("Test").build();
        VoxelRuntime rt = VoxelApp.launch(game, settings);
        assertNotNull(rt);
        assertTrue(rt.isRunning(), "runtime should be marked running");

        // one tick pass (no-op)
        VoxelApp.tickOnce(rt, 1f / 60f);

        // stop
        VoxelApp.stop(rt);
        assertFalse(rt.isRunning(), "runtime should be stopped");
    }
}
