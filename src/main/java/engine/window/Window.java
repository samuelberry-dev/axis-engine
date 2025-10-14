package engine.window;

public interface Window extends AutoCloseable {
    long handle();
    int width();
    int height();

    boolean shouldClose();
    void setShouldClose(boolean value);

    void pollEvents();
    void swapBuffers();
    void setVsync(boolean vsync);
    void setTitle(String title);
    @Override void close();
}
