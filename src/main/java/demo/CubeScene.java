package demo;

import org.joml.Matrix4f;

import engine.core.EngineContext;
import engine.gfx.Camera;
import engine.gfx.Mesh;
import engine.gfx.MeshBuilder;
import engine.gfx.Renderer;
import engine.gfx.Shader;

public class CubeScene implements AutoCloseable {
    private Shader shader;
    private Mesh cube;
    private Camera camera;
    private Renderer renderer;
    private float angle = 0f;

    public void init(EngineContext ctx) {
        shader = Shader.fromResources("shaders/basic.vert", "shaders/basic.frag");
        renderer = new Renderer();
        renderer.initFor(shader);

        camera = new Camera();
        float aspect = Math.max(1, ctx.window().width()) / (float) Math.max(1, ctx.window().height());
        camera.setPerspective((float)Math.toRadians(60), aspect, 0.1f, 100f);
        camera.lookAt(0f, 0f, 3f, 0f, 0f, 0f);

        // Build cube with MeshBuilder
        MeshBuilder mb = new MeshBuilder();
        float s = 0.5f;
        float[] red =  {1,0,0}, green={0,1,0}, blue={0,0,1}, yellow={1,1,0}, magenta={1,0,1}, cyan={0,1,1};
        float[] uv0={0,0}, uv1={1,0}, uv2={1,1}, uv3={0,1};

        // Front
        mb.addQuad(new float[]{-s,-s, s}, new float[]{ s,-s, s}, new float[]{ s, s, s}, new float[]{-s, s, s},
                   red, uv0, uv1, uv2, uv3);
        // Right
        mb.addQuad(new float[]{ s,-s, s}, new float[]{ s,-s,-s}, new float[]{ s, s,-s}, new float[]{ s, s, s},
                   green, uv0, uv1, uv2, uv3);
        // Back
        mb.addQuad(new float[]{ s,-s,-s}, new float[]{-s,-s,-s}, new float[]{-s, s,-s}, new float[]{ s, s,-s},
                   blue, uv0, uv1, uv2, uv3);
        // Left
        mb.addQuad(new float[]{-s,-s,-s}, new float[]{-s,-s, s}, new float[]{-s, s, s}, new float[]{-s, s,-s},
                   yellow, uv0, uv1, uv2, uv3);
        // Bottom
        mb.addQuad(new float[]{-s,-s,-s}, new float[]{ s,-s,-s}, new float[]{ s,-s, s}, new float[]{-s,-s, s},
                   magenta, uv0, uv1, uv2, uv3);
        // Top
        mb.addQuad(new float[]{-s, s, s}, new float[]{ s, s, s}, new float[]{ s, s,-s}, new float[]{-s, s,-s},
                   cyan, uv0, uv1, uv2, uv3);

        cube = mb.build();
    }

    public void update(EngineContext ctx, float dt) { angle += dt * 0.9f; }

    public void render(EngineContext ctx) {
        renderer.beginFrame(0.10f, 0.12f, 0.15f);

        Matrix4f model = new Matrix4f().rotateY(angle).rotateX(angle * 0.5f);
        Matrix4f mvp = new Matrix4f();
        camera.mvp(model, mvp);

        renderer.draw(cube, shader, mvp);
    }

    @Override public void close() { if (cube!=null) cube.close(); if (shader!=null) shader.close(); }
}