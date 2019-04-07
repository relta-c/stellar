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

import net.chifumi.stellar.math.MutableVector2;
import net.chifumi.stellar.math.Vector2;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

/**
 * Represents game window and various window properties.
 * <p>This class <b>must</b> be initialized before using any rendering function.</p>
 *
 * @author Nattakit Hosapsin
 * @version 1.0.2
 * @since 1.0.0
 */
public class Display {
    /**
     * window resolution
     */
    private final MutableVector2<Integer> resolution;

    /**
     * level of multisample anti-aliasing
     */
    private final int multisamplingLevel;
    /**
     * id of openGL display object
     */
    private long id;
    /**
     * fullscreen mode
     */
    private boolean fullscreen;
    /**
     * window title
     */
    private String title;
    /**
     * camera object that will be use to transform view
     */
    private Camera camera;

    /**
     * Create a new {@link net.chifumi.stellar.graphics.Display} with some default values for further rendering
     * operation.
     *
     * @param width
     *         window resolution width
     * @param height
     *         window resolution height
     * @param title
     *         window title
     * @param multisamplingLevel
     *         level of multisample anti-aliasing
     *
     * @since 1.0.0
     */
    public Display(final int width, final int height, final CharSequence title, final int multisamplingLevel) {
        fullscreen = false;
        this.title = (String) title;
        this.multisamplingLevel = multisamplingLevel;
        resolution = new MutableVector2<>(width, height);
        camera = new Camera(resolution);
        initialize();
    }

    /**
     * Get current window resolution.
     *
     * @return window resolution in {@link net.chifumi.stellar.math.ImmutableVector2}
     *
     * @since 1.0.2
     */
    public Vector2<Integer> getResolution() {
        return resolution.toImmutable();
    }

    /**
     * Get multisample anti-aliasing level.
     *
     * @return level of multisampling
     *
     * @since 1.0.0
     */
    public int getMultisamplingLevel() {
        return multisamplingLevel;
    }

    /**
     * Get current {@link net.chifumi.stellar.graphics.Camera} in this object.
     *
     * @return camera object
     *
     * @since 1.0.0
     */

    public Camera getCamera() {
        return camera;
    }

    /**
     * Set a new {@link net.chifumi.stellar.graphics.Camera} to transform view.
     * <p>This have to be used very time to change camera's parameter.</p>
     *
     * @param camera
     *         new camera object
     *
     * @since 1.0.0
     */
    public void setCamera(final Camera camera) {
        this.camera = camera;
    }

    /**
     * Check if window are fullscreen.
     *
     * @return window fullscreen status
     *
     * @since 1.0.1
     */
    public boolean isFullscreen() {
        return fullscreen;
    }

    /**
     * Set if window should be fullscreen or not.
     *
     * @param fullscreen
     *         should window be fullscreen
     *
     * @since 1.0.1
     */
    public void setFullscreen(final boolean fullscreen) {
        if (this.fullscreen != fullscreen) {
            this.fullscreen = fullscreen;
            if (this.fullscreen) {
                glfwSetWindowMonitor(id, glfwGetPrimaryMonitor(), 0, 0,
                                     resolution.getX(), resolution.getY(), GLFW_DONT_CARE);
            } else {
                glfwSetWindowMonitor(id, 0, 0, 0,
                                     resolution.getX(), resolution.getY(), GLFW_DONT_CARE);
            }
        }
    }

    /**
     * Get window title.
     *
     * @return window title
     *
     * @since 1.0.0
     */
    public String getWindowTitle() {
        return title;
    }

    /**
     * Set window title.
     *
     * @param title
     *         window title
     *
     * @since 1.0.0
     */
    public void setWindowTitle(final CharSequence title) {
        glfwSetWindowTitle(id, title);
        this.title = (String) title;
    }

    /**
     * Swap buffer then clear old buffers to update window state.
     * <p>This method should be used after every thing have been drawn</p>
     *
     * @since 1.0.0
     */
    public void update() {
        glfwMakeContextCurrent(id);
        glfwPollEvents();

        camera.updateViewMatrix();
        camera.updateProjectionMatrix();

        glfwSwapBuffers(id);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    /**
     * Check if window should be close.
     *
     * @return window closing status
     *
     * @since 1.0.0
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(id);
    }

    /**
     * Restore monitor resolution and terminate window.
     *
     * @since 1.0.0
     */
    public void terminate() {
        if (fullscreen) {
            glfwSetWindowMonitor(id, 0, 0, 0, 1, 1, GLFW_DONT_CARE);
        }
        glfwSetWindowShouldClose(id, true);
    }

    /**
     * Get openGL display id
     *
     * @return openGL display id
     *
     * @since 1.0.0
     */
    public long getID() {
        return id;
    }

    /**
     * Initialize glfw and openGL with default configuration.
     */
    private void initialize() {
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        glfwWindowHint(GLFW_SAMPLES, multisamplingLevel);
        id = glfwCreateWindow(resolution.getX(), resolution.getY(), title, 0L, 0L);
        glfwMakeContextCurrent(id);

        GL.createCapabilities();
        glViewport(0, 0, resolution.getX(), resolution.getY());
        glEnable(GL_BLEND);
        glEnable(GL_MULTISAMPLE);
        glEnable(GL_FRAMEBUFFER_SRGB);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // TODO : Make clear color customizable
    }
}
