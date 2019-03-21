package net.chifumi.stellar;

import net.chifumi.stellar.enums.ShaderType;
import org.joml.Matrix4fc;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;

@SuppressWarnings({"SameParameterValue", "unused"})
class Shader {
    private static final int MATRIX4_CAPACITY = 16;
    private int id;

    Shader() {
        id = 0;
    }

    Shader(final CharSequence vertexSource, final CharSequence fragmentSource) {
        compile(vertexSource, fragmentSource);
    }

    Shader(final CharSequence vertexSource, final CharSequence fragmentSource, final CharSequence geometrySource) {
        compile(vertexSource, fragmentSource, geometrySource);
    }

    public void delete() {
        glDeleteProgram(id);
    }

    private static int createShader(final CharSequence source, final ShaderType type) {
        final int shaderId = glCreateShader(type.getId());
        glShaderSource(shaderId, source);
        glCompileShader(shaderId);
        checkCompileError(shaderId, type);
        return shaderId;
    }

    private static int createProgram(final int vertex, final int fragment) {
        final int programId;
        programId = glCreateProgram();
        glAttachShader(programId, vertex);
        glAttachShader(programId, fragment);
        glLinkProgram(programId);
        checkCompileError(programId, ShaderType.PROGRAM);
        return programId;
    }

    private static int createProgram(final int vertex, final int fragment, final int geometry) {
        final int programId;
        programId = glCreateProgram();
        glAttachShader(programId, vertex);
        glAttachShader(programId, fragment);
        glAttachShader(programId, geometry);
        glLinkProgram(programId);
        checkCompileError(programId, ShaderType.PROGRAM);
        return programId;
    }

    private static void checkCompileError(final int object, final ShaderType type) {
        final int success;
        final String infoLog;
        if (type == ShaderType.PROGRAM) {
            success = glGetProgrami(object, GL_LINK_STATUS);
            if (success == GL_FALSE) {
                final String shaderTypeString = ShaderType.PROGRAM.getName();
                infoLog = glGetProgramInfoLog(object);
                System.err.println(shaderTypeString + " error at link time : " + infoLog);
            }
        } else {
            success = glGetShaderi(object, GL_COMPILE_STATUS);
            if (success == GL_FALSE) {
                final String shaderTypeString = type.getName();
                infoLog = glGetShaderInfoLog(object);
                System.err.println(shaderTypeString + " error at compile time : " + infoLog);
            }
        }
    }

    void use() {
        glUseProgram(id);
    }

    private void compile(final CharSequence vertexSource, final CharSequence fragmentSource) {
        final int vertex;
        final int fragment;
        vertex = createShader(vertexSource, ShaderType.VERTEX);
        fragment = createShader(fragmentSource, ShaderType.FRAGMENT);
        id = createProgram(vertex, fragment);
        glDeleteShader(vertex);
        glDeleteShader(fragment);
    }

    private void compile(final CharSequence vertexSource, final CharSequence fragmentSource, final CharSequence geometrySource) {
        final int vertex;
        final int fragment;
        final int geometry;
        vertex = createShader(vertexSource, ShaderType.VERTEX);
        fragment = createShader(fragmentSource, ShaderType.FRAGMENT);
        geometry = createShader(geometrySource, ShaderType.GEOMETRY);
        id = createProgram(vertex, fragment, geometry);
        glDeleteShader(vertex);
        glDeleteShader(fragment);
    }

    void setUniform(final CharSequence name, final int value) {
        glUniform1i(glGetUniformLocation(id, name), value);
    }

    void setUniform(final CharSequence name, final float value) {
        glUniform1f(glGetUniformLocation(id, name), value);
    }

    void setUniform(final CharSequence name, final float x, final float y) {
        glUniform2f(glGetUniformLocation(id, name), x, y);
    }

    void setUniform(final CharSequence name, final Vector2fc vec2) {
        glUniform2f(getUniformLocation(name), vec2.x(), vec2.y());
    }

    void setUniform(final CharSequence name, final float x, final float y, final float z) {
        glUniform3f(getUniformLocation(name), x, y, z);
    }

    void setUniform(final CharSequence name, final Vector3fc vec3) {
        glUniform3f(getUniformLocation(name), vec3.x(), vec3.y(), vec3.z());
    }

    void setUniform(final CharSequence name, final float x, final float y, final float z, final float w) {
        glUniform4f(getUniformLocation(name), x, y, z, w);
    }

    void setUniform(final CharSequence name, final Vector4fc vec4) {
        glUniform4f(getUniformLocation(name), vec4.x(), vec4.y(), vec4.z(), vec4.w());
    }

    void setUniform(final CharSequence name, final Matrix4fc mat4) {
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(MATRIX4_CAPACITY);
        mat4.get(floatBuffer);
        glUniformMatrix4fv(getUniformLocation(name), false, floatBuffer);
    }

    private int getUniformLocation(final CharSequence name) {
        return glGetUniformLocation(id, name);
    }
}
