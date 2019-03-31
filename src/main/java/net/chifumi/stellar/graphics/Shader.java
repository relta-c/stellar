/*
 * Copyright (C) 2019 Nattakit Hosapsin <delta@chifumi.net>
 *
 * This file is part of Stellar
 * Stellar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either
 *  version 3 of the License, or (at your option) any later version.
 *
 *  Stellar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Stellar.  If not, see <https://www.gnu.org/licenses/lgpl.html>.
 *
 */

package net.chifumi.stellar.graphics;

import org.joml.Matrix4fc;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;

@SuppressWarnings({"unused", "SameParameterValue"})
public
class Shader {
    private static final int MATRIX4_CAPACITY = 16;
    private int id;

    Shader() {
        id = -1;
    }

    public Shader(final CharSequence vertexSource, final CharSequence fragmentSource) {
        compile(vertexSource, fragmentSource);
    }

    public Shader(final CharSequence vertexSource, final CharSequence fragmentSource, final CharSequence geometrySource) {
        compile(vertexSource, fragmentSource, geometrySource);
    }

    void use() {
        glUseProgram(id);
    }

    void setUniform(final CharSequence name, final int value) {
        glUniform1i(getUniformLocation(name), value);
    }

    void setUniform(final CharSequence name, final float value) {
        glUniform1f(getUniformLocation(name), value);
    }

    void setUniform(final CharSequence name, final float x, final float y) {
        glUniform2f(getUniformLocation(name), x, y);
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

    private static int createShader(final CharSequence source, final ShaderType type) {
        final int shaderID = glCreateShader(type.getId());
        glShaderSource(shaderID, source);
        glCompileShader(shaderID);
        checkCompileError(shaderID, type);
        return shaderID;
    }

    private static int createProgram(final int vertex, final int fragment) {
        final int programID = initProgram(vertex, fragment);
        compileProgram(programID);
        return programID;
    }

    private static int createProgram(final int vertex, final int fragment, final int geometry) {
        final int programID = initProgram(vertex, fragment);
        glAttachShader(programID, geometry);
        compileProgram(programID);
        return programID;
    }

    private static int initProgram(final int vertex, final int fragment) {
        final int programID;
        programID = glCreateProgram();
        glAttachShader(programID, vertex);
        glAttachShader(programID, fragment);
        return programID;
    }

    private static void compileProgram(final int programID) {
        glLinkProgram(programID);
        checkCompileError(programID, ShaderType.PROGRAM);
    }

    private static void checkCompileError(final int object, final ShaderType type) {
        final int success;
        final String infoLog;
        if (type == ShaderType.PROGRAM) {
            success = glGetProgrami(object, GL_LINK_STATUS);
            if (success == GL_FALSE) {
                final String shaderTypeString = ShaderType.PROGRAM.getName();
                infoLog = glGetProgramInfoLog(object);
                throw new IllegalStateException(shaderTypeString + " error at compile time : " + infoLog);
            }
        } else {
            success = glGetShaderi(object, GL_COMPILE_STATUS);
            if (success == GL_FALSE) {
                final String shaderTypeString = type.getName();
                infoLog = glGetShaderInfoLog(object);
                throw new IllegalStateException(shaderTypeString + " error at compile time : " + infoLog);
            }
        }
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

    private int getUniformLocation(final CharSequence name) {
        use();
        return glGetUniformLocation(id, name);
    }
}
