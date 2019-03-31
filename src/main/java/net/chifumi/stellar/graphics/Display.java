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

import org.joml.Vector2i;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

/**
 * Represents game's window and various window properties.
 * <p>This class must be initialized before using any rendering function.</p>
 *
 * @author Nattakit Hosapsin
 */
public class Display {
    /**
     * Window resolution
     */
    private final Vector2i resolution;
    /**
     * ID of OpenGL window object
     */
    private long id;
    /**
     * Window title
     */
    private String title;
    /**
     * Camera object that will be use to transform view
     */
    private Camera camera;

    /**
     * Create a new {@link Display} with some default values for further rendering operation.
     *
     * @param width
     *         window resolution width
     * @param height
     *         window resolution height
     * @param title
     *         window title
     */
    public Display(final int width, final int height, final CharSequence title) {
        this.title = (String) title;
        resolution = new Vector2i(width, height);
        camera = new Camera(new Vector2i(width, height));
        initialize();
    }

    /**
     * Get current window resolution.
     *
     * @return window resolution
     */
    public Vector2i getResolution() {
        return resolution;
    }

    /**
     * Get current {@link Camera} object.
     *
     * @return camera object
     */

    public Camera getCamera() {
        return camera;
    }

    /**
     * Set a new {@link Camera} to transform view.
     * <p>This have to be used very time to change camera's parameter.</p>
     *
     * @param camera
     *         new camera object
     */
    public void setCamera(final Camera camera) {
        this.camera = camera;
    }

    /**
     * Get window title.
     *
     * @return window title
     */
    public String getWindowTitle() {
        return title;
    }

    /**
     * Set window title
     *
     * @param title
     *         window title
     */
    public void setWindowTitle(final CharSequence title) {
        glfwSetWindowTitle(id, title);
        this.title = (String) title;
    }

    /**
     * Update widow state and swap framebuffer.
     * <p>This method should be used after every thing have been drawn</p>
     */
    public void update() {
        glfwMakeContextCurrent(id);
        glfwPollEvents();

        // Update transform matrix
        camera.updateViewMatrix();
        camera.updateProjectionMatrix();

        glfwSwapBuffers(id);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    /**
     * Check if window are closing.
     *
     * @return window closing status
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(id);
    }

    /**
     * Terminate window
     */
    public void terminate() {
        glfwSetWindowShouldClose(id, true);
    }

    /**
     * @return window OpenGL object id
     */
    public long getID() {
        return id;
    }

    /**
     * Initialize glfw and openGL with default configuration
     */
    private void initialize() {
        // Initialize glfw and create window
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        glfwWindowHint(GLFW_SAMPLES, 8);
        id = glfwCreateWindow(resolution.x, resolution.y, title, 0L, 0L);

        glfwMakeContextCurrent(id);

        // Initialize OpenGL
        GL.createCapabilities();
        glViewport(0, 0, resolution.x, resolution.y);
        glEnable(GL_BLEND);
        glEnable(GL_MULTISAMPLE);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // TODO : Make clear color customizable
    }
}
