package engine.voxels;

public interface VoxelRegistry {
    /** Register name if absent; return id. Flags are sticky once set. */
    short register(String name, int flags);

    /** Lookups; return -1 if missing. */
    short idOf(String name);
    String nameOf(short id);

    /** Property flags for an id. 0 if unknown. */
    int flagsOf(short id);
}
