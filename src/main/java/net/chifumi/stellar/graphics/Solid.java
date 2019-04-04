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

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public abstract class Solid implements Drawable {
    private Primitive primitive;
    private Vector3f color;
    private Matrix4f modelMatrix;

    Solid(final Primitive primitive) {
        this.primitive = primitive;
        modelMatrix = new Matrix4f();
        color = new Vector3f(1.0f, 1.0f, 1.0f);
    }

    @Override
    public void setColor(final float red, final float green, final float blue) {
        color = new Vector3f(red / RGB_MAX, green / RGB_MAX, blue / RGB_MAX);
    }

    @Override
    public Matrix4f getModelMatrix() {
        updateModelMatrix();
        return modelMatrix;
    }

    @Override
    public void setModelMatrix(final Matrix4fc modelMatrix) {
        this.modelMatrix = (Matrix4f) modelMatrix;
    }

    @Override
    public Vector3f getColor() {
        return color;
    }

    public void setColor(final Vector3fc color) {
        this.color = (Vector3f) color;
    }

    @Override
    public Primitive getPrimitive() {
        return primitive;
    }

    void setPrimitive(final Primitive primitive) {
        this.primitive = primitive;
    }

    protected abstract void updateModelMatrix();
}
