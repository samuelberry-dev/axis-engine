package engine.voxels;

/**
 * Renderless world surface backed by a sparse chunk map.
 * Coordinates are in block space unless stated otherwise.
 */
public interface World {
    /** Get or create the chunk at chunk coordinates. */
    Chunk getOrCreateChunk(ChunkPos cpos);

    /** Get chunk if loaded, else null. */
    Chunk getChunk(ChunkPos cpos);

    /** Read/write a block in world (block) coordinates. */
    BlockState getBlock(BlockPos wpos);
    void setBlock(BlockPos wpos, BlockState state);

    /** Convenience helpers. */
    default int chunkSizeX() { return VoxelConstants.CHUNK_SIZE_X; }
    default int chunkSizeY() { return VoxelConstants.CHUNK_SIZE_Y; }
    default int chunkSizeZ() { return VoxelConstants.CHUNK_SIZE_Z; }
}
