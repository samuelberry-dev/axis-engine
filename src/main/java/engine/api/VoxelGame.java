package engine.api;

public interface VoxelGame {
    void onStart();          // engine created runtime/resources
    void onTick(float dt);   // called by engine loop (dt in seconds)
    void onRender();         // render hook
    void onStop();           // shutdown/save hooks
}
