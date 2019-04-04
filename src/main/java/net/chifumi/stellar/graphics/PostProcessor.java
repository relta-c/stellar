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
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class PostProcessor {
    private final Vector2i resolution;
    private final Framebuffer multiSampledFramebuffer;
    private final List<Effect> effectList;
    private final List<Framebuffer> framebufferList;
    private final List<Shader> shaderList;
    private int vertexArray;

    public PostProcessor(final Display display) {
        resolution = display.getResolution();

        multiSampledFramebuffer = new Framebuffer();
        multiSampledFramebuffer.createRenderBufferAttachment(resolution, display.getMultisamplingLevel());

        effectList = new ArrayList<>();
        framebufferList = new ArrayList<>();
        shaderList = new ArrayList<>();

        initializeRenderData();
    }

    public void addEffect(final Effect effect) {
        if (!effectList.contains(effect)) {
            Shader newShader;
            try {
                newShader = IO.loadShader(ShaderPath.FRAMEBUFFER, effect.getPath());
            } catch (final FileNotFoundException e) {
                newShader = new Shader();
                e.printStackTrace();
            }
            shaderList.add(newShader);

            final Framebuffer newFramebuffer = new Framebuffer();
            newFramebuffer.createTextureAttachment(resolution);
            framebufferList.add(newFramebuffer);
            effectList.add(effect);
        }
    }

    public void begin() {
        multiSampledFramebuffer.bind();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void end() {
        if (framebufferList.isEmpty()) {
            addEffect(Effect.NORMAL);
        }
        multiSampledFramebuffer.bindRead();
        framebufferList.get(0).bindDraw();
        glBlitFramebuffer(0, 0, resolution.x, resolution.y,
                          0, 0, resolution.x, resolution.y,
                          GL_COLOR_BUFFER_BIT,
                          GL_NEAREST);
        Framebuffer.bindDefault();
        draw();
    }

    private void draw() {
        glBindVertexArray(vertexArray);
        glActiveTexture(GL_TEXTURE0); // TODO : Can optimize
        for (int i = 0; i < framebufferList.size(); i++) {
            if (i == framebufferList.size() - 1) {
                Framebuffer.bindDefault();
            } else {
                framebufferList.get(i + 1).bind();
            }
            shaderList.get(i).use();
            glBindTexture(GL_TEXTURE_2D, framebufferList.get(i).getAttachment());
            glDrawArrays(GL_TRIANGLES, 0, 6);
        }
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
}
