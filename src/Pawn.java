public class Pawn extends Piece{
    private boolean firstMove;
    public Pawn(int[] pos, boolean white){
        super(pos,white);
        firstMove = false;
    }
    public String getSymbol(){
        return "♙";
    }
    @Override
    public void setPos(int[] newPos){
        firstMove = true;
        super.setPos(newPos);
    }
    public boolean isLegalMove(int[] targetPos, boolean taking){
        if(firstMove){
            return targetPos[0]-getPos()[0]>0 && targetPos[0]-getPos()[0]>=2;
        }
        else if(taking){
            return targetPos[0]-getPos()[0]==1 && Math.abs(targetPos[1]-getPos()[1])==1;
        }
        else{
            return targetPos[0]-getPos()[0]==1;
        }
    }
}
