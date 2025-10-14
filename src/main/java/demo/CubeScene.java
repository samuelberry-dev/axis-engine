package demo;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import org.lwjgl.system.MemoryStack;

import engine.core.EngineContext;
import engine.gfx.Camera;
import engine.gfx.Mesh;
import engine.gfx.Shader;

public class CubeScene implements AutoCloseable {
    private Shader shader;
    private Mesh cube;
    private Camera camera;
    private int uMvpLoc;
    private float angle = 0f;

    public void init(EngineContext ctx) {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        shader = Shader.fromResources("shaders/basic.vert", "shaders/basic.frag");
        cube = Mesh.coloredCube();
        camera = new Camera();

        float aspect = Math.max(1, ctx.window().width()) / (float) Math.max(1, ctx.window().height());
        camera.setPerspective((float)Math.toRadians(60), aspect, 0.1f, 100f);
        camera.lookAt(0f, 0f, 3f, 0f, 0f, 0f);

        uMvpLoc = shader.loc("uMVP");
    }

    public void update(EngineContext ctx, float dt) {
        angle += dt * 0.9f;
    }

    public void render(EngineContext ctx) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Matrix4f model = new Matrix4f().rotateY(angle).rotateX(angle * 0.5f);

        Matrix4f mvp = new Matrix4f();
        camera.mvp(model, mvp);

        shader.use();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            mvp.get(fb);
            shader.setUniformMat4(uMvpLoc, fb);
        }
        cube.draw();
        Shader.unuse();
    }

    @Override public void close() {
        if (cube != null) cube.close();
        if (shader != null) shader.close();
    }
}
