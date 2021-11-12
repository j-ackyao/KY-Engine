package ky;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    
    // only supports .wav .aiff .au .mid 

    private AudioInputStream audioStream;
    private Clip clip;
    private float volume=0;
    private float pan=0;

    public AudioPlayer (String audioFile) {
        setAudioFile(audioFile);
    }

    public void setAudioFile (String audioFile) {

        try {
            if (clip!=null) {
                clip.stop();
            }
            audioStream = AudioSystem.getAudioInputStream(new File(audioFile));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException e) {
            System.out.println(e.getMessage());
            System.out.println("Only supports .wave .aiff .au and midi related files");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    public void play () {
        clip.start();
    }

    public void pause () {
        clip.stop();
    }

    public float getVolume () {
        return volume;
    }

    public float getPan () {
        return pan;
    }

    public void setVolume (float decibels) {
        volume = decibels;
        if (volume > 6) 
            volume = 6;
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gain.setValue(volume);
    }

    public void setPan (float pos) {
        pan = pos;
        if (pan>1)
            pan=1;
        if (pan<-1)
            pan=-1;
        FloatControl pann = (FloatControl) clip.getControl(FloatControl.Type.PAN);
        pann.setValue(pan);
    }

    public void reset () {
        clip.setMicrosecondPosition(0);        
    }

    public void setTime (long min, long sec, long millis) {
        clip.setMicrosecondPosition((long)(min*6e7 + sec*1e6 + millis*1e3));
    }

    public long getTimeMillis () {
        return clip.getMicrosecondPosition()*1000;
    }

    public void setLoop (boolean continuously) {
        if (continuously)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        else
            clip.loop(0);
    }

    public void setLoop (int times) {
        clip.loop(times);
    }

}
