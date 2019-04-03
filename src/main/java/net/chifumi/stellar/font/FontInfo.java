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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

class FontInfo {
    private static final String SEPARATOR = " ";
    private static final String VALUE_SEPARATOR = "=";
    private static final String ARRAY_SEPARATOR = ",";
    private static final Pattern STRING_PATTERN = Pattern.compile("\"");
    private final Scanner scanner;

    private String name;
    private int size;
    private final int[] padding;
    private final int[] spacing;

    private int lineHeight;
    private int base;
    private int atlasWidth;
    private int atlasHeight;

    private String fileName;

    private int characterCount;

    private final Map<Integer, CharacterInfo> characterMap;

    FontInfo(final CharSequence path) throws FileNotFoundException {
        final File file = new File((String) path);
        scanner = new Scanner(file);
        name = "";
        size = 0;
        padding = new int[4];
        spacing = new int[2];
        lineHeight = 0;
        base = 0;
        atlasWidth = 0;
        atlasHeight = 0;
        characterCount = 0;
        characterMap = new HashMap<>();

        processFamilyData();
        processFileData();
        processPageData();
        processCharacterCountData();
        processCharacterData();
        scanner.close();
    }

    String getName() {
        return name;
    }

    int getSize() {
        return size;
    }

    int getAtlasWidth() {
        return atlasWidth;
    }

    int getAtlasHeight() {
        return atlasHeight;
    }

    String getFileName() {
        return fileName;
    }

    Map<Integer, CharacterInfo> getCharacterMap() {
        return Collections.unmodifiableMap(characterMap);
    }

    private void processFamilyData() {
        scanner.next();
        final String line = scanner.nextLine();
        final String[] valueSets = line.split(SEPARATOR);
        for (final String valueSet : valueSets) {
            final String[] values = valueSet.split(VALUE_SEPARATOR);
            switch (values[0]) {
                case "face":
                    name = values[1];
                    break;
                case "size":
                    size = Integer.parseInt(values[1]);
                    break;
                case "padding":
                    final String[] paddingList = values[1].split(ARRAY_SEPARATOR);
                    for (int i = 0; i < 4; i++) {
                        padding[i] = Integer.parseInt(paddingList[i]);
                    }
                    break;
                case "spacing":
                    final String[] spacingList = values[1].split(ARRAY_SEPARATOR);
                    for (int i = 0; i < 2; i++) {
                        spacing[i] = Integer.parseInt(spacingList[i]);
                    }
                    break;
                default:
            }
        }
    }

    private void processFileData() {
        scanner.next();
        final String line = scanner.nextLine();
        final String[] valueSets = line.split(SEPARATOR);
        for (final String valueSet : valueSets) {
            final String[] values = valueSet.split(VALUE_SEPARATOR);
            switch (values[0]) {
                case "lineHeight":
                    lineHeight = Integer.parseInt(values[1]);
                    break;
                case "base":
                    base = Integer.parseInt(values[1]);
                    break;
                case "scaleW":
                    atlasWidth = Integer.parseInt(values[1]);
                    break;
                case "scaleH":
                    atlasHeight = Integer.parseInt(values[1]);
                    break;
                default:
            }
        }
    }

    private void processPageData() {
        scanner.next();
        final String charCountLine = scanner.nextLine();
        final String[] charCountValueSets = charCountLine.split(SEPARATOR);
        for (final String valueSet : charCountValueSets) {
            final String[] values = valueSet.split(VALUE_SEPARATOR);
            if ("file".equals(values[0])) {
                fileName = STRING_PATTERN.matcher(values[1]).replaceAll("");
            }
        }
    }

    private void processCharacterCountData() {
        scanner.next();
        final String charCountLine = scanner.nextLine();
        final String[] charCountValueSets = charCountLine.split(SEPARATOR);
        for (final String valueSet : charCountValueSets) {
            final String[] values = valueSet.split(VALUE_SEPARATOR);
            if ("count".equals(values[0])) {
                characterCount = Integer.parseInt(values[1]);
            }
        }
    }

    private void processCharacterData() {
        for (int i = 0; i < characterCount; i++) {
            scanner.next();
            final String charLine = scanner.nextLine();
            final String[] charValueSets = charLine.split(SEPARATOR);
            int id = 0;
            int x = 0;
            int y = 0;
            int width = 0;
            int height = 0;
            int xOffset = 0;
            int yOffset = 0;
            int xAdvance = 0;
            for (final String valueSet : charValueSets) {
                final String[] values = valueSet.split(VALUE_SEPARATOR);
                switch (values[0]) {
                    case "id":
                        id = Integer.parseInt(values[1]);
                        break;
                    case "x":
                        x = Integer.parseInt(values[1]);
                        break;
                    case "y":
                        y = Integer.parseInt(values[1]);
                        break;
                    case "width":
                        width = Integer.parseInt(values[1]);
                        break;
                    case "height":
                        height = Integer.parseInt(values[1]);
                        break;
                    case "xoffset":
                        xOffset = Integer.parseInt(values[1]);
                        break;
                    case "yoffset":
                        yOffset = Integer.parseInt(values[1]);
                        break;
                    case "xadvance":
                        xAdvance = Integer.parseInt(values[1]);
                        break;
                    default:
                        continue;
                }
                final CharacterInfo characterInfo = new CharacterInfo(x, y, width, height, xOffset, yOffset, xAdvance);
                characterMap.put(id, characterInfo);
            }
        }
    }
}
