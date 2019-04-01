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

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {
    private final Map<Primitive, Integer> vertexArraySet;
    private final Map<Primitive, Integer> vertexBufferSet;
    private Shader texturedShader;
    private Shader solidShader;

    public Renderer(final Display display) {
        try {
            texturedShader = IO.loadShader(ShaderSet.DEFAULT, ShaderSet.SPRITE);
            solidShader = IO.loadShader(ShaderSet.DEFAULT, ShaderSet.SOLID);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
            texturedShader = new Shader();
            solidShader = new Shader();
        }
        vertexArraySet = new HashMap<>();
        vertexBufferSet = new HashMap<>();
    }

    public void draw(final Display display, final Drawable drawable) {
        final Primitive primitive = drawable.getPrimitive();

        if (vertexArraySet.get(primitive) == null) {
            init(primitive);
        }

        setUniformValues(solidShader, display, drawable);

        drawArrays(primitive);
    }

    public void draw(final Display display, final TexturedDrawable texturedDrawable) {
        final Primitive primitive = texturedDrawable.getPrimitive();

        if (vertexArraySet.get(primitive) == null) {
            init(primitive);
        }

        setUniformValues(texturedShader, display, texturedDrawable);

        glActiveTexture(GL_TEXTURE0);
        texturedDrawable.getTexture().bind();

        drawArrays(primitive);
    }

    private static void setUniformValues(final Shader shader, final Display display, final Drawable drawable) {
        shader.setUniform("projection", display.getCamera().getProjectionMatrix());
        shader.setUniform("view", display.getCamera().getViewMatrix());
        shader.setUniform("model", drawable.getModelMatrix());
        shader.setUniform("color", drawable.getColor());
    }

    private void init(final Primitive primitive) {
        vertexArraySet.put(primitive, glGenVertexArrays());
        vertexBufferSet.put(primitive, glGenBuffers());

        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferSet.get(primitive));
        glBufferData(GL_ARRAY_BUFFER, primitive.getVertices(), GL_STATIC_DRAW);

        glBindVertexArray(vertexArraySet.get(primitive));
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * 4, 0L);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * 4, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, GL_NONE);
        glBindVertexArray(GL_NONE);
    }

    private void drawArrays(final Primitive primitive) {
        glBindVertexArray(vertexArraySet.get(primitive));
        glDrawArrays(primitive.getDrawMode(), 0, primitive.getVerticesNum());
    }
}
