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

import net.chifumi.stellar.geometry.Line;

/**
 * @version 1.0.2
 * @since 1.0.0
 */
class LinePrimitive implements Primitive {
    private final float[] vertices;
    private final int verticesNum;
    private final int drawMode;


    /**
     * @param line
     *         a line for creating primitive
     *
     * @since 1.0.2
     */
    LinePrimitive(final Line line) {
        vertices = new float[]{
                line.getPointA().getX(), line.getPointA().getY(), 0.0f, 0.0f,
                line.getPointB().getX(), line.getPointB().getY(), 0.0f, 0.0f};
        verticesNum = 2;
        drawMode = DrawMode.LINE.getID();
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
