import org.noip.evan1026.Game;


public class Nim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game = new Game(10);
		game.printBoard();
		for (int i = 0; i < 10; i++){
		    game.tryRemoveSelection(i, 0, i + 1);
		    game.printBoard();
		}
		
		
	}

}
