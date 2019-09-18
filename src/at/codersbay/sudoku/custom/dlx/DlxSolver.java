package at.codersbay.sudoku.custom.dlx;

import java.awt.FontFormatException;

public class DlxSolver {
	private Integer[][] solvedSudoku = TemplateSudokus.generateEmptySudoku();
	private Integer[][] sudokuToSolve = null;
	private HeaderCrown headerCrown = null;
	
	
	public DlxSolver(HeaderCrown headerCrown,  Integer[][] sudokuToSolve){
	this.headerCrown = headerCrown;
	this.sudokuToSolve= sudokuToSolve;
	}
	
	void iterateOverSudoku() {
		
		
	}
	
}
