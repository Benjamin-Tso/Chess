import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private Piece[][] board;
    private Boolean checkmate;//null = stalemate, false = black win, true = white win;
    private boolean whitePlaying; //false - black, true - white
    private ArrayList<Piece> blackPieces, whitePieces;
    King bKing, wKing;
    public Board(){
        board = new Piece[8][8];
        whitePlaying = true;
        blackPieces = new ArrayList<>();
        whitePieces = new ArrayList<>();
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
        bKing = (King) board[0][4];
        board[7][4] = new King(new int[] {7,4}, true);
        wKing = (King) board[7][4];
        board[0][5] = new Bishop(new int[]{0,5},false);
        board[7][5] = new Bishop(new int[]{7,5},true);
        board[0][6] = new Knight(new int[] {0,6},false);
        board[7][6] = new Knight(new int[] {7,6},true);
        board[0][7] = new Rook(new int[] {0, 7}, false);
        board[7][7] = new Rook(new int[] {7, 7}, true);
        for(Piece[] row : board){
            for(Piece p : row) {
                if (p != null){
                    (p.isWhite()? whitePieces:blackPieces).add(p);
                }
            }
        }
    }
    public Boolean isCheckmate() {
        return checkmate;
    }

    public boolean isWhitePlaying() {
        return whitePlaying;
    }

    @Override
    public String toString(){
        final String BG1 = "\u001B[47m"; //light
        final String C1 = "\u001B[37m"; // ^ text
        final String BG2 = "\u001B[45;1m";//dark
        final String C2 = "\u001B[35m";// ^ text
        final String RESET = "\u001B[0m";
        String out = "  ";
        boolean bgc = false;
        for(char c = 'A';c<='H';c++){
            out+=c+" ";
        }
        out+="\n";
        int count = 8;
        for(Piece[] row : board){
            out+=count+" ";
            count--;
            for(Piece p : row){
                if(p!=null){
                    out+= (p.isWhite() ? "" : "\u001B[30m") + (bgc? BG1 : BG2) + p.getSymbol()+RESET;
                }
                else{
                    out+=(bgc? BG1 + C1 : BG2 + C2)+"♙" + RESET;
                }
                bgc = !bgc;
            }
            out+=RESET + "\n";
            bgc = !bgc;
        }
        return out;
    }
    public Boolean movePiece(String start, String move){//null stalemate/checkmate has been reached, false failed to move piece, true succeeded in moving piece
        int[] startPos = parseMove(start);
        int[] movePos = parseMove(move);
        if(movePos==null || startPos==null){
            System.out.println("invalid move");
            return false;
        }
        if(board[startPos[0]][startPos[1]]==null||board[startPos[0]][startPos[1]].isWhite()!= whitePlaying){
            System.out.println("invalid piece");
            return false;
        }
        //check movePos for possible checks, if all possible moves are check, check all other friendly pieces to see if they can block, if none can block, set checkmate to !isWhite of the moving King
        int attackedPositions = 0;
        for(int i = -1; i<=1; i++){
            for(int j = -1; j<=1; j++){
                if(startPos[0]+i<0 || startPos[1]+j<0 || startPos[0]+i>7 || startPos[1]+j>7){
                    attackedPositions++;
                }
                else if(beingAttacked(whitePlaying? wKing : bKing, new int[]{startPos[0]+i, startPos[1]+j}).size()>0 && 2*i+j!=0){
                    int temp1 = beingAttacked(whitePlaying? wKing : bKing, new int[]{startPos[0]+i, startPos[1]+j}).size();
                    int temp2 = 0;
                    for(Piece p : beingAttacked(whitePlaying? wKing : bKing, new int[]{startPos[0]+i, startPos[1]+j})){
                        if(canBeBlocked(p,new int[]{startPos[0]+i, startPos[1]+j})){
                            temp2++;
                        }
                    }
                    if(temp1!=temp2){
                        attackedPositions++;
                    }
                }
            }
        }
        if(beingAttacked(whitePlaying? wKing : bKing, new int[]{startPos[0], startPos[1]}).size()>0&&attackedPositions==8){
            attackedPositions++;
        }

        switch (attackedPositions) {
            case 8 -> {
                return null;
            }
            case 9 -> {
                checkmate = !whitePlaying;
                return null;
            }
        }
        if(board[startPos[0]][startPos[1]] instanceof Pawn){
            movePos = new int[] {movePos[0],movePos[1], (board[movePos[0]][movePos[1]]!=null && board[movePos[0]][movePos[1]].isWhite()!= board[startPos[0]][startPos[1]].isWhite())? 0:1};//index 2 specifically for pawn, because taking has special movement rule, 1 for taking, 0 for not
        }
        if(board[startPos[0]][startPos[1]].isLegalMove(movePos)){
            if(isBlocked(board[startPos[0]][startPos[1]], movePos)){
                System.out.println("there is a piece in the way");
                return false;
            }
            if(board[movePos[0]][movePos[1]]!=null){
                (board[movePos[0]][movePos[1]].isWhite()?whitePieces:blackPieces).remove(board[movePos[0]][movePos[1]]);
            }
            board[movePos[0]][movePos[1]] = board[startPos[0]][startPos[1]];
            board[startPos[0]][startPos[1]] = null;
            board[movePos[0]][movePos[1]].setPos(movePos);
            if(beingAttacked(whitePlaying? wKing : bKing,whitePlaying? wKing.getPos() : bKing.getPos()).size()>0){//check if in check after moving, if so undo move
                board[startPos[0]][startPos[1]] = board[movePos[0]][movePos[1]];
                board[movePos[0]][movePos[1]] = null;
                board[startPos[0]][startPos[1]].setPos(movePos);
                System.out.println("your king is in check");
                return false;
            }
        }
        else {
            System.out.println("illegal move");
            return false;
        }
        King targetKing = board[movePos[0]][movePos[1]].isWhite()? bKing: wKing;
        int[] targetPos = targetKing.getPos();
        if(board[movePos[0]][movePos[1]] instanceof Pawn){
            targetPos = new int[] {targetPos[0],targetPos[1], 1};
        }
        if(board[movePos[0]][movePos[1]].isLegalMove(targetPos) && !isBlocked(board[movePos[0]][movePos[1]], targetPos)){//put enemy king in check
            targetKing.setCheck(true);
        }
        whitePlaying = !whitePlaying;
        return true;
    }

    public int[] parseMove(String move){ //turns move like G5 into coordinates {4,6}
        if(move.length()!=2){
            return null;
        }
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
        return new int[] {8-row, columns.indexOf(column)};
    }
    public boolean isBlocked(Piece p, int[] move){//called after legal move check, so move is known to be legal
        if(p instanceof Knight || p instanceof King){//can't be blocked
            return board[move[0]][move[1]]!=null && board[move[0]][move[1]].isWhite() == p.isWhite() ;
        }
        else if(p instanceof Pawn){//blocked if piece is in front
            boolean test1 = board[move[0]][move[1]]!=null && move[1]==p.getPos()[1]; //piece at target and not taking
            boolean test2 = false;
            if(((Pawn) p).isFirstMove()){
                if(p.isWhite()) {
                    test2 = board[p.getPos()[0] - 1][p.getPos()[1]] != null; //piece in front
                }
                else{
                    test2 = board[p.getPos()[0] + 1][p.getPos()[1]] != null;
                }
            }
            return test1 || test2;
        }
        else if(p instanceof Bishop || (p instanceof Queen && (p.getPos()[0]!=move[0]&&p.getPos()[1]!=move[1]) )){ // bishop or queen w/ bishop movement
            for(int i = 1; i!=Math.abs(p.getPos()[0]-move[0]); i+=1){ // diagonal towards move
                if(board[p.getPos()[0]+((p.getPos()[0]-move[0]<0)?i:-i)][p.getPos()[1]+((p.getPos()[1]-move[1]<0)?i:-i)]!=null && !Arrays.equals(new int[]{p.getPos()[0] + i, p.getPos()[1] + i}, move)){
                    return true;
                }
            }
        }
        else if(p instanceof Rook ||(p instanceof Queen && ((p.getPos()[0]==move[0]) != (p.getPos()[1]==move[1]))) ){ // rook or queen w/ rook movement
            if(p.getPos()[0]==move[0]){//horizontal towards move
                for(int i = 0; i!=Math.abs(move[1]-p.getPos()[1]); i+= (move[1]-p.getPos()[1])/Math.abs(move[1]-p.getPos()[1])){
                    if(board[p.getPos()[0]][p.getPos()[1]+i]!=null && !Arrays.equals(new int[]{p.getPos()[0],p.getPos()[1]+i},move)){
                        return true;
                    }
                }
            }
            if(p.getPos()[1]==move[1]){//vertical towards move
                for(int i = 0; i!=Math.abs(move[0]-p.getPos()[0]); i+= (move[0]-p.getPos()[0])/Math.abs(move[0]-p.getPos()[0])){
                    if(i==0){
                        i+=move[0]-p.getPos()[0]/Math.abs(move[0]-p.getPos()[0]);
                    }
                    if(board[p.getPos()[0]+i][p.getPos()[1]]!=null && board[p.getPos()[0]+i][p.getPos()[1]].isWhite()!=p.isWhite()){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean canBeBlocked(Piece p, int[] move){
        ArrayList<Piece> opponentPieces = p.isWhite()? blackPieces : whitePieces ;
        if(p instanceof Bishop || (p instanceof Queen && (p.getPos()[0]!=move[0]&&p.getPos()[1]!=move[1]) )){ // bishop or queen w/ bishop movement
            for(int i = 1; i!=Math.abs(p.getPos()[0]-move[0]); i+=1){ // diagonal towards move
                for(Piece oP : opponentPieces){
                    int[] target = (oP instanceof Pawn)? new int[]{p.getPos()[0]+((p.getPos()[0]-move[0]<0)?i:-i),p.getPos()[1]+((p.getPos()[1]-move[1]<0)?i:-i),1} : new int[]{p.getPos()[0]+((p.getPos()[0]-move[0]<0)?i:-i),p.getPos()[1]+((p.getPos()[1]-move[1]<0)?i:-i)};
                    if(oP.isLegalMove(target) && !isBlocked(oP, target)){
                        return true;
                    }
                }
            }
        }
        else if(p instanceof Rook ||(p instanceof Queen && ((p.getPos()[0]==move[0]) != (p.getPos()[1]==move[1]))) ){ // rook or queen w/ rook movement
            if(p.getPos()[0]==move[0]){//horizontal towards move
                for(int i = 0; i!=Math.abs(move[1]-p.getPos()[1]); i+= (move[1]-p.getPos()[1])/Math.abs(move[1]-p.getPos()[1])){
                    for(Piece oP : opponentPieces){
                        int[] target = (oP instanceof Pawn)? new int[]{p.getPos()[0],p.getPos()[1]+i,1}:new int[]{p.getPos()[0],p.getPos()[1]+i};
                        if(oP.isLegalMove(target) && !isBlocked(oP,target)){
                            return true;
                        }
                    }
                }
            }
            if(p.getPos()[1]==move[1]){//vertical towards move
                for(int i = 0; i!=Math.abs(move[0]-p.getPos()[0]); i+= (move[0]-p.getPos()[0])/Math.abs(move[0]-p.getPos()[0])){
                    for(Piece oP : opponentPieces){
                        int[] target = (oP instanceof Pawn)? new int[]{p.getPos()[0]+i,p.getPos()[1],1}:new int[]{p.getPos()[0]+i,p.getPos()[1]};
                        if(oP.isLegalMove(new int[] {p.getPos()[0]+i,p.getPos()[1]}) &&  !isBlocked(oP,new int[]{p.getPos()[0]+i,p.getPos()[1]} )){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public ArrayList<Piece> beingAttacked(King k, int[] pos){
        ArrayList<Piece> attackers = new ArrayList<>();
            for(Piece p : k.isWhite()?blackPieces:whitePieces){
                if(p.getPos()!=pos) {
                    if (p instanceof Pawn) {
                        if (p.isLegalMove(new int[]{k.getPos()[0], k.getPos()[1], 1}) && !isBlocked(p, k.getPos())) {
                            attackers.add(p);
                        }
                    } else if (p.isLegalMove(k.getPos()) && !isBlocked(p, k.getPos())) {
                        attackers.add(p);
                    }
                }
            }
        return attackers;
    }
}
