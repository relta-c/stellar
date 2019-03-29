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

package net.chifumi.stellar.util;

import net.chifumi.stellar.graphics.Shader;
import net.chifumi.stellar.graphics.ShaderSet;
import net.chifumi.stellar.image.Texture;
import org.apache.commons.io.IOUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

public enum Resource {
    ;

    public static Shader loadShader(final ShaderSet shaderSet) throws FileNotFoundException {
        return loadShaderFile(shaderSet.getVertex(), shaderSet.getFragment());
    }

    @SuppressWarnings("unused")
    public static Shader loadShader(final CharSequence vertexPath, final CharSequence fragmentPath) throws FileNotFoundException {
        return loadShaderFile(vertexPath, fragmentPath);
    }

    public static Texture LoadTextureFile(final CharSequence path) throws FileNotFoundException {
        final ByteBuffer data;
        final int width;
        final int height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer rawWidth = stack.mallocInt(1);
            final IntBuffer rawHeight = stack.mallocInt(1);
            final IntBuffer channels = stack.mallocInt(1);
            data = STBImage.stbi_load(path, rawWidth, rawHeight, channels, 0);
            if (data == null) {
                throw new FileNotFoundException("failed to load texture : " + path);
            }
            width = rawWidth.get();
            height = rawHeight.get();
        }
        return new Texture(width, height, data);
    }

    private static Shader loadShaderFile(final CharSequence vertexPath, final CharSequence fragmentPath) throws FileNotFoundException {
        final String vertexSource = loadTextFile(vertexPath);
        final String fragmentSource = loadTextFile(fragmentPath);
        return new Shader(vertexSource, fragmentSource);
    }

    @SuppressWarnings("unused")
    private static Shader loadShaderFile(final CharSequence vertexPath, final CharSequence fragmentPath, final CharSequence geometryPath) throws FileNotFoundException {
        final String vertexSource = loadTextFile(vertexPath);
        final String fragmentSource = loadTextFile(fragmentPath);
        final String geometrySource = loadTextFile(geometryPath);

        return new Shader(vertexSource, fragmentSource, geometrySource);
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
        final InputStream fileStream = Resource.class.getResourceAsStream((String) path);
        if (fileStream == null) {
            throw new FileNotFoundException("File not found : " + path);
        }
        return fileStream;
    }
}
