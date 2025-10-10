package engine.voxels;

/**
 * Minimal chunk interface (16x16x16). Implementation can change internally;
 */
public interface Chunk {
    int sizeX();
    int sizeY();
    int sizeZ();

    /** Get/set state at local coords [0..size-1]. */
    BlockState get(int x, int y, int z);
    void set(int x, int y, int z, BlockState state);

    /** Dirty means mesh/light/saves need refreshing. */
    boolean isDirty();
    void markDirty(boolean dirty);

    /** Optional helpers. */
    default void fill(BlockState state) {
        for (int y = 0; y < sizeY(); y++)
            for (int z = 0; z < sizeZ(); z++)
                for (int x = 0; x < sizeX(); x++)
                    set(x, y, z, state);
    }
}
