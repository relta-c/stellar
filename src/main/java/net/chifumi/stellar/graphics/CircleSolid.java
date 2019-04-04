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

import net.chifumi.stellar.geometry.Circle;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;


public class CircleSolid extends Solid {
    private int face;
    private Circle circle;

    public CircleSolid(final Vector2fc origin, final float radius, final int face) {
        super(new CirclePrimitive(face));
        this.face = face;
        circle = new Circle(origin, radius);
    }

    public Circle getSharp() {
        return circle;
    }

    public void setSharp(final Circle circle) {
        this.circle = circle;
    }

    public int getFace() {
        return face;
    }

    public void setFace(final int face) {
        this.face = face;
    }

    public Vector2f getOrigin() {
        return circle.getOrigin();
    }

    public void setOrigin(final Vector2fc origin) {
        circle = new Circle(origin, circle.getRadius());
    }

    public void setOrigin(final float x, final float y) {
        circle = new Circle(new Vector2f(x, y), circle.getRadius());
    }

    public float getRadius() {
        return circle.getRadius();
    }

    public void SetRadius(final float radius) {
        circle = new Circle(circle.getOrigin(), radius);
    }


    @Override
    protected void updateModelMatrix() {
        final Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.translate(new Vector3f(circle.getOrigin(), 0.0f));
        modelMatrix.scale(new Vector3f(circle.getRadius(), circle.getRadius(), 1.0F));
        setModelMatrix(modelMatrix);
    }
}
