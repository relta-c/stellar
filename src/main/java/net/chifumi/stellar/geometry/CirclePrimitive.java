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

package net.chifumi.stellar.geometry;

import org.joml.Vector2fc;

class CirclePrimitive implements Primitive {
    private final int drawMode;
    private float[] vertices;
    private int verticesNum;

    CirclePrimitive(final Vector2fc origin, final float radius, final int side) {
        drawMode = PrimitiveDrawMode.TRIANGLE_FAN.getId();
        createVertices(origin, radius, side);
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

    @SuppressWarnings("NumericCastThatLosesPrecision")
    private void createVertices(final Vector2fc origin, final float radius, final int side) {
        verticesNum = side + 2;
        final double doublePi = 2 * Math.PI;

        final double[] verticesX = new double[verticesNum];
        final double[] verticesY = new double[verticesNum];

        verticesX[0] = origin.x();
        verticesY[0] = origin.y();

        for (int i = 1; i < verticesNum; i++) {
            verticesX[i] = origin.x() + (radius * StrictMath.cos(i * doublePi / side));
            verticesY[i] = origin.y() - (radius * StrictMath.sin(i * doublePi / side));
        }

        vertices = new float[verticesNum * 4];
        for (int i = 0; i < verticesNum; i++) {
            final int index = i * 4;
            vertices[index] = (float) verticesX[i];
            vertices[index + 1] = (float) verticesY[i];
            vertices[index + 2] = 0;
            vertices[index + 3] = 0;
        }
    }
}
