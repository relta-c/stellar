package net.chifumi.stellar;

import org.joml.*;
import org.lwjgl.opengl.*;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.glfw.GLFW.*;

public class Display {
    private static final float HALF_LEN = 0.5F;
    private final Vector2i resolution;
    private Vector2f cameraPosition;
    private Quaternionf cameraRotation;
    private final Queue<Sprite> drawQueue;
    private long windowId;
    private int vaoId;
    private float zoom;
    private String windowTitle;
    private Matrix4f view;
    private Matrix4f projection;
    private Shader shader;

    public Display(final int width, final int height, final String windowTitle) {
        resolution = new Vector2i(width, height);
        cameraPosition = new Vector2f();
        cameraRotation = new Quaternionf();
        this.windowTitle = windowTitle;
        windowId = 0L;
        vaoId = 0;
        zoom = 1.0F;
        updateViewMatrix();
        updateProjectionMatrix();
        shader = new Shader();
        drawQueue = new LinkedList<>();
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public int getWidth() {
        return resolution.x;
    }

    public int getHeight() {
        return resolution.y;
    }

    public Vector2f getCameraPosition() {
        return cameraPosition;
    }

    public float getCameraX() {
        return cameraPosition.x;
    }

    public float getCameraY() {
        return cameraPosition.y;
    }

    public void setCameraPosition(final Vector2f cameraPosition) {
        this.cameraPosition = cameraPosition;
    }

    public void setCameraPosition(final float x, final float y) {
        cameraPosition = new Vector2f(x, y);
    }

    public Quaternionf getCameraRotation() {
        return cameraRotation;
    }

    public float getCameraEulerRotation() {
        return cameraRotation.angle();
    }

    public void setCameraRotation(final Quaternionf cameraRotation) {
        this.cameraRotation = cameraRotation;
    }

    public void setCameraEulerRotation(final float angle) {
        cameraRotation = new Quaternionf().fromAxisAngleDeg(new Vector3f(0.0f, 0.0f, 1.0f), angle);
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(final float zoom) {
        this.zoom = zoom;
    }

    public CharSequence getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(final String windowTitle) {
        glfwSetWindowTitle(windowId, windowTitle);
        this.windowTitle = windowTitle;
    }

    public void init() {
        // Initialize glfw and create window
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_RESIZABLE, GL11.GL_FALSE);
        windowId = glfwCreateWindow(resolution.x, resolution.y, windowTitle, 0L, 0L);

        glfwMakeContextCurrent(windowId);  // TODO : Use this in every window update

        // Initialize OpenGl
        GL.createCapabilities();
        //GL11.glViewport(0, 0, width, height);
        GL11.glViewport(0, 0, resolution.x, resolution.y);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // TODO : Make color customizable

        // Load default shaders
        try {
            shader = ResourceLoader.loadShader("/shaders/vs_default.glsl", "/shaders/fs_default.glsl");
        } catch (final FileNotFoundException e) {
            System.err.println("shader file not found");
        }
        shader.use();
        shader.setUniform("sprite", 0);

        final int vboId;
        final float[] rectVertices = {
                // Pos      // Tex
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f
        };

        vaoId = GL30.glGenVertexArrays();
        vboId = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, rectVertices, GL15.GL_STATIC_DRAW);

        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 << 2, 0L);
        GL20.glEnableVertexAttribArray(1);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 << 2, 0L);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, GL11.GL_NONE);
        GL30.glBindVertexArray(GL11.GL_NONE);
    }

    public void update() {
        glfwMakeContextCurrent(windowId);

        glfwPollEvents(); // TODO : May need to move this to relevant class

        // Update matrix
        updateViewMatrix();
        updateProjectionMatrix();
        shader.setUniform("view", view);
        shader.setUniform("projection", projection);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        while (!drawQueue.isEmpty()) {
            drawFromQueue(drawQueue.poll());
        }

        glfwSwapBuffers(windowId);
    }

    public void draw(final Sprite sprite) {
        drawQueue.add(sprite);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowId);
    }

    private float getZoomX(final float zoom) {
        return (resolution.x * zoom) - resolution.x;
    }

    private float getZoomY(final float zoom) {
        return (resolution.y * zoom) - resolution.y;
    }

    private void updateProjectionMatrix() {
        projection = new Matrix4f().ortho(
                0.0F - getZoomX(zoom),
                resolution.x + getZoomX(zoom),
                resolution.y + getZoomY(zoom),
                0.0F - getZoomY(zoom),
                -1.0F,
                1.0F);
    }

    private void updateViewMatrix() {
        view = new Matrix4f();
        view.translate(new Vector3f(-cameraPosition.x, -cameraPosition.y, 0.0f));
        view.translate(new Vector3f(HALF_LEN * resolution.x, HALF_LEN * resolution.y, 0.0f));
        view = view.rotate(cameraRotation);
        view.translate(new Vector3f(-HALF_LEN * resolution.x, -HALF_LEN * resolution.y, 0.0f));
    }

    private void drawFromQueue(final Sprite sprite) {
        shader.setUniform("projection", projection);
        shader.setUniform("view", view);
        shader.setUniform("model", sprite.getModel());
        shader.setUniform("spriteColor", sprite.getColor());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        sprite.getTexture().bind();

        GL30.glBindVertexArray(vaoId);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
        GL30.glBindVertexArray(GL11.GL_NONE);
        GL13.glActiveTexture(GL11.GL_NONE);
    }
}
