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

import org.joml.*;

public class Camera {
    private static final float HALF_LEN = 0.5F;
    private final Vector2i resolution;
    private Vector2f position;
    private Quaternionf rotation;
    private float zoom;
    private Matrix4f view;
    private Matrix4f projection;

    @SuppressWarnings("WeakerAccess")
    public Camera(final Vector2i resolution) {
        view = new Matrix4f();
        projection = new Matrix4f();
        position = new Vector2f();
        this.resolution = resolution;
        rotation = new Quaternionf();
        zoom = 1.0F;
        updateViewMatrix();
        updateProjectionMatrix();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(final Vector2f position) {
        this.position = position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public float getDegreesRotation() {
        return rotation.angle(); // TODO : This is radian
    }

    public void setRotation(final Quaternionf rotation) {
        this.rotation = rotation;
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

    float getZoomX() {
        return (resolution.x * zoom) - resolution.x;
    }

    float getZoomY() {
        return (resolution.y * zoom) - resolution.y;
    }

    Matrix4f getView() {
        return view;
    }

    Matrix4f getProjection() {
        return projection;
    }

    void updateViewMatrix() {
        view = new Matrix4f();
        view.translate(new Vector3f(-position.x, -position.y, 0.0f));
        view.translate(new Vector3f(HALF_LEN * resolution.x, HALF_LEN * resolution.y, 0.0f));
        view = view.rotate(rotation);
        view.translate(new Vector3f(-HALF_LEN * resolution.x, -HALF_LEN * resolution.y, 0.0f));
    }

    void updateProjectionMatrix() {
        projection = new Matrix4f().ortho(
                0.0F - getZoomX(),
                resolution.x + getZoomX(),
                resolution.y + getZoomY(),
                0.0F - getZoomY(),
                -1.0F,
                1.0F);
    }
}
