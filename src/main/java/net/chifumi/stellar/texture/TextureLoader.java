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

package net.chifumi.stellar.texture;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TextureLoader {
    private ImageFormat rawImageFormat;
    private ImageFormat internalImageFormat;
    private WrapMode wrapS;
    private WrapMode wrapT;
    private FilteringMode filterMin;
    private FilteringMode filterMax;

    public TextureLoader() {
        rawImageFormat = ImageFormat.RGBA;
        internalImageFormat = ImageFormat.RGBA; // TODO : Automatic format detection
        wrapS = WrapMode.REPEAT;
        wrapT = WrapMode.REPEAT;
        filterMin = FilteringMode.NEAREST;
        filterMax = FilteringMode.NEAREST;
    }

    public Texture load(final CharSequence path) throws FileNotFoundException {
        final Texture loadTextureFile = LoadTextureFile(path);
        loadTextureFile.setRawImageFormat(rawImageFormat);
        loadTextureFile.setInternalImageFormat(internalImageFormat);
        loadTextureFile.setWrapS(wrapS);
        loadTextureFile.setWrapT(wrapT);
        loadTextureFile.setFilterMin(filterMin);
        loadTextureFile.setFilterMax(filterMax);
        loadTextureFile.generate();
        return loadTextureFile;
    }

    public TextureLoader rawImageFormat(final ImageFormat format) {
        rawImageFormat = format;
        return this;
    }

    public TextureLoader internalImageFormat(final ImageFormat format) {
        internalImageFormat = format;
        return this;
    }

    public TextureLoader imageFormat(final ImageFormat format) {
        rawImageFormat = format;
        internalImageFormat = format;
        return this;
    }

    public TextureLoader wrapT(final WrapMode wrapMode) {
        wrapS = wrapMode;
        return this;
    }

    public TextureLoader wrapS(final WrapMode wrapMode) {
        wrapT = wrapMode;
        return this;
    }

    public TextureLoader wrap(final WrapMode wrapMode) {
        wrapS = wrapMode;
        wrapT = wrapMode;
        return this;
    }

    public TextureLoader filterMin(final FilteringMode filteringMode) {
        filterMin = filteringMode;
        return this;
    }

    public TextureLoader filterMax(final FilteringMode filteringMode) {
        filterMax = filteringMode;
        return this;
    }

    public TextureLoader filter(final FilteringMode filteringMode) {
        filterMin = filteringMode;
        filterMax = filteringMode;
        return this;
    }

    private static Texture LoadTextureFile(final CharSequence path) throws FileNotFoundException {
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
}
