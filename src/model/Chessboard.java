package model;

import java.util.ArrayList;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    public Cell[][] grid;
    public ArrayList<ChessPiece> blueDead;
    public ArrayList<ChessPiece> redDead;
    public ArrayList<Turn> turn;
    public ArrayList<Turn> getTurn(){
        return turn;
    }



    public Chessboard() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//7*9
        blueDead = new ArrayList<>();
        redDead = new ArrayList<>();
        initGrid();
        initPieces();
        turn=new ArrayList<Turn>();
    }

    public void initGrid() {//初始化棋盘
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void initPieces() {//初始化棋子，TODO：按规则放置棋子。
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat",1));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat",1));
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {//行差+列差
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    //悔棋
    public void regret(){
        if(turn.size()>0){
            Turn t=turn.get(turn.size()-1);
            ChessboardPoint dest=t.getDest();
            ChessboardPoint src=t.getSrc();
            ChessPiece deadChess=t.getDeadChess();
            ChessPiece movingChess=removeChessPiece(dest);
            setChessPiece(src,movingChess);
            if(deadChess!=null){setChessPiece(dest,deadChess);}
            turn.remove(getTurn().size()-1);
        }
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        Turn t=new Turn(src,dest,getChessPieceAt(src).getOwner());
        turn.add(t);
        //System.out.print(turn.size());
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        if(getChessPieceAt(dest).getOwner()==PlayerColor.BLUE){
            blueDead.add(getChessPieceAt(dest));
        }else{
            redDead.add(getChessPieceAt(dest));
        }
        Turn t=new Turn(src,dest,getChessPieceAt(src).getOwner(),getChessPieceAt(dest));
        turn.add(t);
        //System.out.print(t.toString());

        getGridAt(dest).removePiece();
        setChessPiece(dest,getChessPieceAt(src));
        getGridAt(src).removePiece();

    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {//TODO
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        if(isOwnDen(src,dest)){
            return false;
        }
        boolean b = calculateDistance(src,dest)==1;
        if(getChessPieceAt(src).getRank()==8||getChessPieceAt(src).getRank()==5||getChessPieceAt(src).getRank()==4||getChessPieceAt(src).getRank()==3||getChessPieceAt(src).getRank()==2){
            return b&&!isRiver(dest);
        }
        if(getChessPieceAt(src).getRank()==7||getChessPieceAt(src).getRank()==6){
            return (b&&!isRiver(dest))||(canJumpRiver(src,dest));
        }
        if(getChessPieceAt(src).getRank()==1){
            return b;
        }else {
            return false;
        }
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) == null) {
            return false;
        }
        if(getChessPieceOwner(src)==getChessPieceOwner(dest)){
            return false;
        }
        boolean b = calculateDistance(src,dest)==1;
        if(b&&isOwnTrap(src, dest)){
            return true;
        }
        if(getChessPieceAt(src).canCapture(getChessPieceAt(dest))) {
            if(getChessPieceAt(src).getRank()==8||getChessPieceAt(src).getRank()==5||getChessPieceAt(src).getRank()==4||getChessPieceAt(src).getRank()==3||getChessPieceAt(src).getRank()==2){
                if(b&&!isRiver(dest)){
                    return true;
                }
            }
            if(getChessPieceAt(src).getRank()==7||getChessPieceAt(src).getRank()==6){
                if (!isRiver(dest)&&(b||canJumpRiver(src,dest))){
                    return true;
                }
            }
            if(getChessPieceAt(src).getRank()==1){
                if(!isRiver(src)&&b) {
                    return true;
                }
                if(isRiver(src)&&isRiver(dest)&&b){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isRiver(ChessboardPoint dest){
        if(dest.getRow()>=3&&dest.getRow()<=5){
            if(dest.getCol()==1||dest.getCol()==2||dest.getCol()==4||dest.getCol()==5){
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }
    public boolean canJumpRiver(ChessboardPoint src,ChessboardPoint dest){
        if ((src.getRow() == 3 && src.getCol() == 0 && dest.getRow() == 3 && dest.getCol() == 3)
                || (src.getRow() == 3 && src.getCol() == 3 && dest.getRow() == 3 && dest.getCol() == 0)){
            return getChessPieceAt(new ChessboardPoint(3,1)) == null
                    && getChessPieceAt(new ChessboardPoint(3, 2)) == null;
        }
        if ((src.getRow() == 4 && src.getCol() == 0 && dest.getRow() == 4 && dest.getCol() == 3)
                || (src.getRow() == 4 && src.getCol() == 3 && dest.getRow() == 4 && dest.getCol() == 0)){
            return getChessPieceAt(new ChessboardPoint(4, 1)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 2)) == null;
        }
        if ((src.getRow() == 5 && src.getCol() == 0 && dest.getRow() == 5 && dest.getCol() == 3)
                || (src.getRow() == 5 && src.getCol() == 3 && dest.getRow() == 5 && dest.getCol() == 0)){
            return getChessPieceAt(new ChessboardPoint(5, 1)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 2)) == null;
        }
        if ((src.getRow() == 3 && src.getCol() == 3 && dest.getRow() == 3 && dest.getCol() == 6)
                || (src.getRow() == 3 && src.getCol() == 6 && dest.getRow() == 3 && dest.getCol() == 3)){
            return getChessPieceAt(new ChessboardPoint(3, 4)) == null
                    && getChessPieceAt(new ChessboardPoint(3, 5)) == null;
        }
        if ((src.getRow() == 4 && src.getCol() == 3 && dest.getRow() == 4 && dest.getCol() == 6)
                || (src.getRow() == 4 && src.getCol() == 6 && dest.getRow() == 4 && dest.getCol() == 3)){
            return getChessPieceAt(new ChessboardPoint(4, 4)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 5)) == null;
        }
        if ((src.getRow() == 5 && src.getCol() == 3 && dest.getRow() == 5 && dest.getCol() == 6)
                || (src.getRow() == 5 && src.getCol() == 6 && dest.getRow() == 5 && dest.getCol() == 3)){
            return getChessPieceAt(new ChessboardPoint(5, 4)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 5)) == null;
        }

        if ((src.getRow() == 2 && src.getCol() == 1 && dest.getRow() == 6 && dest.getCol() == 1)
                || (src.getRow() == 6 && src.getCol() == 1 && dest.getRow() == 2 && dest.getCol() == 1)){
            return getChessPieceAt(new ChessboardPoint(3, 1)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 1)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 1)) == null;
        }
        if ((src.getRow() == 2 && src.getCol() == 2 && dest.getRow() == 6 && dest.getCol() == 2)
                || (src.getRow() == 6 && src.getCol() == 2 && dest.getRow() == 2 && dest.getCol() == 2)){
            return getChessPieceAt(new ChessboardPoint(3, 2)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 2)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 2)) == null;
        }
        if ((src.getRow() == 2 && src.getCol() == 4 && dest.getRow() == 6 && dest.getCol() == 4)
                || (src.getRow() == 6 && src.getCol() == 4 && dest.getRow() == 2 && dest.getCol() == 4)){
            return getChessPieceAt(new ChessboardPoint(3, 4)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 4)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 4)) == null;
        }
        if ((src.getRow() == 2 && src.getCol() == 5 && dest.getRow() == 6 && dest.getCol() == 5)
                || (src.getRow() == 6 && src.getCol() == 5 && dest.getRow() == 2 && dest.getCol() == 5)){
            return getChessPieceAt(new ChessboardPoint(3, 5)) == null
                    && getChessPieceAt(new ChessboardPoint(4, 5)) == null
                    && getChessPieceAt(new ChessboardPoint(5, 5)) == null;
        }
        return false;
    }
    public boolean isOwnTrap(ChessboardPoint scr,ChessboardPoint dest){
        if(getChessPieceAt(scr).getOwner()==PlayerColor.BLUE){
            return (dest.getRow()==8&&dest.getCol()==2)||
                    (dest.getRow()==8&&dest.getCol()==4)||
                    (dest.getRow()==7&&dest.getCol()==3);
        }else {
            return(dest.getRow()==0&&dest.getCol()==2)||
                    (dest.getRow()==0&&dest.getCol()==4)||
                    (dest.getRow()==1&&dest.getCol()==3);
        }
    }
    public boolean isOpponentTrap(ChessboardPoint scr,ChessboardPoint dest){
        if(getChessPieceAt(scr).getOwner()==PlayerColor.RED){
            return (dest.getRow()==8&&dest.getCol()==2)||
                    (dest.getRow()==8&&dest.getCol()==4)||
                    (dest.getRow()==7&&dest.getCol()==3);
        }else {
            return(dest.getRow()==0&&dest.getCol()==2)||
                    (dest.getRow()==0&&dest.getCol()==4)||
                    (dest.getRow()==1&&dest.getCol()==3);
        }
    }
    public boolean isOwnDen(ChessboardPoint scr,ChessboardPoint dest){
        if(getChessPieceAt(scr).getOwner()==PlayerColor.BLUE){
            return (dest.getRow()==8&&dest.getCol()==3);
        }else{
            return (dest.getRow()==0&&dest.getCol()==3);
        }
    }
    public int getRedDead(){
        return redDead.size();
    }
    public int getBlueDead(){
        return blueDead.size();
    }
}
