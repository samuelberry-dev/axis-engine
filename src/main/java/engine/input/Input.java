package engine.input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class Input {
    private final long window;

    public Input(long window) { this.window = window; }

    public boolean keyDown(int key) {
        int s = glfwGetKey(window, key);
        return s == GLFW_PRESS || s == GLFW_REPEAT;
    }

    public double mouseX() {
        double[] x = new double[1], y = new double[1];
        glfwGetCursorPos(window, x, y);
        return x[0];
    }
    public double mouseY() {
        double[] x = new double[1], y = new double[1];
        glfwGetCursorPos(window, x, y);
        return y[0];
    }
}
