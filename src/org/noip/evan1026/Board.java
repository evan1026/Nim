package org.noip.evan1026;

import java.util.ArrayList;

public class Board {
	
	private ArrayList<int[]> _pieces;
	
	public static int PLAYER_NEUTRAL = 0;
	public static int PLAYER_ONE     = 1;
	public static int PLAYER_TWO     = 2;
	
	/**
	 * Calls the other constructor, sending 5 as the size.
	 */
	public Board(){
		this(5);
	}
	
	/**
	 * Sets up the {@link ArrayList} that represents the board. The indices of the {@link ArrayList} start at zero and go top to bottom.
	 * It is and {@link ArrayList} of boolean arrays, and the size of each array starts at one and increases by 1 for each subsequent row.
	 * @param size The amount of rows to have
	 */
	public Board(int size){
		_pieces = new ArrayList<int[]>();
		
		for (int i = 0; i < size; i++){
			_pieces.add(i, new int[i + 1]);
			
			for (int j = 0; j < _pieces.get(i).length; j++){
				_pieces.get(i)[j] = PLAYER_NEUTRAL;
			}
		}
	}
	
	/**
	 * 
	 * @return The whole board, as an {@link ArrayList} of boolean arrays
	 */
	public ArrayList<int[]> getBoard(){
		return _pieces;
	}
	
	protected void setBoard(ArrayList<int[]> value){
		_pieces = value;
	}
	
	/**
	 * 
	 * @param row Zero based
	 * @return the row, as a boolean[]
	 */
	public int[] getRow(int row){
		
		if (row >= _pieces.size()){
			throw new IndexOutOfBoundsException(row + " is greater than the ArrayList's max index of " + (_pieces.size() - 1));
		}
		
		return _pieces.get(row);
	}
	
	protected void setRow(int row, int[] value){
		if (row >= _pieces.size()){
			throw new IndexOutOfBoundsException(row + " is greater than the ArrayList's max index of " + (_pieces.size() - 1));
		}
		
		_pieces.set(row, value);
	}
	
	/**
	 * 
	 * @param row Zero based
	 * @param index Zero based
	 * @return the piece, as a boolean
	 */
	
	public int getPiece(int row, int index){
		
		if (row >= _pieces.size()){
			throw new IndexOutOfBoundsException(row + " is greater than the ArrayList's max index of " + (_pieces.size() - 1));
		}
		
		if (index >= _pieces.get(row).length){
			throw new IndexOutOfBoundsException(index + " is greater than the array's max index of " + (_pieces.get(row).length - 1));
		}
		
		return _pieces.get(row)[index];
	}
	
	protected void setPiece(int row, int index, int value){
		if (row >= _pieces.size()){
			throw new IndexOutOfBoundsException(row + " is greater than the ArrayList's max index of " + (_pieces.size() - 1));
		}
		
		if (index >= _pieces.get(row).length){
			throw new IndexOutOfBoundsException(index + " is greater than the array's max index of " + (_pieces.get(row).length - 1));
		}
		
		_pieces.get(row)[index] = value;
	}
	
	@Override
	public String toString(){
		String output = "";
		int maxLength = _pieces.get(_pieces.size() - 1).length * 2;
		
		for (int[] row : _pieces){
			int spaces = maxLength - row.length * 2;
			
			for (int i = 0; i < spaces / 2; i++){
				output += " ";
			}
			
			for (int piece : row){
				output += piece + " ";
			}
			
			output += "\n";
		}
		
		return output;
	}
}
