package engine.window;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RAW_MOUSE_MOTION;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwRawMouseMotionSupported;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.glViewport;

public class GlfwWindow implements Window {
    private long handle;
    private int width, height;

    public GlfwWindow(int width, int height, String title, boolean vsync) {
        this.width = width; this.height = height;

        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) throw new IllegalStateException("GLFW init failed");

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        handle = glfwCreateWindow(width, height, title, 0, 0);
        if (handle == 0) throw new IllegalStateException("Window creation failed");

        glfwMakeContextCurrent(handle);
        setVsync(vsync);
        GL.createCapabilities();

        glfwSetFramebufferSizeCallback(handle, (w, newW, newH) -> {
            this.width = newW; this.height = newH;
            glViewport(0, 0, newW, newH);
        });

        glfwSetWindowPos(handle, 100, 100);
        glfwShowWindow(handle);

        glViewport(0, 0, width, height);
    }

    @Override public long handle() { return handle; }
    @Override public int width() { return width; }
    @Override public int height() { return height; }
    @Override public boolean shouldClose() { return glfwWindowShouldClose(handle); }
    @Override public void setShouldClose(boolean v) { glfwSetWindowShouldClose(handle, v); }
    @Override public void pollEvents() { glfwPollEvents(); }
    @Override public void swapBuffers() { glfwSwapBuffers(handle); }
    @Override public void setVsync(boolean vsync) { glfwSwapInterval(vsync ? 1 : 0); }
    @Override public void setTitle(String title) { glfwSetWindowTitle(handle, title); }

    @Override
    public void setCursorDisabled(boolean disabled) {
        glfwSetInputMode(handle, GLFW_CURSOR, disabled ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
        if (glfwRawMouseMotionSupported()) {
            glfwSetInputMode(handle, GLFW_RAW_MOUSE_MOTION, disabled ? GLFW_TRUE : GLFW_FALSE);
        }
    }

    @Override
    public void setCursorHidden(boolean hidden) {
        glfwSetInputMode(handle, GLFW_CURSOR, hidden ? GLFW_CURSOR_HIDDEN : GLFW_CURSOR_NORMAL);
    }
    @Override public void close() {
        glfwDestroyWindow(handle);
        glfwTerminate();
    }
}
