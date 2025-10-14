package engine.core;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import engine.input.Input;
import engine.time.Time;
import engine.window.GlfwWindow;
import engine.window.Window;

public final class Engine {

    public interface Game {
        void onStart(EngineContext ctx);
        void onUpdate(EngineContext ctx, float dt);
        void onRender(EngineContext ctx);
        void onStop(EngineContext ctx);
    }

    public static void run(Game game, EngineConfig cfg) {
        Window window = null;
        try {
            window = new GlfwWindow(cfg.width, cfg.height, cfg.title, cfg.vsync);
            Input input = new Input(window.handle());
            Time time = new Time();
            EngineContext ctx = new EngineContext(window, input, time);

            game.onStart(ctx);

            glClearColor(0.10f, 0.12f, 0.15f, 1f);

            while (!window.shouldClose()) {
                window.pollEvents();

                if (input.keyDown(GLFW_KEY_ESCAPE)) window.setShouldClose(true);

                time.tick();
                game.onUpdate(ctx, time.dt());

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                game.onRender(ctx);

                window.swapBuffers();

                if (time.fps() > 0) {
                    window.setTitle(cfg.title + "  [" + time.fps() + " FPS]");
                }
            }

            game.onStop(ctx);
        } finally {
            if (window != null) window.close();
        }
    }

    private Engine() {}
}
