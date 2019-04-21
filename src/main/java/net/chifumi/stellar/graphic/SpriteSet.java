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

import net.chifumi.stellar.math.ImmutableVector2;

/**
 * @version 1.0.4
 * @since 1.0.4
 */
public class SpriteSet extends Sprite {
    private final SpriteMap spriteMap;

    /**
     * @param spriteMap sprite map
     * @param name the name of sprite
     *
     * @since 1.0.4
     */
    public SpriteSet(final SpriteMap spriteMap, final CharSequence name) {
        super(spriteMap, name);
        this.spriteMap = spriteMap;
    }

    public void setSprite(final CharSequence name) {
        setPrimitive(new SpritePrimitive(
                new ImmutableVector2<>(
                        spriteMap.getSpriteArea(name).getPosition().getX() / spriteMap.getTextureMap().getWidth(),
                        spriteMap.getSpriteArea(name).getPosition().getY() / spriteMap.getTextureMap().getHeight()),

                new ImmutableVector2<>(
                        spriteMap.getSpriteArea(name).getSize().getX() / spriteMap.getTextureMap().getWidth(),
                        spriteMap.getSpriteArea(name).getSize().getY() / spriteMap.getTextureMap().getHeight())));
        updateModelMatrix();
    }
}
