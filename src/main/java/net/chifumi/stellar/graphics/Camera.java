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

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.joml.Vector3f;

/**
 * Represent game camera.
 * <p>Can be used to manipulate game view</p>
 *
 * @author Nattakit Hosapsin
 * @version 1.0.0
 * @since 1.0.0
 */
public class Camera {
    private static final int TWO_RADIAN_DEGREE = 360;
    private static final float HALF = 0.5F;
    /**
     * Camera resolution
     * <p>Should be same as display resolution.</p>
     */
    private final Vector2i resolution;
    /**
     * Camera zoom level
     */
    private float zoom;
    /**
     * Camera position
     */
    private Vector2f position;
    /**
     * View transform matrix
     * <p>Use to transform world space to view space.</p>
     */
    private Matrix4f viewMatrix;
    /**
     * Projection transform matrix
     * <p>Use to transform view space to clip space.</p>
     */
    private Matrix4f projectionMatrix;
    /**
     * Camera rotation
     */
    private Quaternionf rotation;

    /**
     * Create a new {@link net.chifumi.stellar.graphics.Camera} with supplied resolution.
     *
     * @param resolution
     *         resolution of camera
     *
     * @since 1.0.0
     */
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

    /**
     * Get camera position.
     *
     * @return camera position
     *
     * @since 1.0.0
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * Set new camera position.
     *
     * @param position
     *         new camera position in {@link org.joml.Vector2f}
     *
     * @since 1.0.0
     */
    public void setPosition(final Vector2fc position) {
        this.position = (Vector2f) position;
    }


    /**
     * Set new camera position.
     *
     * @param x
     *         x-axis offset of new position
     * @param y
     *         y-axis offset of new position
     *
     * @since 1.0.0
     */
    public void setPosition(final float x, final float y) {
        setPosition(new Vector2f(x, y));
    }

    /**
     * Get rotation in {@link org.joml.Quaternionf}.
     *
     * @return quaternion rotation
     *
     * @since 1.0.0
     */
    public Quaternionf getRotation() {
        return rotation;
    }

    /**
     * Set rotation in quaternion.
     *
     * @param rotation
     *         quaternion rotation
     *
     * @since 1.0.0
     */
    public void setRotation(final Quaternionfc rotation) {
        this.rotation = (Quaternionf) rotation;
    }

    /**
     * Get rotation in degree.
     *
     * @return degree rotation
     *
     * @since 1.0.0
     */
    @SuppressWarnings("NumericCastThatLosesPrecision")
    public float getDegreesRotation() {
        float degree = (float) Math.toDegrees(rotation.angle());
        if (rotation.z <= 0 ^ rotation.w <= 0) {
            degree = TWO_RADIAN_DEGREE - degree;
        }
        return degree;
    }

    /**
     * Set rotation in degree.
     *
     * @param angle
     *         rotation in degree
     *
     * @since 1.0.0
     */
    public void setDegreesRotation(final float angle) {
        rotation = new Quaternionf().fromAxisAngleDeg(new Vector3f(0.0f, 0.0f, 1.0f), angle);
    }

    /**
     * Get zoom level.
     *
     * @return zoom level
     *
     * @since 1.0.0
     */
    public float getZoom() {
        return zoom;
    }

    /**
     * Set zoom level.
     *
     * @param zoom
     *         zoom level
     *
     * @since 1.0.0
     */
    public void setZoom(final float zoom) {
        this.zoom = zoom;
    }

    /**
     * Get view matrix.
     *
     * @return view matrix
     */
    Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    /**
     * Get projection matrix.
     *
     * @return projection matrix
     */
    Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * Update view matrix with new parameter.
     */
    void updateViewMatrix() {
        viewMatrix = new Matrix4f();
        viewMatrix.translate(new Vector3f(-position.x, -position.y, 0.0f));
        viewMatrix.translate(new Vector3f(HALF * resolution.x, HALF * resolution.y, 0.0f));
        viewMatrix = viewMatrix.rotate(rotation);
        viewMatrix.translate(new Vector3f(-HALF * resolution.x, -HALF * resolution.y, 0.0f));
    }

    /**
     * Update projection matrix with new parameter.
     */
    void updateProjectionMatrix() {
        projectionMatrix = new Matrix4f().ortho2D(
                0.0F - getZoomX(),
                resolution.x + getZoomX(),
                resolution.y + getZoomY(),
                0.0F - getZoomY());
    }

    /**
     * Get x-axis offset for current zoom level, use for updating projection matrix.
     *
     * @return x-axis offset
     */
    private float getZoomX() {
        return (resolution.x * zoom) - resolution.x;
    }

    /**
     * Get y-axis offset for current zoom level,
     * use for updating projection matrix.
     *
     * @return y-axis offset
     */
    private float getZoomY() {
        return (resolution.y * zoom) - resolution.y;
    }
}
