package view;

import controller.GameController;
import model.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class settingframe extends JFrame{
    private final int w;
    private final int h;
    private boolean x=true;
    private JLabel j = new JLabel();
    private JLabel i = new JLabel();
    private GameController gameController;
    private ChessboardComponent chessboardComponent;
    private ChessGameFrame chessGameFrame;
    public settingframe(int w,int h,GameController gameController,ChessGameFrame chessGameFrame){
        setTitle("setting");
        this.w=w;
        this.h=h;
        this.gameController=gameController;
        this.chessGameFrame=chessGameFrame;
        chessboardComponent=chessGameFrame.getChessboardComponent();


        setSize(this.w,this.h);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);

        addRestartButton();
        addLoadButton();
        addSaveButton();
        addMusicButton();


    }

    private void addRestartButton() {
        JButton button = new JButton("重新开始");
        button.addActionListener((e) -> {gameController.restart();});
        button.setLocation(w/4+40, h / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }



    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(w/4+40, h / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            try {
                gameController.loadGameFromFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(w/4+40, h / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String name = JOptionPane.showInputDialog(this,"Input Name here");
            try {
                gameController.saveGame(name);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void addMusicButton(){
        JButton button = new JButton("music");
        button.setLocation(w/4+40, h / 10 +480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            MusicPlayer music=new MusicPlayer();
            MusicFrame frame=new MusicFrame(600,400,music);
            frame.setVisible(true);
        });
    }


}
