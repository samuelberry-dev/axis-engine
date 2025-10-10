package engine.voxels;

import static engine.voxels.VoxelConstants.*;

public final class WorldCoords {
    private WorldCoords() {}

    public static ChunkPos toChunkPos(BlockPos w) {
        int cx = Math.floorDiv(w.x, CHUNK_SIZE_X);
        int cy = Math.floorDiv(w.y, CHUNK_SIZE_Y);
        int cz = Math.floorDiv(w.z, CHUNK_SIZE_Z);
        return new ChunkPos(cx, cy, cz);
    }

    public static int localX(int wx) { return Math.floorMod(wx, CHUNK_SIZE_X); }
    public static int localY(int wy) { return Math.floorMod(wy, CHUNK_SIZE_Y); }
    public static int localZ(int wz) { return Math.floorMod(wz, CHUNK_SIZE_Z); }
}
