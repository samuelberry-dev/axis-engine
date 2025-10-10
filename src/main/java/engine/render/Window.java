package engine.render;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public final class Window implements AutoCloseable {
    private long handle;

    public void create(int width, int height, String title, boolean vSync) {
        if (!glfwInit()) throw new IllegalStateException("GLFW init failed");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL) {
            glfwTerminate();
            throw new IllegalStateException("Window creation failed");
        }

        // Center on primary monitor
        long monitor = glfwGetPrimaryMonitor();
        var vidmode = glfwGetVideoMode(monitor);
        if (vidmode != null) {
            int x = (vidmode.width() - width) / 2;
            int y = (vidmode.height() - height) / 2;
            glfwSetWindowPos(handle, x, y);
        }

        glfwMakeContextCurrent(handle);
        GL.createCapabilities();

        glfwSwapInterval(vSync ? 1 : 0); // vsync
        glfwShowWindow(handle);

        // ESC to close
        glfwSetKeyCallback(handle, (win, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(win, true);
            }
        });
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(handle);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void clear(float r, float g, float b, float a) {
        GL11.glViewport(0, 0, getWidth(), getHeight());
        GL11.glClearColor(r, g, b, a);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void swap() { glfwSwapBuffers(handle); }

    public int getWidth() {
        int[] w = new int[1], h = new int[1];
        glfwGetFramebufferSize(handle, w, h);
        return w[0];
    }

    public int getHeight() {
        int[] w = new int[1], h = new int[1];
        glfwGetFramebufferSize(handle, w, h);
        return h[0];
    }

    @Override
    public void close() {
        if (handle != NULL) {
            glfwDestroyWindow(handle);
            handle = NULL;
        }
        glfwTerminate();
    }
}
