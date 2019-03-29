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

package net.chifumi.stellar.collision;

import net.chifumi.stellar.geometry.Circle;
import org.joml.Vector2f;
import org.joml.Vector2fc;

import java.util.ArrayList;
import java.util.List;

public enum SAT {
    ;

    public static boolean check(final Polygon polygonA, final Polygon polygonB) { // TODO : MTV
        final List<Vector2f> axes = getAxis(polygonA.getVertices());
        axes.addAll(getAxis(polygonB.getVertices()));
        boolean result = true;
        for (final Vector2f axis : axes) {
            final Vector2f projectionA = project(axis, polygonA.getVertices());
            final Vector2f projectionB = project(axis, polygonB.getVertices());
            if (isOverlap(projectionA, projectionB)) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean check(final Polygon polygonA, final Circle circle) {
        final List<Vector2f> axes = getCircleAxis(circle.getOrigin(), polygonA);
        boolean result = true;
        for (final Vector2fc axis : axes) {
            final Vector2fc projectionA = project(axis, polygonA.getVertices());
            final Vector2fc projectionB = projectCircle(axis, circle);
            if (isOverlap(projectionA, projectionB)) {
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

    static Vector2f projectCircle(final Vector2fc axis, final Circle circle) {
        final float projection = axis.dot(circle.getOrigin());
        return new Vector2f(projection - circle.getRadius(), projection + circle.getRadius());
    }

    static List<Vector2f> getAxis(final List<? extends Vector2f> conner) { // TODO : optimize for rectangles
        final List<Vector2f> result = new ArrayList<>();
        for (int i = 0; i < conner.size(); i++) {
            final Vector2f currentVertex = conner.get(i);
            final Vector2f nextVertex = conner.get((i + 1) % conner.size());
            final Vector2f edge = currentVertex.sub(nextVertex);
            final Vector2f normal = edge.perpendicular();
            result.add(normal);
        }
        return result;
    }

    static List<Vector2f>  getCircleAxis(final Vector2fc origin, final Polygon polygon) {
        final List<Vector2f> result = new ArrayList<>();
        Vector2f circleAxis = null;
        for (int i = 0; i < polygon.getVertices().size(); i++) { // TODO : Optimize this (Beware, Vector is mutable)
            final Vector2f currentVertex = polygon.getVertices().get(i);
            final Vector2f nextVertex = polygon.getVertices().get((i + 1) % polygon.getVertices().size());
            final Vector2f edge = currentVertex.sub(nextVertex);
            final Vector2f normal = edge.perpendicular();
            result.add(normal.normalize());

            final Vector2f vertexToCircle = polygon.getVertices().get(i).sub(origin);
            if (circleAxis != null) {
                if (vertexToCircle.lengthSquared() < circleAxis.lengthSquared()) {
                    circleAxis = vertexToCircle;
                }
            } else {
                circleAxis = vertexToCircle;
            }
        }
        assert circleAxis != null;
        result.add(circleAxis.normalize());
        return result;
    }

    static boolean isOverlap(final Vector2fc projectionA, final Vector2fc projectionB) {
        return projectionA.x() > projectionB.y() || projectionB.x() > projectionA.y();
    }
}
