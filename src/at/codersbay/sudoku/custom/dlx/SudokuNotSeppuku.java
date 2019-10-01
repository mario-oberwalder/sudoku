package at.codersbay.sudoku.custom.dlx;

import java.awt.List;

// TODO: Auto-generated Javadoc
/**
 * the Class SudokuNotSepoku.
 * 
 * content: sudoku solver with a custom implementation of
 * 			don knuths dlx.
 *			
 * 
 * copyright: 2019 mario oberwalder
 * 
 * contact: mario.oberwalder@gmail.com
 */
public class SudokuNotSeppuku {

	public final static int SUDOKU_DIMENSION = 9;
	public final static int DIMENSION_SQRD = SUDOKU_DIMENSION*SUDOKU_DIMENSION;
	public final static int SQRT_DIMENSION = (int) Math.sqrt(SUDOKU_DIMENSION);
	public final static int NUM_CONSTRAINTS = 4;

	/**
	 * the main method.
	 *
	 *we generate a Double linked list(DLL) with rows and columns the 
	 *first and last element are connected so it resembles a torus
	 *
	 *each column has a columnHead and each row has a rowHead
	 *the columnHeads are organized in a header'Crown'
	 *
	 *to solve the sudoku a exact cover matrix is generated which is 
	 *represented by the DLL, then a lot of liked list switching occurs
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		/* TODO I.sudoku user I/O 
		 * 	- not implemented 
		 * 	-sudoku hard coded for now
		 * TODO II. validate sudoku 
		 * 	-not implemented
		 * III. generate exact cover matrix/DLL 
		 *  -probably going directly to DoubleLinkedList representation 
		 *  -pre-consideration of already present entries
		 * TODO IV. recursively find solutions
		 * V. profit!
		 * */
		Integer[][] sudokuToSolve = TemplateSudokus.generateSudoku();
		Integer[][] solvedSudoku = TemplateSudokus.generateEmptySudoku();
		HeaderCrown headerCrown = new HeaderCrown();
		DlxSolver dlxSolver = null;
		
	
		/* generate the header structure according to constraints and sudoku dimension */
		headerCrown.generateHeaderCrown(); //generate ring of ColumnHeaders
		headerCrown.generateNodes();// generate Nodes
		headerCrown.generateSize(); //calculate size of each ColumnHeader
		dlxSolver = new DlxSolver(headerCrown, sudokuToSolve);
		dlxSolver.applySudokuToDLL(); 
		headerCrown.print(); //debug print the header structure
		dlxSolver.solve(); //call sudoku solver, we already passed headerCrown and sudokuToSolve previously
		
		



	}

	



}
