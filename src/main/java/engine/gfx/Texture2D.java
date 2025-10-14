package engine.gfx;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import org.lwjgl.stb.STBImage;

public class Texture2D implements AutoCloseable {
    private final int id;

    private Texture2D(int id) {
        this.id = id;
    }

    public static Texture2D fromResource(String path, boolean flipY) {
        if (flipY) STBImage.stbi_set_flip_vertically_on_load(true);
        try (InputStream is = Texture2D.class.getResourceAsStream("/" + path)) {
            if (is == null) throw new IllegalArgumentException("No resource: " + path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            is.transferTo(baos);
            ByteBuffer file = BufferUtils.createByteBuffer(baos.size()).put(baos.toByteArray()).flip();

            IntBuffer x = BufferUtils.createIntBuffer(1);
            IntBuffer y = BufferUtils.createIntBuffer(1);
            IntBuffer comp = BufferUtils.createIntBuffer(1);
            ByteBuffer pixels = STBImage.stbi_load_from_memory(file, x, y, comp, 4);
            if (pixels == null) throw new IllegalStateException("stbi: " + STBImage.stbi_failure_reason());

            int tex = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, tex);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, x.get(0), y.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
            glBindTexture(GL_TEXTURE_2D, 0);
            STBImage.stbi_image_free(pixels);

            return new Texture2D(tex);
        } catch (Exception e) {
            throw new RuntimeException("Load texture failed: " + path, e);
        }
    }

    public void bind(int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public static void unbind() { glBindTexture(GL_TEXTURE_2D, 0); }

    @Override public void close() { glDeleteTextures(id); }
}
