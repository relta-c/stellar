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

package net.chifumi.stellar.font;

import net.chifumi.stellar.image.FilteringMode;
import net.chifumi.stellar.image.Texture;
import net.chifumi.stellar.image.TextureLoader;
import org.joml.Vector2d;

import java.io.File;
import java.io.FileNotFoundException;

public class FontFamily {
    private final FontInfo fontInfo;
    private final Texture atlas;

    public FontFamily(final CharSequence path) throws FileNotFoundException {
        fontInfo = new FontInfo(path);
        final String atlasPath = getPath((String) path) + "/" + fontInfo.getFileName();
        atlas = new TextureLoader().filter(FilteringMode.LINEAR).load(atlasPath);
    }

    public String getName() {
        return fontInfo.getName();
    }

    public int getMaxSize() {
        return fontInfo.getSize();
    }

    CharacterInfo getCharacter(final int id) {
        return fontInfo.getCharacterMap().get(id);
    }

    Vector2d getNormalizeCharacterOffset(final CharacterInfo characterInfo) {
        return new Vector2d(characterInfo.getX() / (double) fontInfo.getAtlasWidth(),
                            characterInfo.getY() / (double) fontInfo.getAtlasHeight());
    }

    Vector2d getNormalizeSize(final CharacterInfo characterInfo) {
        return new Vector2d(characterInfo.getWidth() / (double) (fontInfo.getAtlasWidth()),
                            characterInfo.getHeight() / (double) fontInfo.getAtlasHeight());
    }

    Texture getAtlas() {
        return atlas;
    }

    private static String getPath(final String filename) {
        final int i = filename.lastIndexOf(File.separator);
        return (i > -1) ? filename.substring(0, i) : filename;
    }

}
