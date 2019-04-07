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
import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.Vector2;

import static org.lwjgl.opengl.GL33.GL_TRIANGLES;

class CharacterPrimitive implements Primitive {
    private static final int verticesNum = 6;
    private static final int drawMode = GL_TRIANGLES;
    private final float[] vertices;

    CharacterPrimitive(final Vector2<Double> topLeft, final Vector2<Double> size) {
        final Vector2<Double> topRight = new ImmutableVector2<>(topLeft.getX() + size.getX(), topLeft.getY());
        final Vector2<Double> bottomLeft = new ImmutableVector2<>(topLeft.getX(), topLeft.getY() + size.getY());
        final Vector2<Double> bottomRight = new ImmutableVector2<>(topLeft.getX() + size.getX(),
                                                                   topLeft.getY() + size.getY());
        vertices = new float[]{
                0.0f, 1.0f, bottomLeft.getX().floatValue(), bottomLeft.getY().floatValue(),
                1.0f, 0.0f, topRight.getX().floatValue(), topRight.getY().floatValue(),
                0.0f, 0.0f, topLeft.getX().floatValue(), topLeft.getY().floatValue(),
                0.0f, 1.0f, bottomLeft.getX().floatValue(), bottomLeft.getY().floatValue(),
                1.0f, 1.0f, bottomRight.getX().floatValue(), bottomRight.getY().floatValue(),
                1.0f, 0.0f, topRight.getX().floatValue(), topRight.getY().floatValue()
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
