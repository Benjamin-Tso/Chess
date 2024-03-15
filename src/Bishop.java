public class Bishop extends Piece{
    public Bishop(int[] pos, boolean white){
        super(pos,white);
    }
    public String  getSymbol(){
        return "♗";
    }

    @Override
    public boolean isLegalMove(int[] targetPos) {
        return Math.abs(targetPos[0]-getPos()[0]) == Math.abs(targetPos[1]-getPos()[1]);
    }
    public boolean isBlocked(){
        return true;
    }
}
