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

import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Set of utility methods for checking collision.
 * <p>This class implements mostly using SAT.</p>
 *
 * @author Nattakit Hosapsin
 * @version 1.0.0
 * @see <a href="http://www.dyn4j.org/2010/01/sat/">SAT (Separating Axis Theorem)</a>
 * @since 1.0.0
 */
public enum Collision {
    ;

    /**
     * Check if two polygons are colliding.
     *
     * @param polygonA
     *         first polygon to check for collision
     * @param polygonB
     *         polygon b
     *
     * @return collision status
     *
     * @since 1.0.0
     */
    public static boolean check(final Polygon polygonA, final Polygon polygonB) { // TODO : MTV
        final List<Vector2<Float>> axes = getAxis(polygonA.getVertices());
        axes.addAll(getAxis(polygonB.getVertices()));
        boolean result = true;
        for (final Vector2<Float> axis : axes) {
            final Vector2<Float> projectionA = project(axis, polygonA.getVertices());
            final Vector2<Float> projectionB = project(axis, polygonB.getVertices());
            if (areOverlap(projectionA, projectionB)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Check if a polygon and a circle are colliding.
     *
     * @param polygon
     *         polygon
     * @param circle
     *         circle
     *
     * @return collision status
     *
     * @since 1.0.0
     */
    public static boolean check(final Polygon polygon, final Circle circle) {
        final List<Vector2<Float>> axes = getCircleAxis(circle.getOrigin(), polygon);
        boolean result = true;
        for (final Vector2<Float> axis : axes) {
            final Vector2<Float> projectionA = project(axis, polygon.getVertices());
            final Vector2<Float> projectionB = projectCircle(axis, circle);
            if (areOverlap(projectionA, projectionB)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Check if two circle are colliding.
     *
     * @param circleA
     *         circle a
     * @param circleB
     *         circle b
     *
     * @return collision status
     *
     * @since 1.0.0
     */
    public static boolean check(final Circle circleA, final Circle circleB) {
        final Vector2<Float> originB = circleB.getOrigin();
        final Vector2<Float> subVector = originB.sub(circleA.getOrigin());
        final float originDistant = subVector.length();
        final float radiusSum = circleA.getRadius() + circleB.getRadius();

        return originDistant < radiusSum;
    }

    /**
     * Project vertices to axis and get vertices with minimum and maximum value on axis.
     *
     * @param axis
     *         axis to project to
     * @param vertices
     *         vertices to project
     *
     * @return vertices with minimum and maximum value on axis
     *
     * @since 1.0.2
     */
    private static Vector2<Float> project(final Vector2<Float> axis, final List<? extends Vector2<Float>> vertices) {
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
        return new ImmutableVector2<>(min, max);
    }

    /**
     * Project circle to axis and get vertices with minimum and maximum value on axis.
     *
     * @param axis
     *         axis to project to
     * @param circle
     *         circle to project
     *
     * @return vertices with minimum and maximum value on axis
     *
     * @since 1.0.2
     */
    private static Vector2<Float> projectCircle(final Vector2<Float> axis, final Circle circle) {
        final float projection = axis.dot(circle.getOrigin());
        return new ImmutableVector2<>(projection - circle.getRadius(), projection + circle.getRadius());
    }


    /**
     * Get axis to project.
     *
     * @param conner
     *         conner of sharpe
     *
     * @return axis for projecting
     *
     * @since 1.0.2
     */
    private static List<Vector2<Float>> getAxis(final List<? extends Vector2<Float>> conner) { // TODO : optimize for rectangles
        final List<Vector2<Float>> result = new ArrayList<>();
        for (int i = 0; i < conner.size(); i++) {
            final Vector2<Float> currentVertex = conner.get(i);
            final Vector2<Float> nextVertex = conner.get((i + 1) % conner.size());
            final Vector2<Float> edge = currentVertex.sub(nextVertex);
            final Vector2<Float> normal = edge.perpendicular();
            result.add(normal);
        }
        return result;
    }

    /**
     * Get axis to project from a circle.
     *
     * @param origin
     *         circle origin position
     * @param polygon
     *         polygon to test with
     *
     * @return axis for projecting
     *
     * @since 1.0.2
     */
    private static List<Vector2<Float>> getCircleAxis(final Vector2<Float> origin, final Polygon polygon) {
        final List<Vector2<Float>> result = new ArrayList<>();
        Vector2<Float> circleAxis = null;
        for (int i = 0; i < polygon.getVertices()
                .size(); i++) { // TODO : Optimize this (Beware, Vector is mutable)
            final Vector2<Float> currentVertex = polygon.getVertices().get(i);
            final Vector2<Float> nextVertex = polygon.getVertices()
                    .get((i + 1) % polygon.getVertices().size());
            final Vector2<Float> edge = currentVertex.sub(nextVertex);
            final Vector2<Float> normal = edge.perpendicular();
            result.add(normal.normalize());

            final Vector2<Float> vertexToCircle = polygon.getVertices().get(i).sub(origin);
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


    /**
     * Check if projection of sharpe a and sharpe b are overlapping
     *
     * @param projectionA
     *         projection of sharpe a
     * @param projectionB
     *         projection of sharpe b
     *
     * @return are overlapping
     *
     * @since 1.0.2
     */
    private static boolean areOverlap(final Vector2<Float> projectionA,
                                      final Vector2<Float> projectionB) {
        return projectionA.getX() > projectionB.getY() || projectionB.getX() > projectionA.getY();
    }
}
