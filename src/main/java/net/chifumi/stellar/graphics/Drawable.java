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

import org.joml.Matrix4fc;
import org.joml.Vector3fc;

/**
 * Interface for object that can be draw.
 *
 * @author Nattakit Hosapsin
 */
public interface Drawable {
    /**
     * Get {@link Primitive} for drawing.
     *
     * @return primitive
     */
    Primitive getPrimitive();

    /**
     * Get object model matrix to transform object to global space.
     *
     * @return model transform matrix
     */
    Matrix4fc getModelMatrix();

    /**
     * @param modelMatrix
     *         model transform matrix
     */
    void setModelMatrix(final Matrix4fc modelMatrix);

    /**
     * Gen object color for drawing.
     *
     * @return object color
     */
    Vector3fc getColor();
}
