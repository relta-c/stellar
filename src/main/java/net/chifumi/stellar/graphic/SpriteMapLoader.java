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

import net.chifumi.stellar.math.MutableVector2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class SpriteMapLoader {
    private static final String SEPARATOR = " ";
    private static final String VALUE_SEPARATOR = "=";
    private final Scanner scanner;
    private final Map<CharSequence, SpriteArea> spriteMapData;
    private int spritesCount;
    private double defaultWidth;
    private double defaultHeight;

    SpriteMapLoader(final CharSequence path) throws FileNotFoundException {
        final File file = new File((String) path);
        scanner = new Scanner(file);
        spriteMapData = new HashMap<>();
        spritesCount = 0;
        defaultHeight = 0;
        defaultWidth = 0;

        processMapVersion();
        processMapData();
        processCharacterData();
        scanner.close();
    }

    Map<CharSequence, SpriteArea> getSpriteMapData() {
        return Collections.unmodifiableMap(spriteMapData);
    }

    private void processMapVersion() {
        final String line = scanner.nextLine();
        final String[] valueSets = line.split(SEPARATOR);
        for (final String valueSet : valueSets) {
            final String[] values = valueSet.split(VALUE_SEPARATOR);
            switch (values[0]) {
                case "type":
                    if (!values[1].equals("sprite_map")) {
                        throw new IllegalStateException("Not a sprite map data file");
                    }
                    break;
                case "version":
                    if (Integer.parseInt(values[1]) != 1) {
                        throw new IllegalStateException("Unsupported sprite map version");
                    }
                    break;
                default:
            }
        }
    }

    private void processMapData() {
        final String line = scanner.nextLine();
        final String[] valueSets = line.split(SEPARATOR);
        for (final String valueSet : valueSets) {
            final String[] values = valueSet.split(VALUE_SEPARATOR);
            switch (values[0]) {
                case "sprites_count":
                    spritesCount = Integer.parseInt(values[1]);
                    break;
                case "default_width":
                    defaultWidth = Double.parseDouble(values[1]);
                    break;
                case "default_height":
                    defaultHeight = Double.parseDouble(values[1]);
                    break;
                default:
            }
        }
    }

    private void processCharacterData() {
        for (int i = 0; i < spritesCount; i++) {
            final String charLine = scanner.nextLine();
            final String[] charValueSets = charLine.split(SEPARATOR);

            String name = "sprite-" + i;
            final MutableVector2<Double> position = new MutableVector2<>(0.0, 0.0);
            final MutableVector2<Double> size = new MutableVector2<>(defaultWidth, defaultHeight);

            for (final String valueSet : charValueSets) {
                final String[] values = valueSet.split(VALUE_SEPARATOR);
                switch (values[0]) {
                    case "name":
                        name = values[1];
                        break;
                    case "width":
                        size.setX(Double.parseDouble(values[1]));
                        break;
                    case "height":
                        size.setY(Double.parseDouble(values[1]));
                        break;
                    case "x":
                        position.setX(Double.parseDouble(values[1]));
                        break;
                    case "y":
                        position.setY(Double.parseDouble(values[1]));
                        break;
                    default:
                        continue;
                }
                final SpriteArea spriteArea = new SpriteArea(position, size);
                spriteMapData.put(name, spriteArea);
            }
        }
    }
}
