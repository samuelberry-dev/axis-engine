package engine.voxels;

import static engine.voxels.VoxelConstants.*;

public final class SimpleChunk implements Chunk {
    private final int sx, sy, sz;
    private final int[] data;
    private volatile boolean dirty = true;

    public SimpleChunk() {
        this(CHUNK_SIZE_X, CHUNK_SIZE_Y, CHUNK_SIZE_Z);
    }

    public SimpleChunk(int sx, int sy, int sz) {
        this.sx = sx; this.sy = sy; this.sz = sz;
        this.data = new int[sx * sy * sz];
        // defaults to air (0)
    }

    @Override public int sizeX() { return sx; }
    @Override public int sizeY() { return sy; }
    @Override public int sizeZ() { return sz; }

    @Override
    public BlockState get(int x, int y, int z) {
        bounds(x, y, z);
        return BlockState.fromPacked(data[idx(x, y, z)]);
    }

    @Override
    public void set(int x, int y, int z, BlockState state) {
        bounds(x, y, z);
        int i = idx(x, y, z);
        int prev = data[i];
        int next = state.packed();
        if (prev != next) {
            data[i] = next;
            dirty = true;
        }
    }

    @Override public boolean isDirty() { return dirty; }
    @Override public void markDirty(boolean d) { this.dirty = d; }

    private void bounds(int x, int y, int z) {
        if ((x | y | z) < 0 || x >= sx || y >= sy || z >= sz) {
            throw new IndexOutOfBoundsException("local(" + x + "," + y + "," + z + ")");
        }
    }
}
