public class Pawn extends Piece{
    private boolean firstMove;
    public Pawn(int[] pos, boolean white){
        super(pos,white);
        firstMove = false;
    }
    public String getSymbol(){
        return "♙";
    }
}
