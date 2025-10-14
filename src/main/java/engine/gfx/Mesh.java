package engine.gfx;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import org.lwjgl.system.MemoryUtil;

public class Mesh implements AutoCloseable {
    private final int vao, vbo, ebo;
    private final int indexCount;

    private Mesh(int vao, int vbo, int ebo, int indexCount) {
        this.vao = vao; this.vbo = vbo; this.ebo = ebo; this.indexCount = indexCount;
    }

    public static Mesh coloredCube() {
        // Interleaved: position (3f) + color (3f)
        float[] verts = {
            //   x     y     z      r    g    b
            // Front
            -0.5f,-0.5f, 0.5f,   1,0,0,
             0.5f,-0.5f, 0.5f,   0,1,0,
             0.5f, 0.5f, 0.5f,   0,0,1,
            -0.5f, 0.5f, 0.5f,   1,1,0,
            // Back
            -0.5f,-0.5f,-0.5f,   1,0,1,
             0.5f,-0.5f,-0.5f,   0,1,1,
             0.5f, 0.5f,-0.5f,   1,1,1,
            -0.5f, 0.5f,-0.5f,   0.3f,0.3f,0.3f,
        };
        int[] idx = {
            // Front
            0,1,2, 2,3,0,
            // Right
            1,5,6, 6,2,1,
            // Back
            5,4,7, 7,6,5,
            // Left
            4,0,3, 3,7,4,
            // Bottom
            4,5,1, 1,0,4,
            // Top
            3,2,6, 6,7,3
        };

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        FloatBuffer vfb = MemoryUtil.memAllocFloat(verts.length);
        vfb.put(verts).flip();
        glBufferData(GL_ARRAY_BUFFER, vfb, GL_STATIC_DRAW);

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        IntBuffer ifb = MemoryUtil.memAllocInt(idx.length);
        ifb.put(idx).flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ifb, GL_STATIC_DRAW);

        int stride = 6 * Float.BYTES;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0L);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, 3L * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        MemoryUtil.memFree(vfb);
        MemoryUtil.memFree(ifb);

        return new Mesh(vao, vbo, ebo, idx.length);
    }

    public void draw() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0L);
        glBindVertexArray(0);
    }

    @Override public void close() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);
    }
}
