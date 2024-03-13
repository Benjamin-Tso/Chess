public class Knight extends Piece{
    public Knight(int[] pos, boolean white){
        super(pos, white);
    }
    public String getSymbol(){
        return "♘";
    }

    @Override
    public boolean isLegalMove(int[] targetPos, boolean taking) {
        return Math.abs(targetPos[0]-getPos()[0])==2 && Math.abs(targetPos[1]-getPos()[1])==1 || Math.abs(targetPos[0]-getPos()[0])==1 && Math.abs(targetPos[1]-getPos()[1])==2;
    }
}
