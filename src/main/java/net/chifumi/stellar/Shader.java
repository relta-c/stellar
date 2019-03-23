//    Copyright (C) 2019 Nattakit Hosapsin <delta@chifumi.net>
//
//    This file is part of Stellar
//    Stellar is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as
//    published by the Free Software Foundation, either
//    version 3 of the License, or (at your option) any later version.
//
//    Stellar is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with Stellar.  If not, see <https://www.gnu.org/licenses/lgpl.html>.

package net.chifumi.stellar;

import net.chifumi.stellar.enums.ShaderType;
import org.joml.Matrix4fc;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

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
        GL20.glDeleteProgram(id);
    }

    private static int createShader(final CharSequence source, final ShaderType type) {
        final int shaderId = GL20.glCreateShader(type.getId());
        GL20.glShaderSource(shaderId, source);
        GL20.glCompileShader(shaderId);
        checkCompileError(shaderId, type);
        return shaderId;
    }

    private static int createProgram(final int vertex, final int fragment) {
        final int programId;
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertex);
        GL20.glAttachShader(programId, fragment);
        GL20.glLinkProgram(programId);
        checkCompileError(programId, ShaderType.PROGRAM);
        return programId;
    }

    private static int createProgram(final int vertex, final int fragment, final int geometry) {
        final int programId;
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertex);
        GL20.glAttachShader(programId, fragment);
        GL20.glAttachShader(programId, geometry);
        GL20.glLinkProgram(programId);
        checkCompileError(programId, ShaderType.PROGRAM);
        return programId;
    }

    private static void checkCompileError(final int object, final ShaderType type) {
        final int success;
        final String infoLog;
        if (type == ShaderType.PROGRAM) {
            success = GL20.glGetProgrami(object, GL20.GL_LINK_STATUS);
            if (success == GL11.GL_FALSE) {
                final String shaderTypeString = ShaderType.PROGRAM.getName();
                infoLog = GL20.glGetProgramInfoLog(object);
                throw new IllegalStateException(shaderTypeString + " error at compile time : " + infoLog);
            }
        } else {
            success = GL20.glGetShaderi(object, GL20.GL_COMPILE_STATUS);
            if (success == GL11.GL_FALSE) {
                final String shaderTypeString = type.getName();
                infoLog = GL20.glGetShaderInfoLog(object);
                throw new IllegalStateException(shaderTypeString + " error at compile time : " + infoLog);
            }
        }
    }

    void use() {
        GL20.glUseProgram(id);
    }

    private void compile(final CharSequence vertexSource, final CharSequence fragmentSource) {
        final int vertex;
        final int fragment;
        vertex = createShader(vertexSource, ShaderType.VERTEX);
        fragment = createShader(fragmentSource, ShaderType.FRAGMENT);
        id = createProgram(vertex, fragment);
        GL20.glDeleteShader(vertex);
        GL20.glDeleteShader(fragment);
    }

    private void compile(final CharSequence vertexSource, final CharSequence fragmentSource, final CharSequence geometrySource) {
        final int vertex;
        final int fragment;
        final int geometry;
        vertex = createShader(vertexSource, ShaderType.VERTEX);
        fragment = createShader(fragmentSource, ShaderType.FRAGMENT);
        geometry = createShader(geometrySource, ShaderType.GEOMETRY);
        id = createProgram(vertex, fragment, geometry);
        GL20.glDeleteShader(vertex);
        GL20.glDeleteShader(fragment);
    }

    void setUniform(final CharSequence name, final int value) {
        GL20.glUniform1i(GL20.glGetUniformLocation(id, name), value);
    }

    void setUniform(final CharSequence name, final float value) {
        GL20.glUniform1f(GL20.glGetUniformLocation(id, name), value);
    }

    void setUniform(final CharSequence name, final float x, final float y) {
        GL20.glUniform2f(GL20.glGetUniformLocation(id, name), x, y);
    }

    void setUniform(final CharSequence name, final Vector2fc vec2) {
        GL20.glUniform2f(getUniformLocation(name), vec2.x(), vec2.y());
    }

    void setUniform(final CharSequence name, final float x, final float y, final float z) {
        GL20.glUniform3f(getUniformLocation(name), x, y, z);
    }

    void setUniform(final CharSequence name, final Vector3fc vec3) {
        GL20.glUniform3f(getUniformLocation(name), vec3.x(), vec3.y(), vec3.z());
    }

    void setUniform(final CharSequence name, final float x, final float y, final float z, final float w) {
        GL20.glUniform4f(getUniformLocation(name), x, y, z, w);
    }

    void setUniform(final CharSequence name, final Vector4fc vec4) {
        GL20.glUniform4f(getUniformLocation(name), vec4.x(), vec4.y(), vec4.z(), vec4.w());
    }

    void setUniform(final CharSequence name, final Matrix4fc mat4) {
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(MATRIX4_CAPACITY);
        mat4.get(floatBuffer);
        GL20.glUniformMatrix4fv(getUniformLocation(name), false, floatBuffer);
    }

    private int getUniformLocation(final CharSequence name) {
        return GL20.glGetUniformLocation(id, name);
    }
}
