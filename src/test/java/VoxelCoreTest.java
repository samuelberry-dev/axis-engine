import engine.voxels.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VoxelCoreTest {
    @Test
    void blockStatePacksAndUnpacks() {
        BlockState s = BlockState.of(123, 7);
        assertEquals(123, s.id());
        assertEquals(7, s.meta());
        assertEquals(s, s.withMeta(7));
        assertNotEquals(s, s.withMeta(8));
    }

    @Test
    void simpleChunkSetGetAndDirty() {
        SimpleChunk c = new SimpleChunk();
        assertTrue(c.isDirty(), "new chunk starts dirty (needs mesh)");

        c.markDirty(false);
        assertFalse(c.isDirty());

        BlockState stone = BlockState.of(1, 0);
        c.set(0, 0, 0, stone);
        assertTrue(c.isDirty(), "write should dirty");

        assertEquals(stone, c.get(0, 0, 0));
    }

    @Test
    void registryRegistersAndResolves() {
        VoxelRegistry reg = new DefaultVoxelRegistry();
        short stone = reg.register("stone", VoxelFlags.OPAQUE | VoxelFlags.SOLID);
        assertTrue(Short.toUnsignedInt(stone) > 0);
        assertEquals(stone, reg.idOf("stone"));
        assertEquals("stone", reg.nameOf(stone));
        assertEquals(VoxelFlags.OPAQUE | VoxelFlags.SOLID, reg.flagsOf(stone));
        assertEquals(0, reg.idOf("missing"));
    }
}
