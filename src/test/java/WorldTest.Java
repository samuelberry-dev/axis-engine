import engine.voxels.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WorldTest {
    @Test
    void setGetAcrossChunkBoundariesAndNegatives() {
        WorldImpl w = new WorldImpl();
        BlockState stone = BlockState.of(1, 0);

        // origin
        w.setBlock(new BlockPos(0, 0, 0), stone);
        assertEquals(stone, w.getBlock(new BlockPos(0, 0, 0)));

        // positive edge (x=15) and next chunk (x=16)
        w.setBlock(new BlockPos(15, 0, 0), stone);
        w.setBlock(new BlockPos(16, 0, 0), stone);
        assertEquals(stone, w.getBlock(new BlockPos(15, 0, 0)));
        assertEquals(stone, w.getBlock(new BlockPos(16, 0, 0)));

        // negative coords (should land in chunk -1)
        w.setBlock(new BlockPos(-1, 0, -1), stone);
        assertEquals(stone, w.getBlock(new BlockPos(-1, 0, -1)));

        // sanity: at least two chunks should be loaded
        assertTrue(w.loadedChunkCount() >= 2);
    }
}
