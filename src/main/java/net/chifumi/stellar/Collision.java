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

package net.chifumi.stellar;

import org.joml.Vector2f;
import org.joml.Vector2fc;

import java.util.ArrayList;
import java.util.List;

public enum  Collision {
    ;

    public static boolean checkCollision(final Convex convexA, final Convex convexB) {
        return checkSAT(convexA, convexB);
    }

    @SuppressWarnings("unused") // TODO : Use this for axis-aligned rectangle
    static boolean checkAABB(final Convex spriteA, final Convex spriteB) {
        return true;
    }

    static boolean checkSAT(final Convex convexA, final Convex convexB) {
        final List<Vector2f> axes = getAxis(convexA.getVertices());
        axes.addAll(getAxis(convexB.getVertices()));
        boolean result = true;
        for (final Vector2f axis : axes) {
            final Vector2f projectionA = project(axis, convexA.getVertices());
            final Vector2f projectionB = project(axis, convexB.getVertices());
            if (!isOverlap(projectionA, projectionB)) {
                result = false;
                break;
            }
        }
        return result;
    }

    static Vector2f project(final Vector2fc axis, final List<? extends Vector2f> vertices) {
        float min = axis.dot(vertices.get(0));
        float max = min;

        for (int i = 1; i < vertices.size(); i++) {
            final float point = axis.dot(vertices.get(i));
            if (point < min) {
                min = point;
            } else if (point > max) {
                max = point;
            }
        }
        return new Vector2f(min, max);
    }

    static List<Vector2f> getAxis(final List<? extends Vector2f> conner) { // TODO : optimize for rectangles
        final List<Vector2f> result = new   ArrayList<>();
        for (int i = 0; i < conner.size(); i++) {
            final Vector2f currentVertex = conner.get(i);
            final Vector2f nextVertex = conner.get((i + 1) % conner.size());
            final Vector2f edge = currentVertex.sub(nextVertex);
            final Vector2f normal = edge.perpendicular().normalize();
            result.add(normal);
        }
        return result;
    }

    static boolean isOverlap(final Vector2f projectionA, final Vector2f projectionB) {
        return !(projectionA.x > projectionB.y) && !(projectionB.x > projectionA.y);
    }
}
