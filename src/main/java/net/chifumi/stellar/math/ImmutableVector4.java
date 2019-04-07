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

public class ImmutableVector4<Type extends Number> implements Vector4<Type> {
    private final Type x;
    private final Type y;
    private final Type z;
    private final Type w;

    public ImmutableVector4(final Type x, final Type y, final Type z, final Type w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public ImmutableVector4(final Vector4<? extends Type> vector4) {
        x = vector4.getX();
        y = vector4.getY();
        z = vector4.getZ();
        w = vector4.getW();
    }

    @Override
    public Type getX() {
        return x;
    }

    @Override
    public Type getY() {
        return y;
    }

    @Override
    public Type getZ() {
        return z;
    }

    @Override
    public Type getW() {
        return w;
    }

    @Override
    public Vector4<Type> sub(final Vector4<? extends Type> vector4) {
        final Type resultX = GenericMath.subtract(getX(), vector4.getX());
        final Type resultY = GenericMath.subtract(getY(), vector4.getY());
        final Type resultZ = GenericMath.subtract(getZ(), vector4.getZ());
        final Type resultW = GenericMath.multiply(getW(), vector4.getW());
        return new ImmutableVector4<>(resultX, resultY, resultZ, resultW);
    }


    @Override
    public Type dot(final Vector4<? extends Type> vector4) {
        final Type resultX = GenericMath.multiply(getX(), vector4.getX());
        final Type resultY = GenericMath.multiply(getY(), vector4.getY());
        final Type resultZ = GenericMath.multiply(getZ(), vector4.getZ());
        final Type resultW = GenericMath.multiply(getW(), vector4.getW());
        return GenericMath.add(GenericMath.add(GenericMath.add(resultX, resultY), resultZ), resultW);
    }

    @Override
    public Type lengthSquared() {
        return dot(this);
    }

    @Override
    public Type length() {
        return GenericMath.sqrt(lengthSquared());
    }
}
