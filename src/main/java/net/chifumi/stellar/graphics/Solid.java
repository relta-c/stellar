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

import net.chifumi.stellar.geometry.Primitive;
import net.chifumi.stellar.geometry.Sharp;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Solid implements Drawable {
    private Sharp sharp;
    private Primitive primitive;
    private Vector3f color;
    private Matrix4f modelMatrix;

    public Solid(final Sharp sharp) {
        this.sharp = sharp;
        primitive = sharp.getPrimitive();
        modelMatrix = new Matrix4f();
        color = new Vector3f();
    }

    public Sharp getSharp() {
        return sharp;
    }

    public void setSharp(final Sharp sharp) {
        this.sharp = sharp;
        primitive = sharp.getPrimitive();
    }

    public void setColor(final float red, final float green, final float blue) {
        color = new Vector3f(red, green, blue);
    }

    @Override
    public Primitive getPrimitive() {
        return primitive;
    }

    @Override
    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

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
}
