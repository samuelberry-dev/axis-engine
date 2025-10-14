package engine.core;

import engine.input.Input;
import engine.time.Time;
import engine.window.Window;

public class EngineContext {
    private final Window window;
    private final Input input;
    private final Time time;

    public EngineContext(Window window, Input input, Time time) {
        this.window = window;
        this.input = input;
        this.time = time;
    }

    public Window window() { return window; }
    public Input input() { return input; }
    public Time time() { return time; }
}
