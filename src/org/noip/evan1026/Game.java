package org.noip.evan1026;

import java.util.ArrayList;

public class Game {

	private Board _board;
	private boolean player1 = true;

	public Game(){
		_board = new Board();
	}

	public boolean tryRemoveSelection(int rowIndex, int startIndex, int length){
		if (canRemoveSelection(rowIndex, startIndex, length)){
			removeSelection(rowIndex, startIndex, length);
			checkForWin();
			return true;
		}
		return false;
	}

	public Game(int boardSize){
		_board = new Board(boardSize);
	}

	/**
	 * 
	 * @return The whole board, as an {@link ArrayList} of boolean arrays
	 */
	public ArrayList<boolean[]> getBoard(){
		return _board.getBoard();
	}

	/**
	 * 
	 * @param row Zero based
	 * @return the row, as a boolean[]
	 */
	public boolean[] getRow(int row){
		return _board.getRow(row);
	}

	/**
	 * 
	 * @param row Zero based
	 * @param index Zero based
	 * @return the piece, as a boolean
	 */
	public boolean getPiece(int row, int index){
		return _board.getPiece(row, index);
	}

	//Make sure length is 1 based
	private boolean canRemoveSelection(int rowIndex, int startIndex, int length){

		boolean canRemove = true;
		for (int i = startIndex; i < startIndex + length; i++){
			if (!getPiece(rowIndex, i)){
				canRemove = false;
			}
		}

		return canRemove;
	}
	
	private void removeSelection(int rowIndex, int startIndex, int length){
		for (int i = startIndex; i < startIndex + length; i++){
			_board.setPiece(rowIndex, i, false);
		}
	}
	
	public void printBoard(){
		System.out.println(_board.toString());
	}
}
