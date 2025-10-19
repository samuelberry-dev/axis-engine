package engine.audio;

import java.nio.FloatBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.system.MemoryStack;

public final class Audio {
    private static long device;
    private static long context;

    public static void init() {
        device = ALC10.alcOpenDevice((java.nio.ByteBuffer) null);
        if (device == 0L) throw new IllegalStateException("OpenAL device failed");

        context = ALC10.alcCreateContext(device, new int[]{0});
        if (context == 0L) throw new IllegalStateException("OpenAL context failed");

        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(ALC.createCapabilities(device));

        AL10.alDistanceModel(AL10.AL_INVERSE_DISTANCE_CLAMPED);
    }

    public static void setListener(float x, float y, float z,
                                   float atx, float aty, float atz,
                                   float upx, float upy, float upz) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer orientation = stack.floats(atx, aty, atz, upx, upy, upz);
            AL10.alListenerfv(AL10.AL_ORIENTATION, orientation);
        }
    }

    public static void shutdown() {
        ALC10.alcMakeContextCurrent(0);
        if (context != 0L) ALC10.alcDestroyContext(context);
        if (device != 0L) ALC10.alcCloseDevice(device);
    }

    private Audio() {}
}
