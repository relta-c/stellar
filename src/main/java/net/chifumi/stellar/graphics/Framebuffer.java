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

import net.chifumi.stellar.math.Vector2;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

/**
 * @version 1.0.2
 * @since 1.0.0
 */
class Framebuffer {
    private final int id;
    private int attachment;

    Framebuffer() {
        id = glGenFramebuffers();
        attachment = -1;
    }

    static void bindDefault() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    void bindRead() {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
    }

    void bindDraw() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, id);
    }

    int getAttachment() {
        return attachment;
    }


    /**
     * @param resolution
     *         framebuffer size
     * @param multiSampleLevel
     *         multisampling anti-alias level
     *         <p>This value should be the same as value in display</p>
     *
     * @since 1.0.2
     */
    void createRenderBufferAttachment(final Vector2<Integer> resolution, final int multiSampleLevel) {
        bind();
        final int renderbuffer = glGenRenderbuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        glBindRenderbuffer(GL_RENDERBUFFER, renderbuffer);
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, multiSampleLevel, GL_RGB, resolution.getX(),
                                         resolution.getY());
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, renderbuffer);
        attachment = renderbuffer;
        bindDefault();
    }

    /**
     * @param resolution
     *         texture size
     *
     * @since 1.0.2
     */
    void createTextureAttachment(final Vector2<Integer> resolution) {
        bind();
        final int textures = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textures);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, resolution.getX(), resolution.getY(), 0,
                     GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textures, 0);
        attachment = textures;
        bindDefault();
    }
}
