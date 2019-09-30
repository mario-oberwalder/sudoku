package at.codersbay.sudoku.custom.dlx;

public class RowHeader extends Node{

	private int sudokuNumber;

	public RowHeader(int j) {
		this.sudokuNumber = j+1;
	}

	public int getSudokuNumber() {
		return sudokuNumber;
	}

	public void setSudokuNumber(int sudokuNumber) {
		this.sudokuNumber = sudokuNumber;
	}
	
}
