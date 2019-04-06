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

import net.chifumi.stellar.texture.Texture;

/***
 * Represents drawable object with texture.
 *
 * @author Nattakit Hosapsin
 * @version 1.0.1
 * @since 1.0.1
 */
public abstract class TexturedDrawableObject extends DrawableObject {
    private Texture texture;

    protected TexturedDrawableObject(final Primitive primitive, final Texture texture) {
        super(primitive);
        this.texture = texture;
    }

    /**
     * Get current texture
     *
     * @return texture
     *
     * @since 1.0.1
     */
    public Texture getTexture() {
        return texture;
    }


    /**
     * Set new texture
     *
     * @param texture
     *         new texture
     *
     * @since 1.0.1
     */
    boolean isSolidFilled();

    /**
     * Set this to {@code true} to fill alpha with solid color
     *
     * @param solidFilled
     *         solid filled mode
     *
     * @since 1.0.2
     */
    void setSolidFilled(final boolean solidFilled);
}
