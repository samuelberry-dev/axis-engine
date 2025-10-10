package engine.voxels;

import java.util.Objects;

public final class BlockPos {
    public final int x, y, z;

    public BlockPos(int x, int y, int z) { this.x = x; this.y = y; this.z = z; }

    public BlockPos add(int dx, int dy, int dz) { return new BlockPos(x + dx, y + dy, z + dz); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockPos)) return false;
        BlockPos p = (BlockPos) o;
        return x == p.x && y == p.y && z == p.z;
    }
    @Override public int hashCode() { return Objects.hash(x, y, z); }
    @Override public String toString() { return "BlockPos(" + x + "," + y + "," + z + ")"; }
}
