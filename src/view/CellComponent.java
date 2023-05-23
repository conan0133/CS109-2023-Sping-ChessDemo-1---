package view;

import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color background;
    public boolean possibleMove;
    public boolean isMouseOn;

    public CellComponent(Color background, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.background = background;
        isMouseOn=false;

        addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {
                isMouseOn=true;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                isMouseOn=false;
                repaint();
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
