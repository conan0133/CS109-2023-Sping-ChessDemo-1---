package model;


public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        // TODO: Finish this method!
        if(rank==8&&target.getRank()!=1){
            return true;
        }
        if(rank<=7&&rank!=1&&target.getRank()<=rank){
            return true;
        }
        if (rank==1&&(target.getRank()==1||target.getRank()==8)){
            return true;
        } else{
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }
    public int getRank(){//陈嘉祺
        return rank;
    }
}
