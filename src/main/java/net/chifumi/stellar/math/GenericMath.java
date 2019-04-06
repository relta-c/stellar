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

@SuppressWarnings({"unchecked", "IfStatementWithTooManyBranches", "ChainOfInstanceofChecks"})
enum GenericMath {
    ;

    static <Type extends Number> Type add(final Type x, final Type y) {
        if (x instanceof Float) {
            return (Type) new Float(x.floatValue() + y.floatValue());
        } else if (x instanceof Double) {
            return (Type) new Double(x.doubleValue() + y.doubleValue());
        } else if (x instanceof Integer) {
            return (Type) new Integer(x.intValue() + y.intValue());
        } else if (x instanceof Long) {
            return (Type) new Long(x.longValue() + y.longValue());
        } else {
            throw new IllegalStateException("Unsupported Type");
        }
    }

    static <Type extends Number> Type subtract(final Type x, final Type y) {
        if (x instanceof Float) {
            return (Type) new Float(x.floatValue() - y.floatValue());
        } else if (x instanceof Double) {
            return (Type) new Double(x.doubleValue() - y.doubleValue());
        } else if (x instanceof Integer) {
            return (Type) new Integer(x.intValue() - y.intValue());
        } else if (x instanceof Long) {
            return (Type) new Long(x.longValue() - y.longValue());
        } else {
            throw new IllegalStateException("Unsupported Type");
        }
    }

    static <Type extends Number> Type multiply(final Type x, final Type y) {
        if (x instanceof Float) {
            return (Type) new Float(x.floatValue() * y.floatValue());
        } else if (x instanceof Double) {
            return (Type) new Double(x.doubleValue() * y.doubleValue());
        } else if (x instanceof Integer) {
            return (Type) new Integer(x.intValue() * y.intValue());
        } else if (x instanceof Long) {
            return (Type) new Long(x.longValue() * y.longValue());
        } else {
            throw new IllegalStateException("Unsupported Type");
        }
    }

    static <Type extends Number> Type divide(final Type x, final Type y) {
        if (x instanceof Float) {
            return (Type) new Float(x.floatValue() / y.floatValue());
        } else if (x instanceof Double) {
            return (Type) new Double(x.doubleValue() / y.doubleValue());
        } else if (x instanceof Integer) {
            return (Type) new Integer(x.intValue() / y.intValue());
        } else if (x instanceof Long) {
            return (Type) new Long(x.longValue() / y.longValue());
        } else {
            throw new IllegalStateException("Unsupported Type");
        }
    }

    static <Type extends Number> Type sqrt(final Type x) {
        if (x instanceof Float) {
            return (Type) new Float(Math.sqrt(x.floatValue()));
        } else if (x instanceof Double) {
            return (Type) new Double(Math.sqrt(x.doubleValue()));
        } else {
            throw new IllegalStateException("Unsupported Type");
        }
    }
}
