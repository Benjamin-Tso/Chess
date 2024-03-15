public class King extends Piece {
    private boolean check;
    public King(int[] pos, boolean white){
        super(pos, white);
        check = false;
    }
    public String getSymbol(){
        return "♔";
    }

    @Override
    public boolean isLegalMove(int[] targetPos) {
        return Math.abs(targetPos[0]-getPos()[0])==1 || Math.abs(targetPos[1]-getPos()[1])==1;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
