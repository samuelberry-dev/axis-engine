package engine.voxels;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static engine.voxels.WorldCoords.*;

/** Simple sparse world backed by a concurrent chunk map. */
public final class WorldImpl implements World {
    private final ConcurrentMap<ChunkPos, Chunk> chunks = new ConcurrentHashMap<>();

    @Override
    public Chunk getOrCreateChunk(ChunkPos cpos) {
        Objects.requireNonNull(cpos, "cpos");
        return chunks.computeIfAbsent(cpos, k -> new SimpleChunk());
    }

    @Override
    public Chunk getChunk(ChunkPos cpos) {
        return chunks.get(cpos);
    }

    @Override
    public BlockState getBlock(BlockPos wpos) {
        Objects.requireNonNull(wpos, "wpos");
        ChunkPos cpos = toChunkPos(wpos);
        Chunk c = getChunk(cpos);
        if (c == null) return BlockState.air();
        return c.get(localX(wpos.x), localY(wpos.y), localZ(wpos.z));
    }

    @Override
    public void setBlock(BlockPos wpos, BlockState state) {
        Objects.requireNonNull(wpos, "wpos");
        Objects.requireNonNull(state, "state");
        ChunkPos cpos = toChunkPos(wpos);
        Chunk c = getOrCreateChunk(cpos);
        c.set(localX(wpos.x), localY(wpos.y), localZ(wpos.z), state);
    }

    /** For tests/debug. */
    public int loadedChunkCount() { return chunks.size(); }
}
