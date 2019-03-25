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

import net.chifumi.stellar.enums.Primitive;

import java.util.EnumMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {
    private final Map<Primitive, Integer> vao;
    private final Map<Primitive, Integer> vbo;

    public Renderer(final Display display) {
        vao = new EnumMap<>(Primitive.class);
        vbo = new EnumMap<>(Primitive.class);
    }


    private void init(final Primitive primitive) {
        vao.put(primitive, glGenVertexArrays());
        vbo.put(primitive, glGenBuffers());

        glBindBuffer(GL_ARRAY_BUFFER, vbo.get(primitive));
        glBufferData(GL_ARRAY_BUFFER, primitive.getVertices(), GL_STATIC_DRAW);

        glBindVertexArray(vao.get(primitive));
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * 4, 0L);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * 4, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, GL_NONE);
        glBindVertexArray(GL_NONE);
    }

    public void draw(final Display display, final Sprite sprite) {
        final Primitive primitive = sprite.getPrimitive();
        final Shader shader = display.getShader();

        if (vao.get(primitive) == null) {
            init(primitive);
        }

        shader.use();
        shader.setUniform("projection", display.getCamera().getProjection());
        shader.setUniform("view", display.getCamera().getView());
        shader.setUniform("model", sprite.getModel());
        shader.setUniform("spriteColor", sprite.getColor());

        glActiveTexture(GL_TEXTURE0);
        sprite.getTexture().bind();

        glBindVertexArray(vao.get(primitive));
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
}
