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

import net.chifumi.stellar.utils.IO;
import org.joml.Vector2i;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

public class PostProcessor {
    private static final int multiSampleLevel = 8;
    private final Vector2i resolution;
    private final int surface;
    private final int multiSampledFramebuffer;
    private final int blittingFramebuffer;
    private int vertexArray;
    private Shader shader;

    public PostProcessor(final Display display) {
        resolution = display.getResolution();

        multiSampledFramebuffer = glGenFramebuffers();
        blittingFramebuffer = glGenFramebuffers();
        final int renderbuffer = glGenRenderbuffers();

        glBindFramebuffer(GL_FRAMEBUFFER, multiSampledFramebuffer);
        glBindRenderbuffer(GL_RENDERBUFFER, renderbuffer);
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, multiSampleLevel, GL_RGB, resolution.x, resolution.y);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, renderbuffer);

        glBindFramebuffer(GL_FRAMEBUFFER, blittingFramebuffer);
        surface = createTextureAttachment();
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, surface, 0);

        glBindFramebuffer(GL_FRAMEBUFFER, GL_NONE);

        initializeRenderData();
        try {
            shader = IO.loadShader(ShaderSet.FRAMEBUFFER, ShaderSet.BLUR);
        } catch (final FileNotFoundException e) {
            shader = new Shader();
            e.printStackTrace();
        }
    }

    public void begin() {
        glBindFramebuffer(GL_FRAMEBUFFER, multiSampledFramebuffer);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void end() {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, multiSampledFramebuffer);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, blittingFramebuffer);
        glBlitFramebuffer(0, 0, resolution.x, resolution.y,
                          0, 0, resolution.x, resolution.y,
                          GL_COLOR_BUFFER_BIT,
                          GL_NEAREST);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void draw() {
        shader.use();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, surface);
        glBindVertexArray(vertexArray);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glBindVertexArray(0);
    }

    private void initializeRenderData() {
        final int vertexBuffer;
        final float[] vertices = {
                -1.0f, -1.0f,
                1.0f, 1.0f,
                -1.0f, 1.0f,
                -1.0f, -1.0f,
                1.0f, -1.0f,
                1.0f, 1.0f
        };

        vertexArray = glGenVertexArrays();
        vertexBuffer = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindVertexArray(vertexArray);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * 2, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(GL_NONE);
    }

    private int createTextureAttachment() {
        final int textures = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textures);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, resolution.x, resolution.y, 0,
                     GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        return textures;
    }
}
