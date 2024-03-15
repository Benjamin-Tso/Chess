public class King extends Piece {
    private boolean check;
    public boolean canCastle;
    public King(int[] pos, boolean white){
        super(pos, white);
        check = false;
        canCastle = true;
    }
    public String getSymbol(){
        return "♔";
    }

    @Override
    public boolean isLegalMove(int[] targetPos) {
        return Math.abs(targetPos[0]-getPos()[0])==1 || Math.abs(targetPos[1]-getPos()[1])==1;
    }
    public void moved(){
        canCastle = false;
    }
    public boolean canCastle(){
        return canCastle;
    }
    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
