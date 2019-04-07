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

package net.chifumi.stellar.math;

@SuppressWarnings("unchecked")
public class MutableVector2<Type extends Number> implements Vector2<Type> {
    private Type x;
    private Type y;

    public MutableVector2(final Type x, final Type y) {
        this.x = x;
        this.y = y;
    }

    public MutableVector2(final Vector2<? extends Type> vector2) {
        x = vector2.getX();
        y = vector2.getY();
    }

    @Override
    public Type getX() {
        return x;
    }

    public void setX(final Type x) {
        this.x = x;
    }

    @Override
    public Type getY() {
        return y;
    }

    public void setY(final Type y) {
        this.y = y;
    }

    public void set(final Vector2<? extends Type> vector2) {
        x = vector2.getX();
        y = vector2.getY();
    }

    public void set(final Type x, final Type y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Vector2<Type> sub(final Vector2<? extends Type> vector2) {
        final Type resultX = GenericMath.subtract(getX(), vector2.getX());
        final Type resultY = GenericMath.subtract(getY(), vector2.getY());
        return new ImmutableVector2<>(resultX, resultY);
    }

    @Override
    public Type dot(final Vector2<? extends Type> vector2) {
        final Type resultX = GenericMath.multiply(getX(), vector2.getX());
        final Type resultY = GenericMath.multiply(getY(), vector2.getY());
        return GenericMath.add(resultX, resultY);
    }

    @Override
    public Type lengthSquared() {
        return dot(this);
    }

    @Override
    public Type length() {
        return GenericMath.sqrt(lengthSquared());
    }

    @Override
    public Vector2<Type> normalize() {
        final Type magnitude = length();
        final Type newX = GenericMath.divide(getX(), magnitude);
        final Type newY = GenericMath.divide(getY(), magnitude);
        return new ImmutableVector2<>(newX, newY);
    }

    @Override
    public Vector2<Type> perpendicular() {
        final Type newY = (Type) GenericMath.multiply(getX(), -1);
        return new ImmutableVector2<>(getY(), newY);
    }

    public ImmutableVector2<Type> toImmutable() {
        return new ImmutableVector2<>(this);
    }
}
