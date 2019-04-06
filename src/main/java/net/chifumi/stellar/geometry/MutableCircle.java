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

import net.chifumi.stellar.math.MutableVector2;
import net.chifumi.stellar.math.Vector2;

public class MutableCircle implements Circle {
    private final MutableVector2<Float> origin;
    private float radius;

    public MutableCircle(final Vector2<Float> origin, final float radius) {
        this.origin = new MutableVector2<>(origin.getX(), origin.getY());
        this.radius = radius;
    }


    @Override
    public Vector2<Float> getOrigin() {
        return origin;
    }

    public void setOrigin(final Vector2<Float> origin) {
        this.origin.set(origin);
    }

    public void setOrigin(final float x, final float y) {
        origin.set(x, y);
    }

    @Override
    public float getRadius() {
        return radius;
    }

    public void setRadius(final float radius) {
        this.radius = radius;
    }
}
