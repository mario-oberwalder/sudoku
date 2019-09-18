package at.codersbay.sudoku.custom.dlx;

import java.awt.List;

// TODO: Auto-generated Javadoc
/**
 * the Class SudokuNotSepoku.
 * 
 * content: sudoku solver with a custom implementation of
 * 			dan knuths dlx.
 * 
 * copyright: 2019 mario oberwalder
 * 
 * contact: mario.oberwalder@gmail.com
 */
public class SudokuNotSeppuku {

	/**
	 * the main method.
	 *
	 * @param args the arguments
	 */

	public final static int SUDOKU_DIMENSION = 9;
	public final static int DIMENSION_SQRD = SUDOKU_DIMENSION*SUDOKU_DIMENSION;
	public final static int SQRT_DIMENSION = (int) Math.sqrt(SUDOKU_DIMENSION);
	public final static int NUM_CONSTRAINTS = 4;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* TODO I.sudoku user I/O 
		 * 	- not implemented 
		 * 	-sudoku hard coded for now
		 * TODO II. validate sudoku 
		 * 	-not implemented
		 * TODO III. generate exact cover matrix/DLL 
		 *  -probably going directly to DoubleLinkedList representation 
		 *  -pre- or post-consideration of already present entries
		 * TODO IV. recursively find solutions
		 * V. profit!
		 * */
		Integer[][] sudokuToSolve = TemplateSudokus.generateSudoku();
		Integer[][] solvedSudoku = TemplateSudokus.generateEmptySudoku();
		HeaderCrown headerCrown = new HeaderCrown();
		DlxSolver dlxSolver = null;
		
	
		/* generate the header structure according to constraints and sudoku dimension */
		headerCrown.generateHeaderCrown();
		headerCrown.generateNodes();
		//headerCrown.print();
		dlxSolver = new DlxSolver(headerCrown, sudokuToSolve);
		
		
		



	}

	



}
