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

import net.chifumi.stellar.enums.FilteringMode;
import net.chifumi.stellar.enums.ImageFormat;
import net.chifumi.stellar.enums.WrapMode;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

public class Texture {
    private final int id;
    private final ByteBuffer data;
    private int width;
    private int height;
    private ImageFormat rawImageFormat;
    private ImageFormat internalImageFormat;
    private WrapMode wrapS;
    private WrapMode wrapT;
    private FilteringMode filterMin;
    private FilteringMode filterMax;

    public Texture() {
        id = GL11.glGenTextures();
        width = 0;
        width = 0;
        rawImageFormat = ImageFormat.RGB;
        internalImageFormat = ImageFormat.RGB;
        wrapS = WrapMode.REPEAT;
        wrapT = WrapMode.REPEAT;
        filterMin = FilteringMode.NEAREST;
        filterMax = FilteringMode.NEAREST;
        data = ByteBuffer.allocate(0);
    }

    Texture(final int width, final int height, final ByteBuffer data) {
        id = GL11.glGenTextures();
        this.width = width;
        this.height = height;
        rawImageFormat = ImageFormat.RGBA;
        internalImageFormat = ImageFormat.RGBA;
        wrapS = WrapMode.REPEAT;
        wrapT = WrapMode.REPEAT;
        filterMin = FilteringMode.NEAREST;
        filterMax = FilteringMode.NEAREST;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("WeakerAccess")
    public int getWidth() {
        return width;
    }

    @SuppressWarnings("WeakerAccess")
    public int getHeight() {
        return height;
    }

    public ImageFormat getRawImageFormat() {
        return rawImageFormat;
    }

    void setRawImageFormat(final ImageFormat rawImageFormat) {
        this.rawImageFormat = rawImageFormat;
    }

    public ImageFormat getInternalImageFormat() {
        return internalImageFormat;
    }

    void setInternalImageFormat(final ImageFormat internalImageFormat) {
        this.internalImageFormat = internalImageFormat;
    }

    public WrapMode getWrapS() {
        return wrapS;
    }

    void setWrapS(final WrapMode wrapS) {
        this.wrapS = wrapS;
    }

    public WrapMode getWrapT() {
        return wrapT;
    }

    void setWrapT(final WrapMode wrapT) {
        this.wrapT = wrapT;
    }

    public FilteringMode getFilterMin() {
        return filterMin;
    }

    void setFilterMin(final FilteringMode filterMin) {
        this.filterMin = filterMin;
    }

    public FilteringMode getFilterMax() {
        return filterMax;
    }

    void setFilterMax(final FilteringMode filterMax) {
        this.filterMax = filterMax;
    }

    public void delete() {
        GL11.glDeleteTextures(id);
    }

    private static void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, GL11.GL_NONE);
    }

    void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    void generate() {
        bind();
        final int rawFormatId = rawImageFormat.getId();
        final int internalFormatId = internalImageFormat.getId();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrapS.getId());
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrapT.getId());
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filterMin.getId());
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filterMax.getId());
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalFormatId, width, height, 0, rawFormatId, GL11.GL_UNSIGNED_BYTE, data);
        unbind();
    }
}
