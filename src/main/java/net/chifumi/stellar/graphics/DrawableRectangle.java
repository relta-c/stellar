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

import net.chifumi.stellar.geometry.MutableRectangle;
import net.chifumi.stellar.geometry.Polygon;
import net.chifumi.stellar.geometry.Rectangle;
import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.Vector2;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0.3
 * @since 1.0.2
 */
public class DrawableRectangle extends DrawableObject implements Rectangle, Polygon {
    private static final float HALF = 0.5f;
    private static final int DEGREE_IN_TWO_RADIAN = 360;
    private final MutableRectangle rectangle;
    private Quaternionf rotation;

    public DrawableRectangle(final Vector2<Float> position, final Vector2<Float> size) {
        super(RectanglePrimitive.INSTANCE);
        rectangle = new MutableRectangle(position, size);
        rotation = new Quaternionf();
    }

    public DrawableRectangle(final float x, final float y, final float width, final float height) {
        super(RectanglePrimitive.INSTANCE);
        rectangle = new MutableRectangle(x, y, width, height);
        rotation = new Quaternionf();
    }

    DrawableRectangle(final Vector2<Float> position, final Vector2<Float> size, final Primitive primitive) {
        super(primitive);
        rectangle = new MutableRectangle(position, size);
        rotation = new Quaternionf();
    }

    @Override
    public Vector2<Float> getPosition() {
        return rectangle.getPosition();
    }

    public void setPosition(final Vector2<Float> position) {
        rectangle.setPosition(position);
        updateModelMatrix();
    }

    public void setPosition(final float x, final float y) {
        rectangle.setPosition(x, y);
        updateModelMatrix();
    }

    @Override
    public Vector2<Float> getSize() {
        return rectangle.getSize();
    }

    public void setSize(final Vector2<Float> size) {
        rectangle.setSize(size);
        updateModelMatrix();
    }

    public void setSize(final float width, final float height) {
        rectangle.setSize(width, height);
        updateModelMatrix();
    }

    /**
     * @return the angle of rotation in degree
     *
     * @since 1.0.3
     */
    @SuppressWarnings("NumericCastThatLosesPrecision")
    public float getRotation() {
        float degree = (float) Math.toDegrees(rotation.angle());
        if (rotation.z <= 0 ^ rotation.w <= 0) {
            degree = DEGREE_IN_TWO_RADIAN - degree;
        }
        return degree;
    }

    /**
     * Set angle of rotation in degree
     *
     * @param angle
     *         the angle of rotation in degree
     *
     * @since 1.0.3
     */
    public void setRotation(final float angle) {
        rotation = new Quaternionf().fromAxisAngleDeg(new Vector3f(0.0f, 0.0f, 1.0f), angle);
        updateModelMatrix();
    }

    @Override
    public List<Vector2<Float>> getVertices() {
        Vector4f localPosition;
        final List<Vector2<Float>> result = new ArrayList<>();
        localPosition = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
        final Vector4f topLeft = localPosition.mul(getModelMatrix());
        result.add(new ImmutableVector2<>(topLeft.x, topLeft.y));
        localPosition = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
        final Vector4f topRight = localPosition.mul(getModelMatrix());
        result.add(new ImmutableVector2<>(topRight.x, topRight.y));
        localPosition = new Vector4f(1.0f, 1.0f, 0.0f, 1.0f);
        final Vector4f bottomRight = localPosition.mul(getModelMatrix());
        result.add(new ImmutableVector2<>(bottomRight.x, bottomRight.y));
        localPosition = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);
        final Vector4f bottomLeft = localPosition.mul(getModelMatrix());
        result.add(new ImmutableVector2<>(bottomLeft.x, bottomLeft.y));
        return result;
    }

    @Override
    protected void updateModelMatrix() {
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix = modelMatrix.translate(new Vector3f(getPosition().getX(), getPosition().getY(), 0.0f));
        modelMatrix = modelMatrix.translate(new Vector3f(HALF * getSize().getX(), HALF * getSize().getY(), 0.0F));
        modelMatrix = modelMatrix.rotate(rotation);
        modelMatrix = modelMatrix.translate(new Vector3f(-HALF * getSize().getX(), -HALF * getSize().getY(), 0.0F));
        modelMatrix.scale(new Vector3f(getSize().getX(), getSize().getY(), 1.0F));
        setModelMatrix(modelMatrix);
    }
}
