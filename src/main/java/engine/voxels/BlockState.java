package engine.voxels;

import static engine.voxels.VoxelConstants.MAX_BLOCK_ID;

public final class BlockState {
    private final int packed;

    private BlockState(int packed) { this.packed = packed; }

    public static BlockState of(int id, int meta) {
        if (id < 0 || id > MAX_BLOCK_ID) {
            throw new IllegalArgumentException("id out of range: " + id);
        }
        int m = (meta & 0xFFFF);
        return new BlockState((m << 16) | (id & 0xFFFF));
    }

    public static BlockState fromPacked(int packed) {
        return new BlockState(packed);
    }

    public static BlockState air() { return of(0, 0); }

    public int id()   { return packed & 0xFFFF; }
    public int meta() { return (packed >>> 16) & 0xFFFF; }

    public int packed() { return packed; }

    public BlockState withId(int newId)     { return of(newId, meta()); }
    public BlockState withMeta(int newMeta) { return of(id(), newMeta); }

    @Override public String toString() { return "BlockState{id=" + id() + ", meta=" + meta() + "}"; }
    @Override public int hashCode() { return packed; }
    @Override public boolean equals(Object o) {
        return (o instanceof BlockState) && ((BlockState) o).packed == this.packed;
    }
}
