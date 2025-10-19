package engine.audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.system.MemoryUtil.*;

public class SoundBuffer implements AutoCloseable {
    public final int bufferId;

    private SoundBuffer(int id) { this.bufferId = id; }

    public static SoundBuffer fromResourceOgg(String path) {
        try {
            InputStream in = ClassLoader.getSystemResourceAsStream(path);
            if (in == null) throw new RuntimeException("Missing resource: " + path);
            byte[] bytes = in.readAllBytes();
            ByteBuffer data = BufferUtils.createByteBuffer(bytes.length);
            data.put(bytes).flip();

            STBVorbisInfo info = STBVorbisInfo.malloc();
            IntBuffer err = BufferUtils.createIntBuffer(1);
            long decoder = STBVorbis.stb_vorbis_open_memory(data, err, null);
            if (decoder == 0) throw new RuntimeException("stb_vorbis error: "+err.get(0));
            STBVorbis.stb_vorbis_get_info(decoder, info);

            int channels = info.channels();
            int sampleRate = info.sample_rate();

            int samples = STBVorbis.stb_vorbis_stream_length_in_samples(decoder) * channels;
            ShortBuffer pcm = BufferUtils.createShortBuffer(samples);
            STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm);
            STBVorbis.stb_vorbis_close(decoder);

            int format = (channels == 1) ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;

            int id = alGenBuffers();
            alBufferData(id, format, pcm, sampleRate);

            info.free();
            return new SoundBuffer(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void close() { alDeleteBuffers(bufferId); }
}
