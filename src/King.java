public class King extends Piece {
    private boolean canCastle;
    public King(int[] pos, boolean white){
        super(pos, white);
        canCastle = true;
    }
    @Override
    public void setPos(int[] pos){
        canCastle = false;
        super.setPos(pos);
    }
    public String getSymbol(){
        return "♔";
    }

}
