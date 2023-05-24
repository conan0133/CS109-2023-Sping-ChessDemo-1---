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

        do{
            int r=random.nextInt(0, possibleSrc.size());
            src=possibleSrc.get(r);

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
            }else isSrcExit=true;
        }while(!isSrcExit);

        System.out.println("srcok");
        System.out.println(src);
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

        ArrayList<Integer> value = new ArrayList<>();
        for(int i=0;i<possibleDest.size();i++){
            ChessboardPoint point = possibleDest.get(i);
            int x = point.getRow();
            int y = point.getCol();
            if(y<=3){
                if(model.isValidCapture(src,point)) {
                    value.add(x + y+model.getChessPieceAt(point).getRank);
                }else value.add(x+y);
            }else{
                if(model.isValidCapture(src,point)) {
                    value.add(x + (6 - y)+model.getChessPieceAt(point).getRank);
                }else value.add(x+(6-y));
            }
        }
        int max = value.get(0);
        for(int i=1;i<possibleDest.size();i++){
            if(value.get(i)>max){
                max=value.get(i);
            }
        }
        int index = value.indexOf(max);
        dest = possibleDest.get(index)
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