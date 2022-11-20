public class Main {
    public static void main(String[] args) {
        Player p1 = new RandomPlayer();
        Player p2 = new SequencePlayer();

        TicTacToeBoard board = new TicTacToeBoard();
        System.out.println(new Game(p1, p2, true).play(board));
    }
}