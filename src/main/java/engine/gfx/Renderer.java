package engine.gfx;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import org.lwjgl.system.MemoryStack;

public class Renderer {
    private int uMvpLoc = -1, uTexLoc = -1;

    public void initFor(Shader shader) {
        shader.use();
        uMvpLoc = shader.loc("uMVP");
        uTexLoc = shader.loc("uTex0");
        shader.setUniform1i(uTexLoc, 0); // bind sampler to texture unit 0
        Shader.unuse();
    }

    public void beginFrame(float r, float g, float b) {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glClearColor(r, g, b, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void draw(Mesh mesh, Shader shader, Matrix4f mvp) {
        shader.use();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            mvp.get(fb);
            shader.setUniformMat4(uMvpLoc, fb);
        }
        mesh.draw();
        Shader.unuse();
    }

    public void draw(Mesh mesh, Shader shader, Matrix4f mvp, Texture2D tex) {
        shader.use();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            mvp.get(fb);
            shader.setUniformMat4(uMvpLoc, fb);
        }
        tex.bind(0);
        mesh.draw();
        Texture2D.unbind();
        Shader.unuse();
    }
}
