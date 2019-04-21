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
import net.chifumi.stellar.text.DrawableCharacter;
import net.chifumi.stellar.text.Text;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33.*;

/**
 * @author Nattakit Hosapin
 * @version 1.0.4
 * @since 1.0.0
 */
@SuppressWarnings("MethodMayBeStatic")
public class Renderer {
    private final Map<Primitive, Integer> vertexArraySet;
    private final Map<Primitive, Integer> vertexBufferSet;
    private final Display display;
    private Shader texturedShader;
    private Shader solidShader;

    public Renderer(final Display display) {
        this.display = display;
        try {
            texturedShader = new Shader(ShaderPath.DEFAULT, ShaderPath.SPRITE);
            solidShader = new Shader(ShaderPath.DEFAULT, ShaderPath.SOLID);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
            texturedShader = new Shader();
            solidShader = new Shader();
        }
        vertexArraySet = new HashMap<>();
        vertexBufferSet = new HashMap<>();
    }

    public Display getDisplay() {
        return display;
    }

    /**
     * @param drawable
     *         drawable object
     *
     * @since 1.0.2
     */
    public void draw(final Drawable drawable) {
        if (drawable.isVisible()) {
            final Primitive primitive = drawable.getPrimitive();

            if (vertexArraySet.get(primitive) == null) {
                initialize(primitive);
            }

            setUniformValues(solidShader, display, drawable);

            drawArrays(primitive);
        }
    }

    /**
     * @param texturedDrawable
     *         drawable object with texture
     *
     * @since 1.0.2
     */
    public void draw(final TexturedDrawable texturedDrawable) {
        if (texturedDrawable.isVisible()) {
            final Primitive primitive = texturedDrawable.getPrimitive();

            if (vertexArraySet.get(primitive) == null) {
                initialize(primitive);
            }

            if (texturedDrawable.isSolidFilled()) {
                texturedShader.setUniform("solidFilled", GL_TRUE);
            } else {
                texturedShader.setUniform("solidFilled", GL_FALSE);
            }
            setUniformValues(texturedShader, display, texturedDrawable);

            glActiveTexture(GL_TEXTURE0);
            texturedDrawable.getTexture().bind();

            drawArrays(primitive);

            glDeleteVertexArrays(vertexArraySet.get(primitive)); // TODO : Use object ID
            vertexArraySet.remove(primitive);
            glDeleteBuffers(vertexBufferSet.get(primitive));
            vertexBufferSet.remove(primitive);
        }
    }

    /**
     * @param text
     *         text
     *
     * @since 1.0.2
     */
    public void draw(final Text text) {
        final int length = text.getLength();
        for (int i = 0; i < length; i++) {
            final DrawableCharacter character = text.getCharacterAt(i);
            character.setPosition(text.getCursorAt(i));
            draw(character);
        }
    }

    public void terminate() {
        for (final int vertexArray : vertexArraySet.values()) {
            glDeleteVertexArrays(vertexArray);
        }

        for (final int vertexBuffer : vertexBufferSet.values()) {
            glDeleteBuffers(vertexBuffer);
        }
    }

    /**
     * @param position
     *         the position of scissor
     * @param size
     *         the size of scissor
     *
     * @since 1.0.4
     */
    public void enableMask(final Vector2<Integer> position, final Vector2<Integer> size) {
        glEnable(GL_SCISSOR_TEST);
        glScissor(position.getX(), display.getResolution().getY() - position.getY() - size.getY(),
                  size.getX(), size.getY());
    }

    /**
     * @since 1.0.4
     */
    public void disableMask() {
        glDisable(GL_SCISSOR_TEST);
    }

    private static void setUniformValues(final Shader shader, final Display display, final Drawable drawable) {
        shader.setUniform("projection", display.getCamera().getProjectionMatrix());
        shader.setUniform("view", display.getCamera().getViewMatrix());
        shader.setUniform("model", drawable.getModelMatrix());
        shader.setUniform("color", drawable.getNormalizedColor());
        shader.setUniform("transparency", drawable.getNormalizedTransparency());
    }

    private void initialize(final Primitive primitive) {
        vertexArraySet.put(primitive, glGenVertexArrays());
        vertexBufferSet.put(primitive, glGenBuffers());

        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferSet.get(primitive));
        glBufferData(GL_ARRAY_BUFFER, primitive.getVertices(), GL_STATIC_DRAW);

        glBindVertexArray(vertexArraySet.get(primitive));
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 4 * 4, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, GL_NONE);
        glBindVertexArray(GL_NONE);
    }

    private void drawArrays(final Primitive primitive) {
        glBindVertexArray(vertexArraySet.get(primitive));
        glDrawArrays(primitive.getDrawMode(), 0, primitive.getVerticesNum());
    }
}
