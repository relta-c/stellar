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
import net.chifumi.stellar.geometry.MutableCircle;
import net.chifumi.stellar.math.Vector2;
import org.joml.Matrix4f;
import org.joml.Vector3f;


/**
 * Represents circle that can be draw on screen.
 * <p>This sharpe is not actually a circle but a convex shapes with many sides.</p>
 *
 * @author Nattakit Hosapsin
 * @version 1.0.2
 * @since 1.0.1
 */
public class DrawableCircle extends DrawableObject implements Circle {
    private final MutableCircle circle;
    private int face;

    /**
     * Create a new {@link net.chifumi.stellar.graphics.DrawableCircle}.
     *
     * @param origin
     *         origin location
     * @param radius
     *         radius of circle
     *
     * @since 1.0.2
     */
    public DrawableCircle(final Vector2<Float> origin, final float radius) {
        this(origin, radius, (int) radius);
    }

    /**
     * Create a new {@link net.chifumi.stellar.graphics.DrawableCircle}.
     *
     * @param origin
     *         origin location
     * @param radius
     *         radius of circle
     * @param face
     *         sharp face number
     *         <p>this value should be high enough for circle to look smooth</p>
     *
     * @since 1.0.2
     */
    public DrawableCircle(final Vector2<Float> origin, final float radius, final int face) {
        super(new CirclePrimitive(face));
        this.face = face;
        circle = new MutableCircle(origin, radius);
    }

    /**
     * Get face number.
     *
     * @return face of sharpe
     *
     * @since 1.0.0
     */
    public int getFace() {
        return face;
    }

    /**
     * Set face number.
     *
     * @param face
     *         face of sharpe
     *
     * @since 1.0.0
     */
    public void setFace(final int face) {
        this.face = face;
    }

    @Override
    public Vector2<Float> getOrigin() {
        return circle.getOrigin();
    }

    /**
     * Set circle origin position
     *
     * @param origin
     *         origin location
     *
     * @since 1.0.2
     */
    public void setOrigin(final Vector2<Float> origin) {
        circle.setOrigin(origin);
    }

    /**
     * Set circle origin position
     *
     * @param x
     *         x-axis position of origin
     * @param y
     *         y-axis position of origin
     *
     * @since 1.0.1
     */
    public void setOrigin(final float x, final float y) {
        circle.setOrigin(x, y);
    }

    @Override
    public float getRadius() {
        return circle.getRadius();
    }

    /**
     * Set radius of circle
     *
     * @param radius
     *         radius of circle
     *
     * @since 1.0.1
     */
    public void SetRadius(final float radius) {
        circle.setRadius(radius);
    }


    @Override
    protected void updateModelMatrix() {
        final Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.translate(new Vector3f(circle.getOrigin().getX(), circle.getOrigin().getY(), 0.0f));
        modelMatrix.scale(new Vector3f(circle.getRadius(), circle.getRadius(), 1.0F));
        setModelMatrix(modelMatrix);
    }
}
