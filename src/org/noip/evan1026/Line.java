package org.noip.evan1026;

public class Line {
	
	private int _row,
				_start,
				_end;
	
	public Line(int row, int start, int end){
		_row   = row;
		_start = start;
		_end   = end;
	}
	
	public int getRow(){
		return _row;
	}
	
	public int getStart(){
		return _start;
	}
	
	public int getEnd(){
		return _end;
	}
}
