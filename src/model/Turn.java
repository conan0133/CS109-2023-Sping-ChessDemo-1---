package model;

import view.Chess.ChessComponent;

import java.util.Objects;

public class Turn {
    private ChessboardPoint src;
    private ChessboardPoint dest;
    private  String color;
    private  ChessPiece deadChess;

    public ChessboardPoint getSrc(){return src;}
    public ChessboardPoint getDest(){return dest;}
    public ChessPiece getDeadChess() {return deadChess;}

    public Turn(ChessboardPoint p1, ChessboardPoint p2, PlayerColor color){
        src=p1;
        dest=p2;
        deadChess=null;
        if(color==PlayerColor.BLUE){this.color="b";}
        else {this.color="r";}
    }
    public Turn(ChessboardPoint p1,ChessboardPoint p2,PlayerColor color,ChessPiece chess2){
        src=p1;
        dest=p2;
        if(color==PlayerColor.BLUE){this.color="b";}
        else {this.color="r";}
        deadChess=chess2;
    }

    @Override
    public String toString() {
        if(deadChess!=null){
            return String.format("%s %s %s %s",color,src.toString1(),dest.toString1(),deadChess.getName());
        }else{
            return String.format("%s %s %s null",color,src.toString1(),dest.toString1());
        }
    }
}
