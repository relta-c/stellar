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

import net.chifumi.stellar.math.Vector2;
import net.chifumi.stellar.texture.Texture;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nattakit Hosapsin
 * @version 1.0.4
 * @since 1.0.4
 */
public class SpriteMap {
    private final Texture textureMap;
    private final Map<CharSequence, SpriteArea> spriteMapData;

    public SpriteMap(final Texture textureMap,
                     final CharSequence mapDataPath) throws FileNotFoundException {
        this.textureMap = textureMap;

        spriteMapData = new SpriteMapLoader(mapDataPath).getSpriteMapData();
    }

    public SpriteMap(final Texture textureMap,
                     final List<CharSequence> nameList,
                     final List<Vector2<Double>> positionList,
                     final List<Vector2<Double>> sizeList) {

        this.textureMap = textureMap;

        if (positionList.isEmpty() || sizeList.isEmpty() || positionList.size() != sizeList.size()) {
            throw new IllegalArgumentException("Invalid textureMap area data");
        }

        spriteMapData = new HashMap<>();
        for (int i = 0; i < positionList.size(); i++) {
            final SpriteArea spriteArea = new SpriteArea(positionList.get(i), sizeList.get(i));
            spriteMapData.put(nameList.get(i), spriteArea);
        }
    }

    public SpriteArea getSpriteArea(final CharSequence name) {
        return spriteMapData.get(name);
    }

    public Texture getTextureMap() {
        return textureMap;
    }

    /**
     * @return the list of sprite name
     *
     * @since 1.0.4
     */
    public List<CharSequence> getSpriteList() {
        final List<CharSequence> spriteList = new ArrayList<>(spriteMapData.keySet());
        spriteList.sort(null);
        return spriteList;
    }
}
