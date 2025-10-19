package engine.audio;

import static org.lwjgl.openal.AL10.*;

public class SoundSource implements AutoCloseable {
    private final int src;

    public SoundSource() {
        src = alGenSources();
        alSourcef(src, AL_GAIN, 1f);
        alSourcef(src, AL_ROLLOFF_FACTOR, 1f);
        alSourcef(src, AL_REFERENCE_DISTANCE, 2.0f);
        alSourcef(src, AL_MAX_DISTANCE, 48.0f);
        alSourcei(src, AL_LOOPING, AL_FALSE);
    }

    public void setBuffer(SoundBuffer buf) { alSourcei(src, AL_BUFFER, buf.bufferId); }
    public void setPos(float x,float y,float z){ alSource3f(src, AL_POSITION, x,y,z); }
    public void setGain(float g){ alSourcef(src, AL_GAIN, g); }
    public void play(){ alSourcePlay(src); }

    @Override public void close(){ alDeleteSources(src); }
}
