package org.noip.evan1026;

import java.util.ArrayList;

public class Game {

	private Board           _board;
	private ArrayList<Line> _lines   = new ArrayList<Line>();
	private boolean         _player1 = true;

	/**
	 * Initializes the {@link Board} using it's default constructor.
	 */
	public Game(){
		_board = new Board();
	}

	/**
	 * Initializes it with a {@link Board} with a size of the parameter passed.
	 * @param boardSize The size of the {@link Board}
	 */
	public Game(int boardSize){
		_board = new Board(boardSize);
	}
	
	/**
	 * Attempts to remove the pieces and checks for a win.
	 * @param rowIndex The row of the pieces to be removed.
	 * @param startIndex The start index of the pieces to be removed.
	 * @param length The amount of pieces to be removed.
	 * @return True if pieces were removed, false otherwise.
	 */
	public boolean tryRemoveSelection(int rowIndex, int startIndex, int length){
		if (canRemoveSelection(rowIndex, startIndex, length)){
			removeSelection(rowIndex, startIndex, length);
			if (checkForWin()){
				doWin();
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return Boolean representing the current player taking a turn
	 */
	
	public boolean getPlayer(){
		return _player1;
	}
	

	/**
	 * 
	 * @return The whole board, as an {@link ArrayList} of boolean arrays
	 */
	public ArrayList<int[]> getBoard(){
		return _board.getBoard();
	}

	/**
	 * 
	 * @param row Zero based
	 * @return the row, as a boolean[]
	 */
	public int[] getRow(int row){
		return _board.getRow(row);
	}

	/**
	 * 
	 * @param row Zero based
	 * @param index Zero based
	 * @return the piece, as a boolean
	 */
	public int getPiece(int row, int index){
		return _board.getPiece(row, index);
	}

	public void printBoard(){
		System.out.println(_board.toString());
	}
	
	public ArrayList<Line> getLines(){
		return _lines;
	}

	//Make sure length is 1 based
	private boolean canRemoveSelection(int rowIndex, int startIndex, int length){

		boolean canRemove = true;
		for (int i = startIndex; i < startIndex + length; i++){
			if (getPiece(rowIndex, i) != Board.PLAYER_NEUTRAL){
				canRemove = false;
			}
		}

		return canRemove;
	}

	private void removeSelection(int rowIndex, int startIndex, int length){
		for (int i = startIndex; i < startIndex + length; i++){
			_board.setPiece(rowIndex, i, _player1 ? Board.PLAYER_ONE : Board.PLAYER_TWO);
		}
		_lines.add(new Line(rowIndex, startIndex, startIndex + length));
		_player1 = !_player1;
	}

	private boolean checkForWin(){

		boolean win = true;

		for (int[] row : getBoard()){
			for (int i = 0; i < row.length; i++){
				if (row[i] == Board.PLAYER_NEUTRAL){
					win = false;
					break;
				}
			}
		}

		return win;
	}

	private void doWin(){
		if (_player1){
			//player1 lost
			System.out.println("Player 2 wins!");
		}
		else{
			//player2 lost
			System.out.println("Player 1 wins!");
		}
	}

}
