import java.util.Scanner;
//chess
public class Main {
    public static void main(String[] args) {
        Board b = new Board();
        Scanner s = new Scanner(System.in);
        Boolean active;
        do{
            System.out.println(b);
            System.out.println((b.isWhitePlaying()? "white" : "black") + "'s move, pick a piece to move based on its position in the format of column row (A1 - H8)");
            String start = s.nextLine().toLowerCase();
            System.out.println("pick a position to move to");
            String end = s.nextLine().toLowerCase();
            active = b.movePiece(start, end);
        }while(active!=null);
        if(b.isCheckmate()==null){
            System.out.println("stalemate");
        }
        else{
            System.out.println((b.isCheckmate()? "white" : "black") + " wins!");
        }
    }

}