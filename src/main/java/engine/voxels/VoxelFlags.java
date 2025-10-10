package engine.voxels;

public final class VoxelFlags {
    private VoxelFlags() {}

    public static final int NONE            = 0;
    public static final int OPAQUE          = 1 << 0; // solid and fully light-occluding
    public static final int SOLID           = 1 << 1; // has collision
    public static final int TRANSPARENT     = 1 << 2; // rendered but not opaque (leaves, glass)
    public static final int LIGHT_EMITTING  = 1 << 3; // emits light level > 0
    public static final int NON_AIR_GEOMETRY= OPAQUE | SOLID | TRANSPARENT;
}
