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

package net.chifumi.stellar.graphic;

import net.chifumi.stellar.math.Vector2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

/**
 * @author Nattakit Hosapsin
 * @version 1.0.2
 * @since 1.0.0
 */
public class PostProcessor {
    private final Vector2<Integer> resolution;
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

    public void add(final Effect effect) {
        if (!effectList.contains(effect)) {
            Shader newShader;
            try {
                newShader = new Shader(ShaderPath.FRAMEBUFFER, effect.getPath());
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

    /**
     * Remove all postprocessing effect.
     *
     * @since 1.0.2
     */
    public void clear() { // TODO : Remove each effect
        effectList.clear();
        framebufferList.clear();
        shaderList.clear();
    }

    /**
     * Get a list of current postprocessing effect
     *
     * @return list of postprocessing effect
     *
     * @since 1.0.2
     */
    public List<Effect> list() {
        return Collections.unmodifiableList(effectList);
    }

    public void begin() {
        multiSampledFramebuffer.bind();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void end() {
        if (framebufferList.isEmpty()) {
            add(Effect.NORMAL);
        }
        multiSampledFramebuffer.bindRead();
        framebufferList.get(0).bindDraw();
        glBlitFramebuffer(0, 0, resolution.getX(), resolution.getY(),
                          0, 0, resolution.getX(), resolution.getY(),
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
