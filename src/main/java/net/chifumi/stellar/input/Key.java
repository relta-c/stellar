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

package net.chifumi.stellar.input;

import static org.lwjgl.glfw.GLFW.*;

public enum Key {
    ZERO(GLFW_KEY_0),
    ONE(GLFW_KEY_1),
    TWO(GLFW_KEY_2),
    THREE(GLFW_KEY_3),
    FOUR(GLFW_KEY_4),
    FIVE(GLFW_KEY_5),
    SIX(GLFW_KEY_6),
    SEVEN(GLFW_KEY_7),
    EIGHT(GLFW_KEY_8),
    NINE(GLFW_KEY_9),
    TEN(GLFW_KEY_0),
    A(GLFW_KEY_A),
    APOSTROPHE(GLFW_KEY_APOSTROPHE),
    B(GLFW_KEY_B),
    BACKSLASH(GLFW_KEY_BACKSLASH),
    BACKSPACE(GLFW_KEY_BACKSPACE),
    C(GLFW_KEY_C),
    CAP_LOCK(GLFW_KEY_CAPS_LOCK),
    COMMA(GLFW_KEY_COMMA),
    D(GLFW_KEY_D),
    DELETE(GLFW_KEY_DELETE),
    DOWN(GLFW_KEY_DOWN),
    E(GLFW_KEY_E),
    END(GLFW_KEY_END),
    ENTER(GLFW_KEY_ENTER),
    EQUAL(GLFW_KEY_EQUAL),
    ESCAPE(GLFW_KEY_ESCAPE),
    F(GLFW_KEY_F),
    F1(GLFW_KEY_F1),
    F2(GLFW_KEY_F2),
    F3(GLFW_KEY_F3),
    F4(GLFW_KEY_F4),
    F5(GLFW_KEY_F5),
    F6(GLFW_KEY_F6),
    F7(GLFW_KEY_F7),
    F8(GLFW_KEY_F8),
    F9(GLFW_KEY_F9),
    F10(GLFW_KEY_F10),
    F11(GLFW_KEY_F11),
    F12(GLFW_KEY_F12), // TODO : More function keys
    G(GLFW_KEY_G),
    GRAVE_ACCENT(GLFW_KEY_GRAVE_ACCENT),
    H(GLFW_KEY_H),
    HOME(GLFW_KEY_HOME),
    I(GLFW_KEY_I),
    INSERT(GLFW_KEY_INSERT),
    J(GLFW_KEY_J),
    K(GLFW_KEY_K),
    KP_0(GLFW_KEY_KP_0),
    KP_1(GLFW_KEY_KP_1),
    KP_2(GLFW_KEY_KP_2),
    KP_3(GLFW_KEY_KP_3),
    KP_4(GLFW_KEY_KP_4),
    KP_5(GLFW_KEY_KP_5),
    KP_6(GLFW_KEY_KP_6),
    KP_7(GLFW_KEY_KP_7),
    KP_8(GLFW_KEY_KP_8),
    KP_9(GLFW_KEY_KP_9),
    KP_ADD(GLFW_KEY_KP_ADD),
    KP_DECIMAL(GLFW_KEY_KP_DECIMAL),
    KP_DIVIDE(GLFW_KEY_KP_DIVIDE),
    KP_ENTER(GLFW_KEY_KP_ENTER),
    KP_EQUAL(GLFW_KEY_KP_EQUAL),
    KP_MULTIPLY(GLFW_KEY_KP_MULTIPLY),
    KP_SUBTRACT(GLFW_KEY_KP_SUBTRACT),
    L(GLFW_KEY_L),
    LEFT(GLFW_KEY_LEFT),
    LEFT_ALT(GLFW_KEY_LEFT_ALT),
    LEFT_BRACKET(GLFW_KEY_LEFT_BRACKET),
    LEFT_CONTROL(GLFW_KEY_LEFT_CONTROL), // TODO : May have to use mod
    LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT),
    LEFT_SUPER(GLFW_KEY_LEFT_SUPER),
    M(GLFW_KEY_M),
    MENU(GLFW_KEY_MENU),
    MINUS(GLFW_KEY_MINUS),
    N(GLFW_KEY_N),
    NUM_LOCK(GLFW_KEY_NUM_LOCK),
    O(GLFW_KEY_O),
    P(GLFW_KEY_P),
    PAGE_DOWN(GLFW_KEY_PAGE_DOWN),
    PAGE_UP(GLFW_KEY_PAGE_UP),
    PAUSE(GLFW_KEY_PAUSE),
    PERIOD(GLFW_KEY_PERIOD),
    PRINT_SCREEN(GLFW_KEY_PRINT_SCREEN),
    Q(GLFW_KEY_Q),
    R(GLFW_KEY_R),
    RIGHT(GLFW_KEY_RIGHT),
    RIGHT_ALT(GLFW_KEY_RIGHT_ALT),
    RIGHT_BRACKET(GLFW_KEY_RIGHT_BRACKET),
    RIGHT_CONTROL(GLFW_KEY_RIGHT_CONTROL),
    RIGHT_SHIFT(GLFW_KEY_RIGHT_SHIFT),
    RIGHT_SUPER(GLFW_KEY_RIGHT_SUPER),
    S(GLFW_KEY_S),
    SEMI_COLON(GLFW_KEY_SEMICOLON),
    SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK),
    SLASH(GLFW_KEY_SLASH),
    SPACE(GLFW_KEY_SPACE),
    T(GLFW_KEY_T),
    TAB(GLFW_KEY_TAB),
    U(GLFW_KEY_U),
    UNKNOWN(GLFW_KEY_UNKNOWN),
    UP(GLFW_KEY_UP),
    V(GLFW_KEY_V),
    W(GLFW_KEY_W),
    WORLD_1(GLFW_KEY_WORLD_1),
    WORLD_2(GLFW_KEY_WORLD_2),
    X(GLFW_KEY_X),
    Y(GLFW_KEY_Y),
    Z(GLFW_KEY_Z);

    private final int id;

    Key(final int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
