package engine.core;

import engine.voxels.*;

public final class DevHarness {
    public static void main(String[] args) {
        System.out.println("AxisEngine DevHarness — logic-only demo");

        WorldImpl world = new WorldImpl();
        BlockState stone = BlockState.of(1, 0);

        world.setBlock(new BlockPos(0, 0, 0), stone);

        world.setBlock(new BlockPos(16, 0, 0), stone);

        world.setBlock(new BlockPos(-1, 0, -1), stone);

        System.out.println("Chunks loaded: " + world.loadedChunkCount());
        System.out.println("(0,0,0): " + world.getBlock(new BlockPos(0, 0, 0)));
        System.out.println("(16,0,0): " + world.getBlock(new BlockPos(16, 0, 0)));
        System.out.println("(-1,0,-1): " + world.getBlock(new BlockPos(-1, 0, -1)));

        System.out.println("Done.");
    }
}
