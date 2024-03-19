public class Pawn extends Piece{
    private boolean firstMove;
    private final int promoteRow;
    public Pawn(int[] pos, boolean white){
        super(pos,white);
        firstMove = false;
        promoteRow = isWhite()? 0:7;
    }
    public String getSymbol(){
        return "♙";
    }
    @Override
    public void setPos(int[] newPos){
        firstMove = true;
        super.setPos(newPos);
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public int getPromoteRow() {
        return promoteRow;
    }

    public boolean isLegalMove(int[] targetPos){
        boolean taking = targetPos[2]==1;
        if(firstMove){
            return targetPos[0]-getPos()[0]>0 && targetPos[0]-getPos()[0]<=2;
        }
        else if(taking){
            return targetPos[0]-getPos()[0]==1 && Math.abs(targetPos[1]-getPos()[1])==1;
        }
        else{
            return targetPos[0]-getPos()[0]==1;
        }
    }
}
