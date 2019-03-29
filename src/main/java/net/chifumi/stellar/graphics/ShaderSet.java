//    Copyright (C) 2019 Nattakit Hosapsin <delta@chifumi.net>
//
//    This file is part of Stellar
//    Stellar is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as
//    published by the Free Software Foundation, either
//    version 3 of the License, or (at your option) any later version.
//
//    Stellar is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with Stellar.  If not, see <https://www.gnu.org/licenses/lgpl.html>.

package net.chifumi.stellar.graphics;

public enum ShaderSet {
    DEFAULT("/shaders/vs_default.glsl", "/shaders/fs_default.glsl"),
    INVERT("/shaders/vs_default.glsl", "/shaders/fs_invert.glsl");

    private final String vertex;
    private final String fragment;

    ShaderSet(final CharSequence vertex, final CharSequence fragment) {
        this.vertex = (String) vertex;
        this.fragment = (String) fragment;
    }

    public String getVertex() {
        return vertex;
    }

    public String getFragment() {
        return fragment;
    }
}