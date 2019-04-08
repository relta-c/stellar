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

/**
 * @author Nattakit Hosapsin
 * @version 1.0.3.2
 * @since 1.0.0
 */
public class TextureLoader {
    private ImageFormat rawImageFormat;
    private ImageFormat internalImageFormat;
    private WrapMode wrapS;
    private WrapMode wrapT;
    private FilteringMode filterMin;
    private FilteringMode filterMax;
    private int colorChannels;

    /**
     * @since 1.0.0
     */
    public TextureLoader() {
        rawImageFormat = ImageFormat.AUTOMATIC;
        internalImageFormat = ImageFormat.AUTOMATIC;
        wrapS = WrapMode.REPEAT;
        wrapT = WrapMode.REPEAT;
        filterMin = FilteringMode.NEAREST;
        filterMax = FilteringMode.NEAREST;
    }

    public Texture load(final CharSequence path) throws FileNotFoundException {
        final Texture loadTextureFile = LoadTextureFile(path);
        setRawImageFormat();
        setInternalImageFormat();
        loadTextureFile.setRawImageFormat(rawImageFormat);
        loadTextureFile.setInternalImageFormat(internalImageFormat);
        loadTextureFile.setWrapS(wrapS);
        loadTextureFile.setWrapT(wrapT);
        loadTextureFile.setFilterMin(filterMin);
        loadTextureFile.setFilterMax(filterMax);
        loadTextureFile.generate();
        return loadTextureFile;
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

    /**
     * @param rawImageFormat
     *         raw format
     *
     * @since 1.0.3.2
     */
    public TextureLoader rawImageFormat(final ImageFormat rawImageFormat) {
        this.rawImageFormat = rawImageFormat;
        return this;
    }

    /**
     * @param internalImageFormat
     *         internal format
     *
     * @since 1.0.3.2
     */
    public TextureLoader internalImageFormat(final ImageFormat internalImageFormat) {
        this.internalImageFormat = internalImageFormat;
        return this;
    }

    private void setInternalImageFormat() {
        if (internalImageFormat == ImageFormat.AUTOMATIC) {
            if (colorChannels == 3) {
                internalImageFormat = ImageFormat.SRGB;
            } else if (colorChannels == 4) {
                internalImageFormat = ImageFormat.SRGB_ALPHA;
            } else {
                throw new IllegalStateException("Unsupported number of color channels");
            }
        }
    }

    private void setRawImageFormat() {
        if (rawImageFormat == ImageFormat.AUTOMATIC ||
                rawImageFormat == ImageFormat.SRGB_ALPHA || rawImageFormat == ImageFormat.RGBA) {
            if (colorChannels == 3) {
                rawImageFormat = ImageFormat.RGB;
            } else if (colorChannels == 4) {
                rawImageFormat = ImageFormat.RGBA;
            } else {
                throw new IllegalStateException("Unsupported number of color channels");
            }
        }
    }

    private Texture LoadTextureFile(final CharSequence path) throws FileNotFoundException {
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
            colorChannels = channels.get();
        }
        return new Texture(width, height, data);
    }
}
