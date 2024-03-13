public class Queen extends Piece{
    public Queen(int[] pos, boolean white){
        super(pos, white);
    }
    public String getSymbol(){
        return "♕";
    }

    @Override
    public boolean isLegalMove(int[] targetPos, boolean taking) {
        return (Math.abs(targetPos[0]-getPos()[0]) == Math.abs(targetPos[1]-getPos()[1])) || (targetPos[0] == getPos()[0]) == (targetPos[1] != getPos()[1]);
    }
}
