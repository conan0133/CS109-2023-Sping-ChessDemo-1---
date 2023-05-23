package view;

import controller.GameController;
import model.MusicPlayer;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    private final int ONE_CHESS_SIZE;
    private GameController gameController;
    private ChessboardComponent chessboardComponent;
    private JLabel j = new JLabel();
    private JLabel i = new JLabel();
    private boolean x=true;


    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;


        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addSettingButton();
        addRegretButton();
        addLabel();
        addChessboard();
        addChangeTheme();
        backGround();

    }

    public int getHeight(){return HEIGTH;}
    public int getWidth(){return WIDTH;}
    public JLabel getJ(){return j;}
    public JLabel getI(){return i;}


    private void addChangeTheme(){
        JButton button = new JButton("ChangeTheme");
        button.setLocation(HEIGTH, HEIGTH / 10 +120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            chessboardComponent.changeTheme();
            changeBackGround();
        });
    }
    private void backGround(){
        Image image1=new ImageIcon("D:\\java\\斗兽棋\\CS109-2023-Sping-ChessDemo(1)-副本\\resource\\OIP-C.png").getImage().getScaledInstance(WIDTH,HEIGTH,Image.SCALE_SMOOTH);
        ImageIcon imageI=new ImageIcon(image1);
        j=new JLabel(imageI);
        j.setLocation(0,0);
        j.setSize(WIDTH,HEIGTH);
        add(j);
        j.setVisible(true);

        Image image2=new ImageIcon("D:\\java\\斗兽棋\\CS109-2023-Sping-ChessDemo(1)-副本\\resource\\OPI-B.png").getImage().getScaledInstance(WIDTH,HEIGTH,Image.SCALE_SMOOTH);
        ImageIcon imageII=new ImageIcon(image2);
        i=new JLabel(imageII);
        i.setLocation(0,0);
        i.setSize(WIDTH,HEIGTH);
        add(i);
        i.setVisible(false);
    }
    private void changeBackGround(){
        if(x){
            j.setVisible(false);
            i.setVisible(true);
            //System.out.println("background1");
            x=false;
            return;
        }
        if(!x){
            i.setVisible(false);
            j.setVisible(true);
            //System.out.println("background2");
            x=true;
        }
    }


    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }
    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }
    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }




//轮数
    JLabel Label = new JLabel("turn: 1 BLUE");
    public JLabel getLabel(){return Label;}
    public void addLabel() {
        Label.setLocation(HEIGTH, HEIGTH / 10);
        Label.setSize(200, 60);
        Label.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Label);
    }


//各种各样的button


    private void addSettingButton(){
        JButton button = new JButton("setting");
        button.setLocation(HEIGTH, HEIGTH / 10 + 600);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            settingframe frame=new settingframe(600,1100,gameController,this);
            frame.setVisible(true);
        });
    }


    private void addRegretButton(){
        JButton button = new JButton("Regret");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            gameController.regret();
        });
    }



}
