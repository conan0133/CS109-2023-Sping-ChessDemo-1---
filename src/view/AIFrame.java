package view;

import javax.swing.*;
import java.awt.*;

public class AIFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;

    private final int Width;
    private final int Height;


    private ChessGameFrame frame;
    public AIFrame(int width, int height,int W,int H) {
        setTitle("AI"); //设置标题
        WIDTH = width;
        HEIGTH = height;
        Width=W;
        Height=H;
        frame =new ChessGameFrame(Width,Height);


        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addEasyAIButton();
        addNormalAIButton();
    }
    public ChessGameFrame getChessGameFrame(){return frame;}
    private void addEasyAIButton() {
        JButton button = new JButton("Easy");
        button.addActionListener((e) -> {
            setVisible(false);
            frame.setVisible(true);
            frame.getGameController().setRandomBotOn(true);
            frame.getGameController().setIsRandomBotOn(true);
            frame.getGameController().botType=true;
        });
        button.setLocation(HEIGTH/2, HEIGTH / 10 + 160);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addNormalAIButton() {
        JButton button = new JButton("Normal");
        button.addActionListener((e) -> {
            setVisible(false);
            frame.setVisible(true);
            frame.getGameController().setRandomBotOn(true);
            frame.getGameController().setIsRandomBotOn(true);
            frame.getGameController().botType=false;
        });
        button.setLocation(HEIGTH/2, HEIGTH / 10 + 160);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

}