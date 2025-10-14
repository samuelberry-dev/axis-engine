package engine.gfx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

public class Shader implements AutoCloseable {
    private final int programId;

    private Shader(int programId) {
        this.programId = programId;
    }

    public static Shader fromResources(String vertPath, String fragPath) {
        String vertSrc = readResource(vertPath);
        String fragSrc = readResource(fragPath);

        int vs = compile(GL_VERTEX_SHADER, vertSrc);
        int fs = compile(GL_FRAGMENT_SHADER, fragSrc);

        int prog = glCreateProgram();
        glAttachShader(prog, vs);
        glAttachShader(prog, fs);
        glBindFragDataLocation(prog, 0, "FragColor");
        glLinkProgram(prog);

        if (glGetProgrami(prog, GL_LINK_STATUS) == 0) {
            String log = glGetProgramInfoLog(prog);
            glDeleteShader(vs); glDeleteShader(fs); glDeleteProgram(prog);
            throw new IllegalStateException("Program link failed:\n" + log);
        }

        glDetachShader(prog, vs);
        glDetachShader(prog, fs);
        glDeleteShader(vs);
        glDeleteShader(fs);

        return new Shader(prog);
    }

    public void use() { glUseProgram(programId); }
    public static void unuse() { glUseProgram(0); }
    public int id() { return programId; }

    public int loc(String name) {
        int loc = glGetUniformLocation(programId, name);
        if (loc < 0) throw new IllegalStateException("Uniform not found: " + name);
        return loc;
    }

    public void setUniformMat4(int location, java.nio.FloatBuffer fb) {
        glUniformMatrix4fv(location, false, fb);
    }

    public void setUniform1i(int location, int value) {
        glUniform1i(location, value);
    }

    private static int compile(int type, String src) {
        int shader = glCreateShader(type);
        glShaderSource(shader, src);
        glCompileShader(shader);
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            String log = glGetShaderInfoLog(shader);
            glDeleteShader(shader);
            throw new IllegalStateException("Shader compile failed:\n" + log);
        }
        return shader;
    }

    private static String readResource(String path) {
        String full = path.startsWith("/") ? path : "/" + path;
        InputStream is = Shader.class.getResourceAsStream(full);
        if (is == null) throw new IllegalArgumentException("Resource not found: " + path);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read resource: " + path, e);
        }
    }

    @Override public void close() { glDeleteProgram(programId); }
}
