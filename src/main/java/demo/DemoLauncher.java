package demo;

import engine.core.Engine;
import engine.core.EngineConfig;
import engine.core.EngineContext;

public class DemoLauncher {
    public static void main(String[] args) {
        EngineConfig cfg = new EngineConfig();
        cfg.title = "AxisEngine — M1 Demo";
        Engine.run(new DemoGame(), cfg);
    }

    static class DemoGame implements Engine.Game {
        @Override public void onStart(EngineContext ctx) {
            System.out.println("Demo start");
        }
        @Override public void onUpdate(EngineContext ctx, float dt) {
        }
        @Override public void onRender(EngineContext ctx) {
        }
        @Override public void onStop(EngineContext ctx) {
            System.out.println("Demo stop");
        }
    }
}
