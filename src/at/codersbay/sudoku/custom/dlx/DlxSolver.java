package at.codersbay.sudoku.custom.dlx;

import java.awt.FontFormatException;

public class DlxSolver {
	private Integer[][] solvedSudoku = TemplateSudokus.generateEmptySudoku();
	private Integer[][] sudokuToSolve = null;
	private HeaderCrown headerCrown = null;
	private Integer[] solutionArray = new Integer[81];


	public DlxSolver(HeaderCrown headerCrown,  Integer[][] sudokuToSolve){
		this.headerCrown = headerCrown;
		this.sudokuToSolve= sudokuToSolve;
	}

	/*parse existing sudoku and select corresponding headers in the headerCrown*/
	void iterateOverSudoku() {
		for (int i = 0; i < SudokuNotSeppuku.SUDOKU_DIMENSION; i++) {
			for (int j = 0; j < SudokuNotSeppuku.SUDOKU_DIMENSION; j++) {
				if (sudokuToSolve[j][i] != 0) {
					selectHeader((i*SudokuNotSeppuku.SUDOKU_DIMENSION)+j, sudokuToSolve[j][i]);
				}
			}	
		}

	}

	private void selectHeader(int cellIndex, int sudokuNumber) {
		ColumnHeader selectedHeader = null;
		Node selectedNode = null;
		Node startNode = null;
		Node verticalStartNode = null;

		selectedHeader = headerCrown.listOfColumnHeaders.get(cellIndex);
		for (int i = 0; i < sudokuNumber; i++) {
			selectedNode = selectedHeader.getDownNode();
			startNode=selectedNode;
		}	
		solutionArray[cellIndex] = (cellIndex % SudokuNotSeppuku.SUDOKU_DIMENSION); //add cellheader to solution array
		
		/* remove headers from the selected choice*/
		do {
			cellIndex = selectedNode.getColumnHead().getIndexOf(headerCrown);
			selectedNode.getColumnHead().removeFromDLL();
			selectedNode = selectedNode.getNextNode();
		}
		while(startNode != selectedNode);

		/*for each node in the selected line, go to each downward node and delete all headers for that line of nodes */
		do {
			selectedNode = selectedNode.getNextNode();
			verticalStartNode = selectedNode;
			do {
				
				selectedNode.getColumnHead().removeFromDLL();
				selectedNode = selectedNode.getDownNode();
				removeLine(selectedNode);	

			}
			while(verticalStartNode != selectedNode);
		}
		while(startNode != selectedNode);

	}

	private void removeLine(Node selectedNode) {
		Node startNode= selectedNode;
		do {
			selectedNode.getColumnHead().removeFromDLL();
			selectedNode = selectedNode.getNextNode();
		}
		while(startNode != selectedNode);

	}

}
