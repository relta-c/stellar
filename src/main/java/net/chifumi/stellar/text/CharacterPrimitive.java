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

package net.chifumi.stellar.text;

import net.chifumi.stellar.graphics.Primitive;
import org.joml.Vector2d;
import org.joml.Vector2dc;

import static org.lwjgl.opengl.GL33.GL_TRIANGLES;

class CharacterPrimitive implements Primitive {
    private static final int verticesNum = 6;
    private static final int drawMode = GL_TRIANGLES;
    private final float[] vertices;

    @SuppressWarnings("NumericCastThatLosesPrecision")
    CharacterPrimitive(final Vector2dc position, final Vector2dc size) {
        final Vector2d topLeft = (Vector2d) position;
        final Vector2d topRight = new Vector2d(position.x() + size.x(), position.y());
        final Vector2d bottomLeft = new Vector2d(position.x(), position.y() + size.y());
        final Vector2d bottomRight = new Vector2d(position.x() + size.x(), position.y() + size.y());
        vertices = new float[]{
                0.0f, 1.0f, (float) bottomLeft.x, (float) bottomLeft.y,
                1.0f, 0.0f, (float) topRight.x, (float) topRight.y,
                0.0f, 0.0f, (float) topLeft.x, (float) topLeft.y,
                0.0f, 1.0f, (float) bottomLeft.x, (float) bottomLeft.y,
                1.0f, 1.0f, (float) bottomRight.x, (float) bottomRight.y,
                1.0f, 0.0f, (float) topRight.x, (float) topRight.y
        };
    }

    @Override
    public float[] getVertices() {
        return vertices.clone();
    }

    @Override
    public int getDrawMode() {
        return drawMode;
    }

    @Override
    public int getVerticesNum() {
        return verticesNum;
    }
}
