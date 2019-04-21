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

package net.chifumi.stellar.text;

import net.chifumi.stellar.graphic.DrawableObject;
import net.chifumi.stellar.graphic.Primitive;
import net.chifumi.stellar.graphic.TexturedDrawable;
import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.MutableVector2;
import net.chifumi.stellar.math.Vector2;
import net.chifumi.stellar.texture.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class DrawableCharacter extends DrawableObject implements TexturedDrawable {
    private final int id;
    private final CharacterInfo characterInfo;
    private final FontFamily family;
    private int fontSize;
    private final MutableVector2<Float> position;
    private Texture texture;
    private boolean solidFilled;

    DrawableCharacter(final int id, final int fontSize, final FontFamily family) {
        super(new CharacterPrimitive(family.getNormalizeCharacterOffset(family.getCharacter(id)),
                                     family.getNormalizeSize(family.getCharacter(id))));
        position = new MutableVector2<>(0.0f, 0.0f);
        this.fontSize = fontSize;
        this.id = id;
        this.family = family;
        texture = family.getAtlas();
        characterInfo = family.getCharacter(id);
        updateModelMatrix();
    }

    public int getID() {
        return id;
    }

    public CharacterInfo getCharacterInfo() {
        return characterInfo;
    }

    public Vector2<Float> getPosition() {
        return position;
    }

    public void setPosition(final Vector2<Float> position) {
        this.position.set(position);
        updateModelMatrix();
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(final int fontSize) {
        this.fontSize = fontSize;
        updateModelMatrix();
    }

    public FontFamily getFamily() {
        return family;
    }

    @Override
    public Primitive getPrimitive() {
        final Vector2<Double> pos = family.getNormalizeCharacterOffset(characterInfo);
        final Vector2<Double> rect = family.getNormalizeSize(characterInfo);
        return new CharacterPrimitive(pos, rect);

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

    protected void updateModelMatrix() {
        final Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.translate(new Vector3f(position.getX(), position.getY(), 0.0f));
        modelMatrix.scale(new Vector3f(getDrawSize().getX(), getDrawSize().getY(), 1.0F));
        setModelMatrix(modelMatrix);
    }

    int getXOffset() {
        return characterInfo.getXOffset();
    }

    int getYOffset() {
        return characterInfo.getYOffset();
    }

    int getAdvance() {
        return characterInfo.getAdvance();
    }

    private Vector2<Float> getDrawSize() {
        final float width = (characterInfo.getWidth() / (float) family.getNativeSize()) * fontSize;
        final float height = (characterInfo.getHeight() / (float) family.getNativeSize()) * fontSize;
        return new ImmutableVector2<>(width, height);
    }
}
