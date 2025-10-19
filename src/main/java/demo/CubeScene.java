package demo;

import org.joml.Matrix4f;

import engine.core.EngineContext;
import engine.gfx.Camera;
import engine.gfx.Mesh;
import engine.gfx.MeshBuilder;
import engine.gfx.Renderer;
import engine.gfx.Shader;
import engine.gfx.Texture2D;

public class CubeScene implements AutoCloseable {
    private Shader shader;
    private Mesh cube;
    private Camera camera;
    private Renderer renderer;
    private Texture2D atlas;

    private float angle = 0f;

    public void init(EngineContext ctx) {
        shader = Shader.fromResources("shaders/basic.vert", "shaders/basic.frag");
        renderer = new Renderer();
        renderer.initFor(shader);

        camera = new Camera();
        float aspect = Math.max(1, ctx.window().width()) / (float) Math.max(1, ctx.window().height());
        camera.setPerspective((float)Math.toRadians(60), aspect, 0.1f, 100f);
        camera.lookAt(0f, 0f, 3f, 0f, 0f, 0f);

        atlas = Texture2D.fromResource("textures/atlas.png", true);

        MeshBuilder mb = new MeshBuilder();
        float s = 0.5f;
        float[] white = {1, 1, 1};

        final float cols = 3f, rows = 2f;
        final float du = 1f / cols, dv = 1f / rows;
        java.util.function.BiFunction<Integer, Integer, float[][]> tile = (cx, cy) -> {
            float u0 = cx * du, v0 = cy * dv;
            float u1 = (cx + 1) * du, v1 = (cy + 1) * dv;
            return new float[][]{
                    {u0, v0}, {u1, v0}, {u1, v1}, {u0, v1}
            };
        };

        float[][] F = tile.apply(0, 0); // Front  = red
        float[][] R = tile.apply(1, 0); // Right  = green
        float[][] B = tile.apply(2, 0); // Back   = blue
        float[][] L = tile.apply(0, 1); // Left   = yellow
        float[][] D = tile.apply(1, 1); // Bottom = magenta
        float[][] T = tile.apply(2, 1); // Top    = cyan

        // Front (+Z)
        mb.addQuad(
                new float[]{-s, -s,  s}, new float[]{ s, -s,  s},
                new float[]{ s,  s,  s}, new float[]{-s,  s,  s},
                white, F[0], F[1], F[2], F[3]
        );
        // Right (+X)
        mb.addQuad(
                new float[]{ s, -s,  s}, new float[]{ s, -s, -s},
                new float[]{ s,  s, -s}, new float[]{ s,  s,  s},
                white, R[0], R[1], R[2], R[3]
        );
        // Back (−Z)
        mb.addQuad(
                new float[]{ s, -s, -s}, new float[]{-s, -s, -s},
                new float[]{-s,  s, -s}, new float[]{ s,  s, -s},
                white, B[0], B[1], B[2], B[3]
        );
        // Left (−X)
        mb.addQuad(
                new float[]{-s, -s, -s}, new float[]{-s, -s,  s},
                new float[]{-s,  s,  s}, new float[]{-s,  s, -s},
                white, L[0], L[1], L[2], L[3]
        );
        // Bottom (−Y)
        mb.addQuad(
                new float[]{-s, -s, -s}, new float[]{ s, -s, -s},
                new float[]{ s, -s,  s}, new float[]{-s, -s,  s},
                white, D[0], D[1], D[2], D[3]
        );
        // Top (+Y)
        mb.addQuad(
                new float[]{-s,  s,  s}, new float[]{ s,  s,  s},
                new float[]{ s,  s, -s}, new float[]{-s,  s, -s},
                white, T[0], T[1], T[2], T[3]
        );

        cube = mb.build();
    }

    public void update(EngineContext ctx, float dt) {
        angle += dt * 0.9f;
    }

    public void render(EngineContext ctx) {
        renderer.beginFrame(0.10f, 0.12f, 0.15f);
        Matrix4f model = new Matrix4f().rotateY(angle).rotateX(angle * 0.5f);
        Matrix4f mvp = new Matrix4f();
        camera.mvp(model, mvp);
        renderer.draw(cube, shader, mvp, atlas);
    }

    @Override public void close() {
        if (cube != null) cube.close();
        if (atlas != null) atlas.close();
        if (shader != null) shader.close();
    }
}
