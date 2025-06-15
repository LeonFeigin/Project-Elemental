package audio;

import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.net.URL;
import java.util.ArrayList;
import java.io.*;
import java.lang.reflect.Array;

public class audioPlayer {

    private ArrayList<Clip> clips = new ArrayList<Clip>();
    public ArrayList<String> clipsName = new ArrayList<String>();

    public static File getFile(String fileName) {
        try {
            return new File("audio/allAudios/" + fileName + ".wav");
        } catch (Exception e) {
            
        }
        return null;
    }

    public void update(){
        for (int i = 0; i < clips.size(); i++) {
            Clip clip = clips.get(i);
            if (!clip.isRunning()) {
                clip.close();
                clips.remove(i);
                clipsName.remove(i); // Remove the corresponding name as well
                i--; // Adjust index after removal
            }
        }
    }

    public void playAudio(String fileName) {
        if(clipsName.contains(fileName)) {
            return; // If the audio is already playing, do not play it again
        }
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getFile(fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.setFramePosition(0);
            clip.start();
            clips.add(clip);
            clipsName.add(fileName);
        } catch (Exception e) {
        }
    }

    public void playAudioForever(String fileName){
        if(clipsName.contains(fileName)) {
            return; // If the audio is already playing, do not play it again
        }
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getFile(fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            clips.add(clip);
            clipsName.add(fileName);
        } catch (Exception e) {
        }
    }

    public void stopPlaying(){
        for (Clip clip : clips) {
            clip.stop();
            clip.close();
        }
        clips.clear();
        clipsName.clear();
    }
}
