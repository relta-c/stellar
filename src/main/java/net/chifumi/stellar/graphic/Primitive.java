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

/**
 * Interface for object with data for drawing a sharpe.
 *
 * @author Nattakit Hosapsin
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Primitive {
    /**
     * Get vertices for rendering this object.
     * <p>Vertex consist of vector2 of local space location and vector2 of texture coordinate.</p>
     *
     * @return vertices
     *
     * @since 1.0.0
     */
    float[] getVertices();

    /**
     * Get draw mode for rendering this object.
     *
     * @return openGL draw mode
     *
     * @since 1.0.0
     */
    int getDrawMode();

    /**
     * Get number of vertices.
     *
     * @return number of vertices
     */
    int getVerticesNum();
}
