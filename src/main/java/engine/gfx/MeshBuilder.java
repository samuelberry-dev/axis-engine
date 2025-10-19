package engine.gfx;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import org.lwjgl.system.MemoryUtil;

public class MeshBuilder {
    private final List<Float> v = new ArrayList<>();
    private final List<Integer> i = new ArrayList<>();
    private int verts = 0;
    private static final int VTX_STRIDE = 8; // pos(3) + color(3) + uv(2)

    public MeshBuilder addQuad(
            float[] p0, float[] p1, float[] p2, float[] p3,
            float[] rgb, float[] uv0, float[] uv1, float[] uv2, float[] uv3) {

        int base = verts;

        // v0..v3
        putVertex(p0, rgb, uv0);
        putVertex(p1, rgb, uv1);
        putVertex(p2, rgb, uv2);
        putVertex(p3, rgb, uv3);

        // indices: (0,1,2) (2,3,0)
        i.add(base);   i.add(base+1); i.add(base+2);
        i.add(base+2); i.add(base+3); i.add(base);
        verts += 4;
        return this;
    }

    private void putVertex(float[] p, float[] c, float[] uv) {
        // pos
        v.add(p[0]); v.add(p[1]); v.add(p[2]);
        // color
        v.add(c[0]); v.add(c[1]); v.add(c[2]);
        // uv
        v.add(uv[0]); v.add(uv[1]);
    }

    public Mesh build() {
        float[] vf = new float[v.size()];
        for (int k = 0; k < vf.length; k++) vf[k] = v.get(k);
        int[] ii = new int[i.size()];
        for (int k = 0; k < ii.length; k++) ii[k] = i.get(k);

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer vfb = MemoryUtil.memAllocFloat(vf.length).put(vf).flip();
        glBufferData(GL_ARRAY_BUFFER, vfb, GL_STATIC_DRAW);

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        IntBuffer ifb = MemoryUtil.memAllocInt(ii.length).put(ii).flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ifb, GL_STATIC_DRAW);

        int stride = VTX_STRIDE * Float.BYTES;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0L);                  // aPos
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, 3L * Float.BYTES);    // aColor
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 6L * Float.BYTES);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        MemoryUtil.memFree(vfb);
        MemoryUtil.memFree(ifb);

        return new Mesh(vao, vbo, ebo, ii.length);
    }
}
