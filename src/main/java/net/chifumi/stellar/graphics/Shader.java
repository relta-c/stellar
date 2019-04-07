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

import net.chifumi.stellar.math.Vector3;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4fc;
import org.lwjgl.BufferUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL33.*;

/**
 * @author Nattakit Hosapsin
 * @version 1.0.2
 * @since 1.0.0
 */
@SuppressWarnings("SameParameterValue")
class Shader {
    private static final int MATRIX4_CAPACITY = 16;
    private int id;

    Shader() {
        id = -1;
    }

    Shader(final ShaderPath vertexPath, final ShaderPath fragmentPath) throws FileNotFoundException {
        final String vertexSource = loadTextFile(vertexPath.getPath());
        final String fragmentSource = loadTextFile(fragmentPath.getPath());
        compile(vertexSource, fragmentSource);
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

    /**
     * @param name
     *         name of uniform
     * @param vector3
     *         value
     *
     * @since 1.0.2
     */
    void setUniform(final CharSequence name, final Vector3<Float> vector3) {
        glUniform3f(getUniformLocation(name), vector3.getX(), vector3.getY(), vector3.getZ());
    }

    void setUniform(final CharSequence name, final Matrix4fc mat4) {
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(MATRIX4_CAPACITY);
        mat4.get(floatBuffer);
        glUniformMatrix4fv(getUniformLocation(name), false, floatBuffer);
    }

    private static int createShader(final CharSequence source, final ShaderType type) {
        final int shaderID = glCreateShader(type.getID());
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

    private static String loadTextFile(final CharSequence path) throws FileNotFoundException {
        final String result;
        final InputStream fileStream = loadResourceFile(path);
        try {
            result = IOUtils.toString(fileStream, StandardCharsets.UTF_8);
            fileStream.close();
        } catch (final IOException e) {
            throw new FileNotFoundException("failed to load file : " + path);
        }
        if (result == null) {
            throw new FileNotFoundException("failed to load file : " + path);
        }
        return result;
    }

    private static InputStream loadResourceFile(final CharSequence path) throws FileNotFoundException {
        final InputStream fileStream = Shader.class.getResourceAsStream("/" + path);
        if (fileStream == null) {
            throw new FileNotFoundException("File not found : " + path);
        }
        return fileStream;
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

    private int getUniformLocation(final CharSequence name) {
        use();
        return glGetUniformLocation(id, name);
    }
}
