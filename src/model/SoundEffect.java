package model;

import javax.sound.sampled.*;
import java.io.*;
public class SoundEffect{
    public void playSoundEffect() throws LineUnavailableException, UnsupportedAudioFileException, IOException {

        File path = new File("D:\\java\\斗兽棋\\CS109-2023-Sping-ChessDemo(1)-副本\\resource\\SoundEffect.wav");
        AudioInputStream in = AudioSystem.getAudioInputStream(path);
        Clip clip = AudioSystem.getClip();
        clip.open(in);
        clip.start();
    }
}