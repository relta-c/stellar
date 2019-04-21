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

package net.chifumi.stellar.graphic;

import net.chifumi.stellar.math.ImmutableVector3;
import net.chifumi.stellar.math.MutableVector3;
import net.chifumi.stellar.math.Vector3;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

/**
 * Represents drawable object without texture.
 *
 * @author Nattakit Hosapsin
 * @version 1.0.2
 * @since 1.0.1
 */
public abstract class DrawableObject implements Drawable {
    private final MutableVector3<Float> color;
    private Primitive primitive;
    private float transparency;
    private boolean visible;
    private Matrix4f modelMatrix;

    /**
     * @param primitive
     *         primitive
     *
     * @since 1.0.1
     */
    protected DrawableObject(final Primitive primitive) {
        this.primitive = primitive;
        visible = true;
        modelMatrix = new Matrix4f();
        transparency = MAX_ALPHA;
        color = new MutableVector3<>(MAX_RGB, MAX_RGB, MAX_RGB);
    }

    @Override
    public void setColor(final float red, final float green, final float blue) {
        float setRed = red > MAX_RGB ? MAX_RGB : red;
        setRed = setRed < 0 ? 0 : red;
        float setGreen = green > MAX_RGB ? MAX_RGB : green;
        setGreen = setGreen < 0 ? 0 : green;
        float setBlue = blue > MAX_RGB ? MAX_RGB : blue;
        setBlue = setBlue < 0 ? 0 : blue;
        color.set(setRed, setGreen, setBlue);
    }

    @Override
    public float getTransparency() {
        return transparency;
    }

    @Override
    public void setTransparency(final float transparency) {
        float setTransparency = transparency > MAX_ALPHA ? MAX_ALPHA : transparency;
        setTransparency = setTransparency < 0 ? 0 : setTransparency;
        this.transparency = setTransparency;
    }

    @Override
    public float getNormalizedTransparency() {
        return transparency / MAX_ALPHA;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    @Override
    public Matrix4f getModelMatrix() {
        updateModelMatrix();
        return modelMatrix;
    }

    @Override
    public void setModelMatrix(final Matrix4fc modelMatrix) {
        this.modelMatrix = (Matrix4f) modelMatrix;
    }

    @Override
    public Vector3<Float> getColor() {
        return color;
    }

    @Override
    public void setColor(final Vector3<Float> color) {
        this.color.set(color);
    }

    @Override
    public Vector3<Float> getNormalizedColor() {
        return new ImmutableVector3<>(color.getX() / MAX_RGB, color.getY() / MAX_RGB, color.getZ() / MAX_RGB);
    }

    @Override
    public Primitive getPrimitive() {
        return primitive;
    }

    /**
     * @param primitive
     *         primitive
     */
    void setPrimitive(final Primitive primitive) {
        this.primitive = primitive;
    }

    /**
     * Update model transform matrix.
     */
    protected abstract void updateModelMatrix();
}
