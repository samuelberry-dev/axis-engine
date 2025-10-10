package engine.render;

import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL20.*;

public final class ShaderProgram implements AutoCloseable {
    private final int program;

    public ShaderProgram(String vertSource, String fragSource) {
        int vs = compile(GL_VERTEX_SHADER, vertSource);
        int fs = compile(GL_FRAGMENT_SHADER, fragSource);
        program = glCreateProgram();
        glAttachShader(program, vs);
        glAttachShader(program, fs);
        glLinkProgram(program);

        int[] status = new int[1];
        glGetProgramiv(program, GL_LINK_STATUS, status);
        if (status[0] == GL20.GL_FALSE) {
            String log = glGetProgramInfoLog(program);
            glDeleteShader(vs);
            glDeleteShader(fs);
            glDeleteProgram(program);
            throw new IllegalStateException("Program link failed:\n" + log);
        }

        glDeleteShader(vs);
        glDeleteShader(fs);
    }

    private static int compile(int type, String src) {
        int id = glCreateShader(type);
        glShaderSource(id, src);
        glCompileShader(id);
        int[] status = new int[1];
        glGetShaderiv(id, GL_COMPILE_STATUS, status);
        if (status[0] == GL20.GL_FALSE) {
            String log = glGetShaderInfoLog(id);
            glDeleteShader(id);
            throw new IllegalStateException("Shader compile failed:\n" + log);
        }
        return id;
    }

    public void use() { glUseProgram(program); }
    @Override public void close() { glDeleteProgram(program); }
}
