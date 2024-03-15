public class Rook extends Piece{
    public Rook(int[] pos,boolean white){
        super(pos, white);
    }
    public String getSymbol(){
        return "♖";
    }

    @Override
    public boolean isLegalMove(int[] targetPos) {
        return (targetPos[0] == getPos()[0]) == (targetPos[1] != getPos()[1]);
    }
    public boolean isBlocked(){
        return true;
    }
}
