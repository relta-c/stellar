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

import net.chifumi.stellar.geometry.Line;
import net.chifumi.stellar.geometry.MutableLine;
import net.chifumi.stellar.math.Vector2;
import org.joml.Matrix4f;

/**
 * @author Nattakit Hosapsin
 * @version 1.0.2
 * @since 1.0.1
 */
public class DrawableLine extends DrawableObject implements Line {
    private final MutableLine line;

    /**
     * @param pointA
     *         point a position
     * @param pointB
     *         point b position
     *
     * @since 1.0.2
     */
    public DrawableLine(final Vector2<Float> pointA, final Vector2<Float> pointB) {
        super(new LinePrimitive(new MutableLine(pointA, pointB)));
        line = new MutableLine(pointA, pointB);
    }

    /**
     * @param ax
     *         point a point a x-axis position
     * @param ay
     *         point a point a y-axis position
     * @param bx
     *         point b point a x-axis position
     * @param by
     *         point b point a y-axis position
     *
     * @since 1.0.1
     */
    public DrawableLine(final float ax, final float ay, final float bx, final float by) {
        super(new LinePrimitive(new MutableLine(ax, ay, bx, by)));
        line = new MutableLine(ax, ay, bx, by);
    }

    /**
     * @return point a position
     *
     * @since 1.0.2
     */
    public Vector2<Float> getPointA() {
        return line.getPointA();
    }

    /**
     * @param pointA
     *         point a position
     *
     * @since 1.0.2
     */
    public void setPointA(final Vector2<Float> pointA) {
        line.setPointA(pointA);
        updatePrimitive();
    }

    /**
     * @param x
     *         point a x-axis position
     * @param y
     *         point a y-axis position
     *
     * @since 1.0.1
     */
    public void setPointA(final float x, final float y) {
        line.setPointA(x, y);
        updatePrimitive();
    }

    @Override
    public Vector2<Float> getPointB() {
        return line.getPointB();
    }

    /**
     * @param pointB
     *         point b position
     *
     * @since 1.0.2
     */
    public void setPointB(final Vector2<Float> pointB) {
        line.setPointB(pointB);
        updatePrimitive();
    }

    /**
     * @param x
     *         point b x-axis position
     * @param y
     *         point b x-axis position
     *
     * @since 1.0.1
     */
    public void setPointB(final float x, final float y) {
        line.setPointB(x, y);
        updatePrimitive();
    }

    @Override
    protected void updateModelMatrix() {
        setModelMatrix(new Matrix4f());
    }

    /**
     * @since 1.0.1
     */
    private void updatePrimitive() {
        setPrimitive(new LinePrimitive(line));
    }
}
