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

package net.chifumi.stellar.audio;

/**
 * Audio status
 *
 * @author Nattakit Hosapsin
 * @version 1.0.2
 * @since 1.0.2
 */
public enum TrackStatus {
    /**
     * nothing have been done
     * @since 1.0.2
     */
    INITIAL,
    /**
     * track have been paused
     *
     * @since 1.0.2
     */
    PAUSED,
    /**
     * track is playing
     *
     * @since 1.0.2
     */
    PLAYING,
    /**
     * track have been stopped
     * @since 1.0.2
     */
    STOPPED
}
