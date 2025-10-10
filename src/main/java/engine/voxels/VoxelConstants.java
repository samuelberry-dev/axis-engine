package engine.voxels;

/** Engine-wide voxel constants. */
public final class VoxelConstants {
    private VoxelConstants() {}

    /** Chunk dimensions (X,Z width/length, Y height). */
    public static final int CHUNK_SIZE_X = 16;
    public static final int CHUNK_SIZE_Y = 16; // vertical sections;
    public static final int CHUNK_SIZE_Z = 16;

    public static final int CHUNK_VOLUME = CHUNK_SIZE_X * CHUNK_SIZE_Y * CHUNK_SIZE_Z;

    public static final int MAX_BLOCK_ID = 0xFFFF;

    public static int idx(int x, int y, int z) {
        return (y << 8) | (z << 4) | x;
    }
}
