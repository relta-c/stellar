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

import net.chifumi.stellar.math.Vector3;
import org.joml.Matrix4fc;

/**
 * Interface for object that can be draw.
 *
 * @author Nattakit Hosapsin
 * @version 1.0.2
 * @since 1.0.0
 */
public interface Drawable {
    /**
     * max color value
     */
    float MAX_RGB = 255.0f;

    /**
     * max alpha value
     */
    float MAX_ALPHA = 100.0f;

    /**
     * Get {@link Primitive} for drawing.
     *
     * @return primitive
     *
     * @since 1.0.0
     */
    Primitive getPrimitive();

    /**
     * Get object model matrix to transform object to global space.
     *
     * @return model transform matrix
     *
     * @since 1.0.0
     */
    Matrix4fc getModelMatrix();

    /**
     * Set model transform matrix.
     *
     * @param modelMatrix
     *         model transform matrix
     *
     * @since 1.0.0
     */
    void setModelMatrix(final Matrix4fc modelMatrix);

    /**
     * Get object color.
     *
     * @return object color in rgb
     *
     * @since 1.0.2
     */
    Vector3<Float> getColor();

    /**
     * @param color
     *         1.0.2
     */
    void setColor(Vector3<Float> color);

    /**
     * @return normalized color
     *
     * @since 1.0.2
     */
    Vector3<Float> getNormalizedColor();

    /**
     * Set object color.
     *
     * @param red
     *         amount of red color (0 - {@value #MAX_RGB})
     * @param green
     *         amount of green color (0 - {@value #MAX_RGB})
     * @param blue
     *         amount of blue color (0 - {@value #MAX_RGB})
     *
     * @since 1.0.0
     */
    void setColor(final float red, final float green, final float blue);

    /**
     * Get object transparency in percentage
     *
     * @return transparency value in percentage
     *
     * @since 1.0.1
     */
    float getTransparency();

    /**
     * Set object transparency in percentage
     *
     * @param transparency in percentage
     *
     * @since 1.0.1
     */
    void setTransparency(final float transparency);

    /**
     * @return normalizedTransparency
     *
     * @since 1.0.2
     */
    float getNormalizedTransparency();

    /**
     * Get object visibility.
     *
     * @return visibility of object
     *
     * @since 1.0.1
     */
    boolean isVisible();

    /**
     * Set object visibility.
     *
     * @param visible
     *         visibility of object
     *
     * @since 1.0.1
     */
    void setVisible(boolean visible);
}
