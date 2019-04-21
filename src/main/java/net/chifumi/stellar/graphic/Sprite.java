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
import net.chifumi.stellar.math.Vector2;
import net.chifumi.stellar.texture.Texture;

/**
 * Represents rectangular sprite.
 * <p>The default size of sprite will be equal to sprite texture</p>
 *
 * @author Nattakit Hopasin
 * @version 1.0.4
 * @since 1.0.0
 */
public class Sprite extends DrawableRectangle implements TexturedDrawable {
    private Texture texture;
    private boolean solidFilled;

    /**
     * @param texture
     *         the sprite texture
     */
    public Sprite(final Texture texture) {
        super(new ImmutableVector2<>(0.0f, 0.0f),
              new ImmutableVector2<>((float) texture.getWidth(), (float) texture.getHeight()));
        this.texture = texture;
        updateModelMatrix();
    }

    /**
     * @param textureMap
     *         the texture map
     * @param position
     *         the position in texture map to use
     * @param size
     *         the size of texture to use
     *
     * @since 1.0.4
     */
    public Sprite(final Texture textureMap, final Vector2<Double> position, final Vector2<Double> size) {
        super(new ImmutableVector2<>(0.0f, 0.0f),
              new ImmutableVector2<>((float) textureMap.getWidth(), (float) textureMap.getHeight()),
              new SpritePrimitive(position, size));
        texture = textureMap;
        updateModelMatrix();
    }

    /**
     * @param textureMap
     *         the texture map
     * @param x
     *         the x coordinate of texture
     * @param y
     *         the y coordinate of texture
     * @param width
     *         the width of texture
     * @param height
     *         the width of texture
     *
     * @since 1.0.4
     */
    public Sprite(final Texture textureMap, final double x, final double y, final double width, final double height) {
        super(new ImmutableVector2<>(0.0f, 0.0f),
              new ImmutableVector2<>((float) width, (float) height),
              new SpritePrimitive(new ImmutableVector2<>(x / textureMap.getWidth(), y / textureMap.getHeight()),
                                  new ImmutableVector2<>(width / textureMap.getWidth(),
                                                         height / textureMap.getHeight())));
        texture = textureMap;
        updateModelMatrix();
    }

    public Sprite(final SpriteMap spriteMap, final CharSequence name) {
        super(new ImmutableVector2<>(0.0f, 0.0f),
              new ImmutableVector2<>(spriteMap.getSpriteArea(name).getSize().getX().floatValue(),
                                     spriteMap.getSpriteArea(name).getSize().getY().floatValue()),
              new SpritePrimitive(
                      new ImmutableVector2<>(
                              spriteMap.getSpriteArea(name).getPosition().getX() / spriteMap.getTextureMap().getWidth(),
                              spriteMap.getSpriteArea(name).getPosition().getY() / spriteMap.getTextureMap().getHeight()),

                      new ImmutableVector2<>(
                              spriteMap.getSpriteArea(name).getSize().getX() / spriteMap.getTextureMap().getWidth(),
                              spriteMap.getSpriteArea(name).getSize().getY() / spriteMap.getTextureMap().getHeight())));
        texture = spriteMap.getTextureMap();
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
