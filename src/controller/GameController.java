package controller;


import listener.GameListener;
import model.*;
import view.CellComponent;

import view.Chess.*;
import view.ChessGameFrame;
import view.ChessboardComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;


/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;
    private ChessboardPoint selectedPoint;
    private ChessGameFrame frame;
    public PlayerColor winner;


    // Record whether there is a selected piece before
    public GameController(ChessboardComponent view, Chessboard model,ChessGameFrame frame) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();

        this.frame =frame;
        frame.registerController(this);


    }

    public ChessboardComponent getView(){return view;}

    private void initialize() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
            }
        }
    }

    // after a valid move swap the player
    private int n=0;
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        n++;
        if(currentPlayer==PlayerColor.BLUE){
            frame.getLabel().setText("turn: "+(model.getTurn().size()+2)/2+" BLUE");
        }else{
            frame.getLabel().setText("turn: "+(model.getTurn().size()+1)/2+" RED");
        }
    }

    private boolean win() {
        if(model.getChessPieceAt(new ChessboardPoint(0,3))!=null||model.getRedDead()==8||model.getChessPieceAt(new ChessboardPoint(8,3))!=null||model.getBlueDead()==8){return true;}
        return false;
        // TODO: Check the board if there is a winner
    }

    private void checkWin() {
        if(model.getChessPieceAt(new ChessboardPoint(0,3))!=null||model.getRedDead()==8){
            this.winner=PlayerColor.BLUE;
            JOptionPane.showMessageDialog(frame, "BLUE WIN !");
        }
        if(model.getChessPieceAt(new ChessboardPoint(8,3))!=null||model.getBlueDead()==8){
            this.winner=PlayerColor.RED;
            JOptionPane.showMessageDialog(frame, "RED WIN !");
        }
    }


    public void regret(){
        //getTurn得到的是一个Arraylist,你理智点，，，
        if(model.getTurn().size()>0){
            Turn t=model.getTurn().get(model.getTurn().size()-1);
            model.regret();
            ChessboardPoint dest=t.getDest();
            ChessboardPoint src=t.getSrc();
            ChessPiece deadChess=t.getDeadChess();
            view.setChessComponentAtGrid(src,view.removeChessComponentAtGrid(dest));
            if(deadChess!=null){view.setChessComponentAtGrid(dest,view.getChessComponent(deadChess));}
            selectedPoint = null;
            swapColor();
            view.repaint();
        }
    }




    // click an empty cell
    public void onPlayerEnterCell(ChessboardPoint point){
        view.getGridComponentAt(point).possibleMove=true;
        view.repaint();
    }
    public void onPlayerExitCell(ChessboardPoint point){
        view.getGridComponentAt(point).possibleMove=false;
        view.repaint();
    }





    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            for(int i=0;i< CHESSBOARD_ROW_SIZE.getNum();i++){
                for(int j=0;j< CHESSBOARD_COL_SIZE.getNum();j++){
                    view.getGridComponentAt(new ChessboardPoint(i,j)).possibleMove=false;
                }
            }
            selectedPoint = null;
            swapColor();
            view.repaint();
            checkWin();
            if(win()){checkWin();}
            }
        }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                for(int i=0;i< CHESSBOARD_ROW_SIZE.getNum();i++){
                    for(int j=0;j< CHESSBOARD_COL_SIZE.getNum();j++){
                        ChessboardPoint dest = new ChessboardPoint(i,j);
                        if(model.isValidMove(point,dest)||model.isValidCapture(point,dest)){
                            view.getGridComponentAt(dest).possibleMove=true;
                            view.repaint();
                        }
                    }
                }
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            for(int i=0;i< CHESSBOARD_ROW_SIZE.getNum();i++){
                for(int j=0;j< CHESSBOARD_COL_SIZE.getNum();j++){
                    ChessboardPoint dest = new ChessboardPoint(i,j);
                    view.getGridComponentAt(dest).possibleMove=false;
                    view.repaint();
                }
            }
            component.repaint();
        }
        // TODO: Implement capture function
        if (selectedPoint!=null&& !selectedPoint.equals(point)&&model.isValidCapture(selectedPoint,point)) {
            model.captureChessPiece(selectedPoint,point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            for(int i=0;i< CHESSBOARD_ROW_SIZE.getNum();i++){
                for(int j=0;j< CHESSBOARD_COL_SIZE.getNum();j++){
                    view.getGridComponentAt(new ChessboardPoint(i,j)).possibleMove=false;
                }
            }
            swapColor();
            view.repaint();
            if(win()){checkWin();}
        }
    }

    public void restart(){
        model.initGrid();
        model.initPieces();
        view.removeAll();
        view.initiateGridComponents();
        view.initiateChessComponent(model);
        view.repaint();
        frame.getLabel().setText("turn: 1 BLUE");
        currentPlayer=PlayerColor.BLUE;
        selectedPoint=null;
        model.getTurn().clear();
        model.redDead.clear();
        model.blueDead.clear();
        this.winner=null;
    }



    public void saveGame(String fileName) throws IOException {
        String location = "D:\\java\\斗兽棋\\CS109-2023-Sping-ChessDemo(1)-副本\\save\\"+fileName+".txt";
        File file = new File(location);
        if(file.exists()){
            int n = JOptionPane.showConfirmDialog(view, "存档名重复，是否覆盖?", "存档重复", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                file.delete();
            }else{
                return;
            }
        }
        FileWriter fileWriter=new FileWriter(location,true);
        fileWriter.write(model.turn.size()+"\n");
        for(int i=0;i<model.turn.size();i++){
            fileWriter.write(model.turn.get(i).toString()+"\n");
        }
        fileWriter.write(currentPlayer == PlayerColor.BLUE ? "b" : "r");
        fileWriter.write("\n");
        fileWriter.close();
    }

    public void loadGameFromFile() throws IOException {
        JFileChooser chooser=new JFileChooser();
        chooser.setCurrentDirectory(new File("D:\\java\\斗兽棋\\CS109-2023-Sping-ChessDemo(1)-副本\\save"));
        chooser.showOpenDialog(view);
        File file = chooser.getSelectedFile();
        if(file!=null){
            if(!file.getName().contains(".txt")) {
                JOptionPane.showConfirmDialog(null, "文件格式错误", "无法读档", JOptionPane.ERROR_MESSAGE);
                return;
            }


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            ArrayList<String> dataList = new ArrayList<>();
            String data=null;
            while((data=bufferedReader.readLine())!=null) {
                dataList.add(data);
            }
            int turnNum = Integer.parseInt(dataList.remove(0));
            boolean b=true;
            if(turnNum!=(dataList.size()-1)){
                b = false;
            }
            for(int i=0;i<turnNum;i++){
                data=dataList.get(i);
                if(i%2==0&&data.charAt(0)!='b'){
                    b=false;
                }if(i%2==1&&data.charAt(0)!='r'){
                    b=false;
                }
            }
            if(!b){
                JOptionPane.showConfirmDialog(null, "对弈记录错误", "无法读档", JOptionPane.ERROR_MESSAGE);
                return;
            }
            restart();
            for(int i=0;i<turnNum;i++){
                data=dataList.get(i);
                ChessboardPoint src = new ChessboardPoint(Integer.parseInt(data.charAt(3)+""),Integer.parseInt(data.charAt(5)+""));
                ChessboardPoint dest = new ChessboardPoint(Integer.parseInt(data.charAt(9)+""),Integer.parseInt(data.charAt(11)+""));
                boolean capture = !data.contains("null");
                if(!capture){
                    if(!model.isValidMove(src,dest)){
                        JOptionPane.showConfirmDialog(null, "对弈记录违法", "无法读档", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    model.moveChessPiece(src,dest);
                    view.setChessComponentAtGrid(dest,view.removeChessComponentAtGrid(src));
                    selectedPoint=null;
                    swapColor();
                    view.repaint();
                }else{
                    if(!model.isValidCapture(src,dest)){
                        JOptionPane.showConfirmDialog(null, "对弈记录违法", "无法读档", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    model.captureChessPiece(src,dest);
                    view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest,view.removeChessComponentAtGrid(src));
                    swapColor();
                    view.repaint();
                }
            }
        }
    }








}








