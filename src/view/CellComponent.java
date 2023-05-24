package view;

import model.ChessboardPoint;
import model.SoundEffect;
import view.Chess.ChessComponent;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color background;
    public boolean possibleMove;
    public boolean isMouseOn;
    private ChessboardComponent chessboardComponent;
    private int x;
    private int y;

    public CellComponent(Color background, Point location, int size, ChessboardComponent chessboardComponent,int x,int y) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.background = background;
        this.chessboardComponent=chessboardComponent;
        this.x=x;
        this.y=y;
        isMouseOn=false;

        addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {
                isMouseOn=true;
                chessboardComponent.repaint();
            }
            public void mouseExited(MouseEvent e) {
                isMouseOn=false;
                chessboardComponent.repaint();

            }

            @Override
            public void mousePressed(MouseEvent e) {
                chessboardComponent.processMouseEvent(e,x,y);
                //System.out.println(e.getX()+ e.getY());
                //System.out.println(chessboardComponent.getChessboardPoint(e.getPoint()));
            }
        });
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(background);
        if(possibleMove){
            g.setColor(Color.pink);
        }
        if(isMouseOn){
            g.setColor(Color.magenta);
        }

        g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
    }

    public void setBackground(Color background) {
        this.background = background;
    }



}
