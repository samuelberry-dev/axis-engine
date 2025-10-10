package engine.core;

import engine.api.VoxelGame;

public final class DevHarness {
    public static void main(String[] args) {
        VoxelGame demo = new VoxelGame() {
            public void onStart() { System.out.println("onStart"); }
            public void onTick(float dt) { System.out.println("onTick " + dt); }
            public void onRender() { System.out.println("onRender"); }
            public void onStop() { System.out.println("onStop"); }
        };
        var settings = AppSettings.builder().title("AxisEngine DevHarness").build();
        var rt = VoxelApp.launch(demo, settings);
        VoxelApp.tickOnce(rt, 1.0f / 60f);
        VoxelApp.stop(rt);
    }
}
