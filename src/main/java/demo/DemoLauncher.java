package demo;

import static org.lwjgl.opengl.GL11.glClearColor;

import engine.core.Engine;
import engine.core.EngineConfig;
import engine.core.EngineContext;

public class DemoLauncher {
    public static void main(String[] args) {
        EngineConfig cfg = new EngineConfig();
        cfg.title = "AxisEngine — M2 Cube";
        Engine.run(new DemoGame(), cfg);
    }

    static class DemoGame implements Engine.Game {
        private CubeScene scene;

        @Override public void onStart(EngineContext ctx) {
            glClearColor(0.10f, 0.12f, 0.15f, 1f);
            scene = new CubeScene();
            scene.init(ctx);
        }
        @Override public void onUpdate(EngineContext ctx, float dt) {
            scene.update(ctx, dt);
        }
        @Override public void onRender(EngineContext ctx) {
            scene.render(ctx);
        }
        @Override public void onStop(EngineContext ctx) {
            if (scene != null) scene.close();
        }
    }
}
