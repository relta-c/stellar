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

import org.joml.Vector2f;
import org.joml.Vector2fc;

public class LineSolid extends Solid {
    private Vector2f pointA;
    private Vector2f pointB;

    public LineSolid(final Vector2fc pointA, final Vector2fc pointB) {
        super(new LinePrimitive(pointA, pointB));
        this.pointA = (Vector2f) pointA;
        this.pointB = (Vector2f) pointB;
    }

    public LineSolid(final float ax, final float ay, final float bx, final float by) {
        super(new LinePrimitive(new Vector2f(ax, ay), new Vector2f(bx, by)));
        pointA = new Vector2f(ax, ay);
        pointB = new Vector2f(bx, by);
    }

    public Vector2f getPointA() {
        return pointA;
    }

    public void setPointA(final Vector2fc pointA) {
        this.pointA = (Vector2f) pointA;
        updatePrimitive();
    }

    public void setPointA(final float x, final float y) {
        pointA = new Vector2f(x, y);
        updatePrimitive();
    }

    public Vector2f getPointB() {
        return pointB;
    }

    public void setPointB(final Vector2fc pointB) {
        this.pointB = (Vector2f) pointB;
        updatePrimitive();
    }

    public void setPointB(final float x, final float y) {
        pointB = new Vector2f(x, y);
        updatePrimitive();
    }

    @Override
    protected void updateModelMatrix() {
    }

    private void updatePrimitive() {
        setPrimitive(new LinePrimitive(pointA, pointB));
    }
}
