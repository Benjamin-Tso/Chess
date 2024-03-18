public class Rook extends Piece{
    private boolean canCastle;
    public Rook(int[] pos,boolean white){
        super(pos, white);
    }
    public String getSymbol(){
        return "♖";
    }

    @Override
    public boolean isLegalMove(int[] targetPos) {
        return (targetPos[0] == getPos()[0]) != (targetPos[1] == getPos()[1]);
    }
    public void moved(){
        canCastle = false;
    }
    public boolean canCastle(){
        return canCastle;
    }
    public boolean isBlocked(){
        return true;
    }
}
