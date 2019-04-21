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

/**
 * Post processing effects
 *
 * @author Nattakit Hosapsin
 * @version 1.0.0
 * @since 1.0.0
 */
public enum Effect {
    /**
     * blur image
     *
     * @since 1.0.0
     */
    BLUR(ShaderPath.BLUR),
    /**
     * turn image into b/w image
     *
     * @since 1.0.0
     */
    GREYSCALE(ShaderPath.GRAY_SCALE),
    /**
     * invert image color
     *
     * @since 1.0.0
     */
    INVERT(ShaderPath.INVERT),
    /**
     * do nothing
     *
     * @since 1.0.0
     */
    NORMAL(ShaderPath.NORMAL),
    /**
     * sharpen image
     *
     * @since 1.0.0
     */
    SHARPEN(ShaderPath.SHARPEN);

    private final ShaderPath path;

    Effect(final ShaderPath path) {
        this.path = path;
    }

    ShaderPath getPath() {
        return path;
    }
}
