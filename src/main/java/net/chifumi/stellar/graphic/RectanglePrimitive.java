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

enum RectanglePrimitive implements Primitive { // TODO : Use EBO
    INSTANCE;

    private final float[] vertices;
    private final int verticesNum;
    private final int drawMode;

    RectanglePrimitive() {
        vertices = new float[]{
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f};
        verticesNum = 6;
        drawMode = DrawMode.TRIANGLES.getID();
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
