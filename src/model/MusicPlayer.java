package model;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MusicPlayer {
    private File file;
    private volatile boolean isPlay;
    private PlayThread playThread;



    public void setFile(String musicLocation) {
        this.file = new File(musicLocation);

    }

    public void play() {
        if (playThread == null) {
            playThread = new PlayThread();
            playThread.start();
        }


    }

    public void over() {
        isPlay = false;
        playThread = null;

    }

    private class PlayThread extends Thread {

        @Override
        public void run() {
            isPlay = true;
            do {

                SourceDataLine sourceDataLine = null;
                BufferedInputStream bufIn = null;
                AudioInputStream audioIn = null;
                try {
                    bufIn = new BufferedInputStream(new FileInputStream(file));
                    audioIn = AudioSystem.getAudioInputStream(bufIn);

                    AudioFormat format = audioIn.getFormat();
                    sourceDataLine = AudioSystem.getSourceDataLine(format);
                    sourceDataLine.open();


                    sourceDataLine.start();
                    byte[] buf = new byte[512];
//					System.out.println(audioIn.available());
                    int len = -1;
                    while (isPlay && (len = audioIn.read(buf)) != -1) {
                        sourceDataLine.write(buf, 0, len);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    if (sourceDataLine != null) {
                        sourceDataLine.drain();
                        sourceDataLine.close();
                    }
                    try {
                        if (bufIn != null) {
                            bufIn.close();
                        }
                        if (audioIn != null) {
                            audioIn.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } while (isPlay);
        }
    }


}


