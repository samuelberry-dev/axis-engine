package engine.render;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public final class RenderHarness {
    public static void main(String[] args) {
        try (Window window = new Window()) {
            window.create(1280, 720, "AxisEngine - Render Harness", true);

            String vs = Resources.readTextResource("/shaders/simple.vert");
            String fs = Resources.readTextResource("/shaders/simple.frag");
            try (ShaderProgram prog = new ShaderProgram(vs, fs)) {

                int vao = glGenVertexArrays();
                glBindVertexArray(vao);

                int vbo = glGenBuffers();
                glBindBuffer(GL_ARRAY_BUFFER, vbo);

                FloatBuffer verts = BufferUtils.createFloatBuffer(3 * 3);
                verts.put(new float[] {
                    -0.6f, -0.5f, 0.0f,
                     0.6f, -0.5f, 0.0f,
                     0.0f,  0.6f, 0.0f
                }).flip();

                glBufferData(GL_ARRAY_BUFFER, verts, GL_STATIC_DRAW);

                glEnableVertexAttribArray(0);
                glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0L);

                long start = System.nanoTime();

                while (!window.shouldClose()) {
                    float t = (System.nanoTime() - start) / 1_000_000_000.0f;

                    glClearColor(0.08f, 0.10f, 0.12f, 1.0f);
                    glClear(GL_COLOR_BUFFER_BIT);

                    float s = 0.5f + 0.5f * (float)Math.sin(t * 1.5);
                    glUseProgram(0); // be explicit; ShaderProgram.use() will set later
                    prog.use();

                    glBindVertexArray(vao);
                    glDrawArrays(GL_TRIANGLES, 0, 3);

                    glBindVertexArray(0);
                    glDisableVertexAttribArray(0);

                    window.swap();
                    window.pollEvents();

                    glEnableVertexAttribArray(0);
                }

                glBindBuffer(GL_ARRAY_BUFFER, 0);
                glBindVertexArray(0);
                glDeleteBuffers(vbo);
                glDeleteVertexArrays(vao);
            }
        }
    }
}

