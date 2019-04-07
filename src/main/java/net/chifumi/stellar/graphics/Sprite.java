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

/**
 * @author Nattakit Hopasin
 * @version 1.0.2
 * @since 1.0.0
 */
public class Sprite extends DrawableRectangle implements TexturedDrawable {
    private Texture texture;
    private boolean solidFilled;

    public Sprite(final Texture texture) {
        super(0.0f, 0.0f, texture.getWidth(), texture.getHeight());
        this.texture = texture;
        updateModelMatrix();
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void setTexture(final Texture texture) {
        this.texture = texture;
    }

    @Override
    public boolean isSolidFilled() {
        return solidFilled;
    }

    @Override
    public void setSolidFilled(final boolean solidFilled) {
        this.solidFilled = solidFilled;
    }
}
