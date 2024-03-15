abstract class Piece {
    private int[] pos;
    private boolean white;
    public Piece(int[] pos, boolean white){
        this.white = white;
        this.pos = pos;
    }
    public void setPos(int[] newPos){
        pos = newPos;
    }
    public int[] getPos(){
        return pos;
    }

    public boolean isWhite() {
        return white;
    }

    public abstract String getSymbol();
    public abstract boolean isLegalMove(int[] targetPos);
}
