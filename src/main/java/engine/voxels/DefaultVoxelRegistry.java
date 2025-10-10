package engine.voxels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class DefaultVoxelRegistry implements VoxelRegistry {
    private final Map<String, Short> nameToId = new HashMap<>();
    private final ArrayList<String> idToName = new ArrayList<>();
    private final ArrayList<Integer> idToFlags = new ArrayList<>();

    public DefaultVoxelRegistry() {
        // id 0 = air
        nameToId.put("air", (short)0);
        idToName.add("air");
        idToFlags.add(VoxelFlags.NONE);
    }

    @Override
    public synchronized short register(String name, int flags) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("name");
        Short existing = nameToId.get(name);
        if (existing != null) return existing;

        short id = (short) idToName.size();
        nameToId.put(name, id);
        idToName.add(name);
        idToFlags.add(flags);
        return id;
    }

    @Override
    public synchronized short idOf(String name) {
        Short id = nameToId.get(name);
        return id == null ? (short)-1 : id;
    }

    @Override
    public synchronized String nameOf(short id) {
        int i = Short.toUnsignedInt(id);
        return (i >= 0 && i < idToName.size()) ? idToName.get(i) : null;
    }

    @Override
    public synchronized int flagsOf(short id) {
        int i = Short.toUnsignedInt(id);
        return (i >= 0 && i < idToFlags.size()) ? idToFlags.get(i) : 0;
        }
}
