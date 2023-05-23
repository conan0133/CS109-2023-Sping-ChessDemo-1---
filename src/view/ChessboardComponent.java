package view;


import controller.GameController;
import model.*;
import view.Chess.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];

    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> densCell= new HashSet<>();
    private final Set<ChessboardPoint> trapCell= new HashSet<>();
    private final Set<ChessboardPoint> ColorCell=new HashSet<>();
    private int x=0;


    private GameController gameController;


    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateGridComponents();

    }



    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    //设置棋子
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard

                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    gridComponents[i][j].add(getChessComponent(chessPiece));
                }
            }
        }

    }

    public ChessComponent getChessComponent(ChessPiece chessPiece){
        switch(chessPiece.getName()){
            case "Elephant":
                return new ElephantChessComponent(chessPiece.getOwner(),CHESS_SIZE);
            case "Lion":
                return new LionChessComponent(chessPiece.getOwner(), CHESS_SIZE);
            case "Tiger":
                return new TigerChessComponent(chessPiece.getOwner(), CHESS_SIZE);
            case "Leopard":
                return new LeopardChessComponent(chessPiece.getOwner(), CHESS_SIZE);
            case "Wolf":
                return new WolfChessComponent(chessPiece.getOwner(), CHESS_SIZE);
            case "Dog":
                return new DogChessComponent(chessPiece.getOwner(), CHESS_SIZE);
            case "Cat":
                return new CatChessComponent(chessPiece.getOwner(), CHESS_SIZE);
            case "Rat":
                return new RatChessComponent(chessPiece.getOwner(), CHESS_SIZE);
        }
        return null;
    }

    //设置河流、陷阱、兽穴
    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));

        densCell.add(new ChessboardPoint(0,3));
        densCell.add(new ChessboardPoint(8,3));

        trapCell.add(new ChessboardPoint(0,2));
        trapCell.add(new ChessboardPoint(0,4));
        trapCell.add(new ChessboardPoint(1,3));
        trapCell.add(new ChessboardPoint(8,2));
        trapCell.add(new ChessboardPoint(8,4));
        trapCell.add(new ChessboardPoint(7,3));



        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(new Color(74, 178, 243), calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                else if(densCell.contains(temp)){
                    cell = new CellComponent(Color.orange,calculatePoint(i,j),CHESS_SIZE);
                    this.add(cell);
                }
                else if(trapCell.contains(temp)){
                    cell = new CellComponent(Color.white,calculatePoint(i,j),CHESS_SIZE);
                    this.add(cell);
                }
                else {
                    cell = new CellComponent(new Color(204, 202, 202), calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void changeTheme(){
        if(x==0){
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 7; j++) {
                    ChessboardPoint temp = new ChessboardPoint(i, j);
                    if (riverCell.contains(temp)) {
                        gridComponents[i][j].setBackground(new Color(51,139,219));
                    }
                    else if(!densCell.contains(temp)&&!trapCell.contains(temp)){
                        gridComponents[i][j].setBackground(new Color(104, 211, 142));
                    }
                }
            }
            repaint();
            revalidate();
            //System.out.print(1);
            x=1;
            return;
        }
        if(x==1){
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 7; j++) {
                    ChessboardPoint temp = new ChessboardPoint(i, j);
                    if (riverCell.contains(temp)) {
                        gridComponents[i][j].setBackground(new Color(74, 178, 243));
                    }
                    else if(!densCell.contains(temp)&&!trapCell.contains(temp)){
                        gridComponents[i][j].setBackground(new Color(204, 202, 202));
                    }
                }
            }
            repaint();
            revalidate();
            x=0;
            //System.out.print(2);
            return;
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }


    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            try {
                new SoundEffect().playSoundEffect();
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }






    public void buleWin() {
        JOptionPane.showMessageDialog(this, "blue win");
    }
    public void redWin(){
        JOptionPane.showMessageDialog(this, "red win");
    }




}
