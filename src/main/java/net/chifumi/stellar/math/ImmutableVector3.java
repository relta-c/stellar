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

public class ImmutableVector3<Type extends Number> implements Vector3<Type> {
    private final Type x;
    private final Type y;
    private final Type z;

    public ImmutableVector3(final Type x, final Type y, final Type z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ImmutableVector3(final Vector3<? extends Type> vector3) {
        x = vector3.getX();
        y = vector3.getY();
        z = vector3.getZ();
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

    public Vector3<Type> subtract(final Vector3<? extends Type> vector3) {
        final Type resultX = GenericMath.subtract(getX(), vector3.getX());
        final Type resultY = GenericMath.subtract(getY(), vector3.getY());
        final Type resultZ = GenericMath.subtract(getZ(), vector3.getZ());
        return new ImmutableVector3<>(resultX, resultY, resultZ);
    }


    public Type dot(final Vector3<? extends Type> vector3) {
        final Type resultX = GenericMath.multiply(getX(), vector3.getX());
        final Type resultY = GenericMath.multiply(getY(), vector3.getY());
        final Type resultZ = GenericMath.multiply(getY(), vector3.getZ());
        return GenericMath.add(GenericMath.add(resultX, resultY), resultZ);
    }

    public Type lengthSquared() {
        return dot(this);
    }

    public Type length() {
        return GenericMath.sqrt(lengthSquared());
    }
}
