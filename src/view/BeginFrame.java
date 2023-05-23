package view;

import javax.swing.*;
import java.awt.*;

public class BeginFrame extends JFrame{
    private final int WIDTH;
    private final int HEIGTH;

    private final int Width;
    private final int Height;


    private ChessGameFrame frame;
    public BeginFrame(int width, int height,int W,int H) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        WIDTH = width;
        HEIGTH = height;
        Width=W;
        Height=H;
        frame =new ChessGameFrame(Width,Height);


        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addBeginButton();
    }


    public ChessGameFrame getChessGameFrame(){return frame;}



    private void addBeginButton() {
        JButton button = new JButton("开始游戏");
        button.addActionListener((e) -> {
            setVisible(false);
            frame.setVisible(true);
        });
        button.setLocation(HEIGTH/2, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


}
