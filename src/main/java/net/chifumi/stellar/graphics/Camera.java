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

import org.joml.*;

import java.lang.Math;

public class Camera {
    private static final int TWO_RADIAN_DEGREE = 360;
    private static final float HALF = 0.5F;
    private final Vector2i resolution;
    private float zoom;
    private Vector2f position;
    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;
    private Quaternionf rotation;

    public Camera(final Vector2ic resolution) {
        this.resolution = (Vector2i) resolution;
        position = new Vector2f();
        viewMatrix = new Matrix4f();
        projectionMatrix = new Matrix4f();
        rotation = new Quaternionf();
        zoom = 1.0F;
        updateViewMatrix();
        updateProjectionMatrix();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(final Vector2fc position) {
        this.position = (Vector2f) position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setRotation(final Quaternionfc rotation) {
        this.rotation = (Quaternionf) rotation;
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
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(final float zoom) {
        this.zoom = zoom;
    }

    Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    void updateViewMatrix() {
        viewMatrix = new Matrix4f();
        viewMatrix.translate(new Vector3f(-position.x, -position.y, 0.0f));
        viewMatrix.translate(new Vector3f(HALF * resolution.x, HALF * resolution.y, 0.0f));
        viewMatrix = viewMatrix.rotate(rotation);
        viewMatrix.translate(new Vector3f(-HALF * resolution.x, -HALF * resolution.y, 0.0f));
    }

    void updateProjectionMatrix() {
        projectionMatrix = new Matrix4f().ortho2D(
                0.0F - getZoomX(),
                resolution.x + getZoomX(),
                resolution.y + getZoomY(),
                0.0F - getZoomY());
    }

    private float getZoomX() {
        return (resolution.x * zoom) - resolution.x;
    }

    private float getZoomY() {
        return (resolution.y * zoom) - resolution.y;
    }
}
