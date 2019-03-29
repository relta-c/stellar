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

public class Line implements Sharp {
    private final Vector2f pointA;
    private final Vector2f pointB;

    public Line(final Vector2fc pointA, final Vector2fc pointB) {
        this.pointA = (Vector2f) pointA;
        this.pointB = (Vector2f) pointB;
    }

    public Line(final float pointAX, final float pointAY, final float pointBX, final float pointBY) {
        pointA = new Vector2f(pointAX, pointAY);
        pointB = new Vector2f(pointBX, pointBY);
    }

    public Vector2f getPointA() {
        return pointA;
    }

    public Vector2f getPointB() {
        return pointB;
    }

    @Override
    public Primitive getPrimitive() {
        return new LinePrimitive(pointA, pointB);
    }
}
