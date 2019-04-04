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

enum ShaderPath {
    DEFAULT("shaders/vs_default.glsl"),
    SPRITE("shaders/fs_sprite.glsl"),
    SOLID("shaders/fs_solid.glsl"),
    INVERT("shaders/fs_pos_invert.glsl"),
    NORMAL("shaders/fs_pos_normal.glsl"),
    GRAY_SCALE("shaders/fs_pos_greyscale.glsl"),
    SHARPEN("shaders/fs_pos_sharpen.glsl"),
    BLUR("shaders/fs_pos_blur.glsl"),
    FRAMEBUFFER("shaders/vs_pos_framebuffer.glsl");

    private final String path;

    ShaderPath(final CharSequence path) {
        this.path = (String) path;
    }

    String getPath() {
        return path;
    }
}
