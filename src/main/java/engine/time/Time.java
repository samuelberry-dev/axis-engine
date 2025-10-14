package engine.time;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {
    private double last = glfwGetTime();
    private float dt = 0f;
    private int frames = 0, fps = 0;
    private double acc = 0.0;

    public void tick() {
        double now = glfwGetTime();
        dt = (float)(now - last);
        last = now;
        frames++;
        acc += dt;
        if (acc >= 1.0) {
            fps = frames;
            frames = 0;
            acc -= 1.0;
        }
    }

    public float dt() { return dt; }
    public int fps() { return fps; }
}
