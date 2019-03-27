//    Copyright (C) 2019 Nattakit Hosapsin <delta@chifumi.net>
//
//    This file is part of Stellar
//    Stellar is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as
//    published by the Free Software Foundation, either
//    version 3 of the License, or (at your option) any later version.
//
//    Stellar is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with Stellar.  If not, see <https://www.gnu.org/licenses/lgpl.html>.

package net.chifumi.stellar;

import org.joml.Vector2i;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Display {
    private final Vector2i resolution;
    private long windowId;
    private String windowTitle;
    private Camera camera;

    public Display(final int width, final int height, final String windowTitle) {
        resolution = new Vector2i(width, height);
        this.windowTitle = windowTitle;
        windowId = 0L;
        camera = new Camera(new Vector2i(width, height));
        init();
    }

    public Vector2i getResolution() {
        return resolution;
    }

    @SuppressWarnings("WeakerAccess")
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(final Camera camera) {
        this.camera = camera;
    }

    public CharSequence getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(final String windowTitle) {
        glfwSetWindowTitle(windowId, windowTitle);
        this.windowTitle = windowTitle;
    }

    public void update() {
        glfwMakeContextCurrent(windowId);
        glfwPollEvents();

        // Update matrix
        camera.updateViewMatrix();
        camera.updateProjectionMatrix();

        glfwSwapBuffers(windowId);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowId);
    }

    public void close() {
        glfwSetWindowShouldClose(windowId, true);
    }

    long getWindowId() {
        return windowId;
    }

    private void init() {
        // Initialize glfw and create window
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        windowId = glfwCreateWindow(resolution.x, resolution.y, windowTitle, 0L, 0L);

        glfwMakeContextCurrent(windowId);

        // Initialize OpenGl
        GL.createCapabilities();
        glViewport(0, 0, resolution.x, resolution.y);
        glEnable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // TODO : Make clear color customizable
    }
}
