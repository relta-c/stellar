//    Copyright (C) 2019 Nattakit Hosapsin <delta@chifumi.net>
//
//    This file is part of Stellar
//    Stellar is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as
//    published by the Free Software Foundation, either
//    version 3 of the License, or (at your option) any later version.
//
//    Stellar is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with Stellar.  If not, see <https://www.gnu.org/licenses/lgpl.html>.

package net.chifumi.stellar.geometry;

import org.joml.Vector2f;
import org.joml.Vector2fc;

public class Circle implements Sharp {
    private final int face;
    private Vector2f origin;
    private float radius;

    @SuppressWarnings("NumericCastThatLosesPrecision")
    public Circle(final Vector2fc origin, final float radius) {
        this.origin = (Vector2f) origin;
        this.radius = radius;
        face = (int)radius;
    }

    public Vector2f getOrigin() {
        return origin;
    }

    public void setOrigin(final Vector2fc origin) {
        this.origin = (Vector2f) origin;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(final float radius) {
        this.radius = radius;
    }

    @Override
    public Primitive getPrimitive() {
        return new CirclePrimitive(origin, radius, face);
    }
}
