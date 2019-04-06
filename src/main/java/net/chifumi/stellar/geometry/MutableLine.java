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

public class MutableLine implements Line {
    private final MutableVector2<Float> pointA;
    private final MutableVector2<Float> pointB;

    public MutableLine(final Vector2<Float> pointA, final Vector2<Float> pointB) {
        this.pointA = new MutableVector2<>(pointA);
        this.pointB = new MutableVector2<>(pointB);
    }

    public MutableLine(final float pointAX, final float pointAY, final float pointBX, final float pointBY) {
        pointA = new MutableVector2<>(pointAX, pointAY);
        pointB = new MutableVector2<>(pointBX, pointBX);
    }

    @Override
    public Vector2<Float> getPointA() {
        return pointA;
    }

    public void setPointA(final Vector2<Float> pointA) {
        this.pointA.set(pointA);
    }

    @Override
    public Vector2<Float> getPointB() {
        return pointB;
    }

    public void setPointB(final Vector2<Float> pointB) {
        this.pointB.set(pointB);
    }

    public void set(final Vector2<Float> pointA, final Vector2<Float> pointB) {
        this.pointA.set(pointA);
        this.pointB.set(pointB);
    }

    public void set(final float pointAX, final float pointAY, final float pointBX, final float pointBY) {
        pointA.set(pointAX, pointAY);
        pointB.set(pointBX, pointBX);
    }

    public void setPointA(final float pointAX, final float pointAY) {
        pointA.set(pointAX, pointAY);
    }

    public void setPointB(final float pointBX, final float pointBY) {
        pointB.set(pointBX, pointBY);
    }
}
