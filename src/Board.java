import java.util.Arrays;

public class Board {
    private Piece[][] board;
    public Board(){
        board = new Piece[8][8];
        initializeBoard();
    }
    private void initializeBoard(){
        for(int column = 0; column < 8; column++){
            board[1][column] = new Pawn(new int[] {1,column}, false);
            board[6][column] = new Pawn(new int[] {6,column}, true);
        }
        //order: rook, knight, bishop, queen, king, bishop, knight, rook
        board[0][0] = new Rook(new int[] {0, 0}, false);
        board[7][0] = new Rook(new int[] {7, 0}, true);
        board[0][1] = new Knight(new int[] {0,1},false);
        board[7][1] = new Knight(new int[] {7,1},true);
        board[0][2] = new Bishop(new int[]{0,2},false);
        board[7][2] = new Bishop(new int[]{0,2},true);
        board[0][3] = new Queen(new int[] {0,3},false);
        board[7][3] = new Queen(new int[] {7,3},true);
        board[0][4] = new King(new int[] {0,4},false);
        board[7][4] = new King(new int[] {7,4}, true);
        board[0][5] = new Bishop(new int[]{0,5},false);
        board[7][5] = new Bishop(new int[]{7,5},true);
        board[0][6] = new Knight(new int[] {0,6},false);
        board[7][6] = new Knight(new int[] {7,6},true);
        board[0][7] = new Rook(new int[] {0, 7}, false);
        board[7][7] = new Rook(new int[] {7, 7}, true);
    }
    @Override
    public String toString(){
        final String BG1 = "\u001B[47m"; //light
        final String C1 = "\u001B[37m"; // ^ text
        final String BG2 = "\u001B[45;1m";//dark
        final String C2 = "\u001B[35m";// ^ text
        final String RESET = "\u001B[0m";
        String out = "";
        boolean bgc = false;
        for(Piece[] row : board){
            for(Piece p : row){
                if(p!=null){
                    out+= (p.isWhite() ? "" : "\u001B[30m") + (bgc? BG1 : BG2) + p.getSymbol();
                }
                else{
                    out+=(bgc? BG1 + C1 : BG2 + C2)+"♙";
                }
                bgc = !bgc;
            }
            out+=RESET + "\n";
            bgc = !bgc;
        }
        return out;
    }
    public void movePiece(String start, String move){
        int[] startPos = parseMove(start);
        int[] movePos = parseMove(move);
        if(movePos==null || startPos==null){
            System.out.println("invalid move");
            return;
        }
        boolean taking = board[movePos[0]][movePos[1]]!=null && board[movePos[0]][movePos[1]].isWhite()!= board[startPos[0]][startPos[1]].isWhite();
        if(board[startPos[0]][startPos[1]].isLegalMove(movePos,taking)){
            board[movePos[0]][movePos[1]] = board[startPos[0]][startPos[1]];
            board[startPos[0]][startPos[1]] = null;
            board[startPos[0]][startPos[1]].setPos(movePos);
        }
    }
    public int[] parseMove(String move){ //turns move like G5 into coordinates {4,6}
        String columns = "abcdefgh";
        int row;
        try{
            row = Integer.parseInt(String.valueOf(move.charAt(1)));
            if(row>8){
                return null;
            }
        }catch(NumberFormatException ignored){
            return null;
        }
        String column = String.valueOf(move.charAt(0)).toLowerCase();
        if(!columns.contains(column)) {
            return null;
        }
        return new int[] {row-1, columns.indexOf(column)};
    }
}
