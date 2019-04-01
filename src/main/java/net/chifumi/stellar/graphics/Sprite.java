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

import net.chifumi.stellar.geometry.Polygon;
import net.chifumi.stellar.image.Texture;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Sprite implements TexturedDrawable, Polygon {
    private static final float HALF = 0.5f;
    private static final int TWO_RADIAN_DEGREE = 360;
    private final StaticPrimitive staticPrimitive;
    private Vector2f position;
    private Vector2f size;
    private Vector3f color; // TODO : Make color easier to use, may be something like (byte, byte, byte)
    private Matrix4f modelMatrix;
    private Quaternionf rotation;
    private Texture texture;

    public Sprite(final Texture texture) {
        position = new Vector2f();
        size = new Vector2f(texture.getWidth(), texture.getHeight());
        rotation = new Quaternionf();
        color = new Vector3f(1.0f, 1.0f, 1.0f);
        this.texture = texture;
        staticPrimitive = StaticPrimitive.RECT;
        updateModelMatrix();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(final Vector2fc position) {
        this.position = (Vector2f) position;
        updateModelMatrix();
    }

    public void setPosition(final float x, final float y) {
        position = new Vector2f(x, y);
        updateModelMatrix();
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(final Vector2fc size) {
        this.size = (Vector2f) size;
        updateModelMatrix();
    }

    public void setSize(final float width, final float height) {
        size = new Vector2f(width, height);
        updateModelMatrix();
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setRotation(final Quaternionfc rotation) {
        this.rotation = (Quaternionf) rotation;
        updateModelMatrix();
    }

    @SuppressWarnings("NumericCastThatLosesPrecision")
    public float getDegreesRotation() {
        float degree = (float) Math.toDegrees(rotation.angle());
        if (rotation.z <= 0 ^ rotation.w <= 0) {
            degree = TWO_RADIAN_DEGREE - degree;
        }
        return degree;
    }

    public void setDegreesRotation(final float angle) {
        rotation = new Quaternionf().fromAxisAngleDeg(new Vector3f(0.0f, 0.0f, 1.0f), angle);
        updateModelMatrix();
    }

    public void setColor(final float red, final float green, final float blue) {
        color = new Vector3f(red, green, blue);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(final Texture texture) {
        this.texture = texture;
    }

    @Override
    public Primitive getPrimitive() {
        return staticPrimitive;
    }

    @Override
    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    @Override
    public void setModelMatrix(final Matrix4fc modelMatrix) {
        this.modelMatrix = (Matrix4f) modelMatrix;
    }

    @Override
    public Vector3f getColor() {
        return color;
    }

    public void setColor(final Vector3fc color) {
        this.color = (Vector3f) color;
    }

    public List<Vector2f> getAbsoluteVertices() { // TODO : Use EBO, then transform vertices
        Vector4f localPosition;
        final List<Vector2f> result = new ArrayList<>();
        localPosition = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
        final Vector4f topLeft = localPosition.mul(modelMatrix);
        result.add(new Vector2f(topLeft.x, topLeft.y));
        localPosition = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
        final Vector4f topRight = localPosition.mul(modelMatrix);
        result.add(new Vector2f(topRight.x, topRight.y));
        localPosition = new Vector4f(1.0f, 1.0f, 0.0f, 1.0f);
        final Vector4f bottomRight = localPosition.mul(modelMatrix);
        result.add(new Vector2f(bottomRight.x, bottomRight.y));
        localPosition = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);
        final Vector4f bottomLeft = localPosition.mul(modelMatrix);
        result.add(new Vector2f(bottomLeft.x, bottomLeft.y));
        return result;
    }

    private void updateModelMatrix() {
        modelMatrix = new Matrix4f();
        modelMatrix = modelMatrix.translate(new Vector3f(position, 0.0f));
        modelMatrix = modelMatrix.translate(new Vector3f(HALF * size.x(), HALF * size.y(), 0.0F));
        modelMatrix = modelMatrix.rotate(rotation);
        modelMatrix = modelMatrix.translate(new Vector3f(-HALF * size.x(), -HALF * size.y(), 0.0F));
        modelMatrix.scale(new Vector3f(size, 1.0F));
    }
}
