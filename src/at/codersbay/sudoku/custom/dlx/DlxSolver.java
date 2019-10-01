package at.codersbay.sudoku.custom.dlx;

import java.awt.FontFormatException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

	private ArrayList<Integer[]> arrayOfSolutions = new ArrayList<>();

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
	 * I)for the header at cellindex, select(add it to solutionArray),
	 * and remove it from the header DLL
	 * II)sudokuNumber tells you which row was selected for all nodes
	 * in that row select their header.
	 * III)for all nodes in a column with a selected header, 
	 * find all headers that have a node in the same row and remove 
	 * them. 
	 *
	 * @param cellIndex the cell index (0..80)
	 * @param sudokuNumber the sudoku number (1..9)
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

		/*put element into solution */
		solutionArray[cellIndex] = startNode.getRowHead().getSudokuNumber();	 

		/* remove headers from the selected choice*/
		do {
			cellIndex = selectedNode.getColumnHead().getIndexOf(headerCrown);
			/*remove from DLL*/
			selectedNode.getColumnHead().removeFromDLL();
			/*remove from unselectedHeaderList */
			this.headerCrown.unselectedHeaders.remove(selectedNode.getColumnHead());

			selectedNode = selectedNode.getNextNode();
		}
		while(startNode != selectedNode);

		/*for each node in the selected line, go to each downward node and delete all headers for that line of nodes */
		do {
			selectedNode = selectedNode.getNextNode();
			verticalStartNode = selectedNode;
			do {

				selectedNode.getColumnHead().removeFromDLL();
				this.headerCrown.unselectedHeaders.remove(selectedNode.getColumnHead());
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
			this.headerCrown.unselectedHeaders.remove(selectedNode.getColumnHead());
			selectedNode = selectedNode.getNextNode();
		}
		while(startNode != selectedNode);

	}

	public void solve(HeaderCrown headerCrown2, Integer[][] sudokuToSolve2) {
		this.headerCrown = headerCrown2;
		this.sudokuToSolve = sudokuToSolve2;
		this.solve();

	}

	private boolean isSolved() {
		/* when the array is full and there are no headers left the sudoku is solved */
		boolean isArrayFull =  true;
		boolean noMoreUnselectedHeaders = false;

		for (int i = 0; i < solutionArray.length; i++) {
			if (solutionArray[i] == 0) {
				isArrayFull = false;
			}
		}

		noMoreUnselectedHeaders = this.headerCrown.unselectedHeaders.isEmpty();

		if(isArrayFull && noMoreUnselectedHeaders) 
		{
			return true;
		}

		return false;
	};

	public void solve() {
		/*sort list of unselected headers*/
		this.headerCrown.unselectedHeaders.sort(Comparator.comparing(ColumnHeader::getSize));
		System.out.println("bla");
	}

		
	
	
	
} // end of class


