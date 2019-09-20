package at.codersbay.sudoku.custom.dlx;

import java.awt.FontFormatException;

// TODO: Auto-generated Javadoc
/**
 * the Class DlxSolver.
 * 
 * content:
 * 
 * copyright: 2019 mario oberwalder
 * 
 * contact: mario.oberwalder@gmail.com
 */
public class DlxSolver {
	
	/** The solved sudoku. */
	private Integer[][] solvedSudoku = TemplateSudokus.generateEmptySudoku();
	
	/** The sudoku to solve. */
	private Integer[][] sudokuToSolve = null;
	
	/** The header crown. */
	private HeaderCrown headerCrown = null;
	
	/** The solution array. */
	private Integer[] solutionArray = new Integer[81];


	/**
	 * Instantiates a new dlx solver.
	 *
	 * @param headerCrown the header crown
	 * @param sudokuToSolve the sudoku to solve
	 */
	public DlxSolver(HeaderCrown headerCrown,  Integer[][] sudokuToSolve){
		this.headerCrown = headerCrown;
		this.sudokuToSolve= sudokuToSolve;
	}

	/**
	 * Apply sudoku to DLL.
	 */
	/*parse existing sudoku and select corresponding headers in the headerCrown*/
	void applySudokuToDLL() {
		for (int i = 0; i < SudokuNotSeppuku.SUDOKU_DIMENSION; i++) {
			for (int j = 0; j < SudokuNotSeppuku.SUDOKU_DIMENSION; j++) {
				if (sudokuToSolve[j][i] != 0) {
					selectHeader((i*SudokuNotSeppuku.SUDOKU_DIMENSION)+j, sudokuToSolve[j][i]);
				}
			}	
		}

	}

	/**
	 * Select header.
	 *
	 * @param cellIndex the cell index
	 * @param sudokuNumber the sudoku number
	 */
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

	/**
	 * Removes the line.
	 *
	 * @param selectedNode the selected node
	 */
	private void removeLine(Node selectedNode) {
		Node startNode= selectedNode;
		do {
			selectedNode.getColumnHead().removeFromDLL();
			selectedNode = selectedNode.getNextNode();
		}
		while(startNode != selectedNode);

	}

}
