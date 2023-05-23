package view;

import model.MusicPlayer;

import javax.swing.*;
import java.awt.*;

public class MusicFrame extends JFrame{
    private int w;
    private int h;
    private MusicPlayer music;

    public MusicFrame(int w, int h, MusicPlayer music) {
        this.w=w;
        this.h=h;
        this.music=music;
        setTitle("music");

        setSize(this.w,this.h);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);

        addMusic1();
        addMusic2();
        addMusic3();
    }








    public void addMusic1(){
        JButton button=new JButton("欢快风格");
        button.setLocation(h/2, h/10+40);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            music.over();
            music=new MusicPlayer();
            music.setFile("D:\\java\\斗兽棋\\CS109-2023-Sping-ChessDemo(1)-副本\\resource\\欢快风格.wav");
            music.play();
        });

    }

    public void addMusic2(){
        JButton button=new JButton("舒缓风格");
        button.setLocation(h/2, h/10+140);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            music.over();
            music=new MusicPlayer();
            music.setFile("D:\\java\\斗兽棋\\CS109-2023-Sping-ChessDemo(1)-副本\\resource\\舒缓风格.wav");
            music.play();
        });

    }

    public void addMusic3(){
        JButton button=new JButton("无音乐");
        button.setLocation(h/2, h/10+240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            music.over();
        });

    }
}
