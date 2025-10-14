package engine.gfx;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private final Matrix4f proj = new Matrix4f();
    private final Matrix4f view = new Matrix4f();

    private final Vector3f eye = new Vector3f(0f, 0f, 3f);
    private final Vector3f center = new Vector3f(0f, 0f, 0f);
    private final Vector3f up = new Vector3f(0f, 1f, 0f);

    public void setPerspective(float fovRad, float aspect, float near, float far) {
        proj.identity().perspective(fovRad, aspect, near, far);
    }

    public void lookAt(float ex, float ey, float ez, float cx, float cy, float cz) {
        eye.set(ex, ey, ez);
        center.set(cx, cy, cz);
        view.identity().lookAt(eye, center, up);
    }

    public Matrix4f mvp(Matrix4f model, Matrix4f dest) {
        // dest = proj * view * model
        return proj.mul(view, dest).mul(model);
    }
}
