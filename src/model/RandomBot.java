package model;

import controller.GameController;
import view.ChessboardComponent;

import java.util.ArrayList;
import java.util.Random;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

public class RandomBot {
    private Random random;
    private ChessboardPoint src;
    private ChessboardPoint dest;

    public Chessboard model;
    private ChessboardComponent view;
    private GameController gameController;
    private ArrayList<ChessboardPoint> possibleDest;
    private ArrayList<ChessboardPoint> possibleSrc;
    private boolean isSrcExit=true;


    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }
    public void registerChessboard(Chessboard chessboard) {
        model = chessboard;
    }
    public void registerChessboardComponent(ChessboardComponent chessboardComponent) {
        view = chessboardComponent;
    }

    public RandomBot() {
        this.random = new Random();
        possibleDest=new ArrayList<ChessboardPoint>();
        possibleSrc=new ArrayList<ChessboardPoint>();
    }

    public ChessboardPoint src() {
        // 随机选择一个动物
        int rank = random.nextInt(1, 9);
        ChessPiece chess;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint possiblesrc = new ChessboardPoint(i, j);
                if(model.getChessPieceAt(possiblesrc)!=null){
                    if(model.getChessPieceAt(possiblesrc).getOwner()==PlayerColor.RED){
                        possibleSrc.add(possiblesrc);
                    }

                }
            }
        }

        if(possibleSrc.size()==1){
            int r = 0;
            src=possibleSrc.get(r);
        }
        if(possibleSrc.size()>1){
            int r=random.nextInt(0, possibleSrc.size());
            src=possibleSrc.get(r);
        }
        System.out.println("srcok");
        System.out.println(src);
        //判断存不存在可以走的格子
        for(int k=0;k<possibleSrc.size();k++){
            ChessboardPoint srcK=possibleSrc.get(k);
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    ChessboardPoint possibledest = new ChessboardPoint(i,j);
                    if(model.isValidMove(srcK,possibledest)||model.isValidCapture(srcK,possibledest)){
                        possibleDest.add(possibledest);
                    }
                }
            }
        }
        if(possibleDest.size()==0){
            isSrcExit=false;
        }
        possibleDest.clear();
        possibleSrc.clear();

        return src;

    }



    public ChessboardPoint dest(){
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint possibledest = new ChessboardPoint(i,j);
                if(model.isValidMove(src,possibledest)||model.isValidCapture(src,possibledest)){
                    possibleDest.add(possibledest);
                }
            }
        }
        if(possibleDest.size()==0&&isSrcExit){
            src();
            dest();
        }
        if(possibleDest.size()==0&&!isSrcExit){dest=new ChessboardPoint(0,3);}
        if(possibleDest.size()==1){
            int rank = 0;
            dest=possibleDest.get(rank);
        }
        if(possibleDest.size()>1){
            int rank=random.nextInt(0, possibleDest.size());
            dest=possibleDest.get(rank);
        }
        System.out.println("destok");
        // 返回移动指令
        System.out.println(dest);
        possibleDest.clear();
        return dest;
    }

    public void run(){
        src=src();
        dest=dest();
        if(model.isValidMove(src,dest)){
            model.moveChessPiece(src,dest);
            view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
            for(int i=0;i< CHESSBOARD_ROW_SIZE.getNum();i++){
                for(int j=0;j< CHESSBOARD_COL_SIZE.getNum();j++){
                    view.getGridComponentAt(new ChessboardPoint(i,j)).possibleMove=false;
                }
            }
            view.repaint();
            System.out.println("zoule");
            if(gameController.win()){gameController.checkWin();}
        }
        if (model.isValidCapture(src,dest)) {
            model.captureChessPiece(src,dest);
            view.removeChessComponentAtGrid(dest);
            view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));

            for(int i=0;i< CHESSBOARD_ROW_SIZE.getNum();i++){
                for(int j=0;j< CHESSBOARD_COL_SIZE.getNum();j++){
                    view.getGridComponentAt(new ChessboardPoint(i,j)).possibleMove=false;
                }
            }
            view.repaint();
            if(gameController.win()){gameController.checkWin();}
        }



    }
}