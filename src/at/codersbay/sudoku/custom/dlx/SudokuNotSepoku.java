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
public class SudokuNotSepoku {
	
	/**
	 * the main method.
	 *
	 * @param args the arguments
	 */
	
	public final static int SUDOKU_DIMENSION = 9;
	public final static int SQRT_DIMENSION = (int) Math.sqrt(SUDOKU_DIMENSION);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* TODO I.sudoku user I/O 
		 * 	- not implemented 
		 * 	-sudoku hard coded for now
		 * TODO II. validate sudoku 
		 * 	-not implemented
		 * TODO III. generate exact cover matrix/DLL matrix
		 *  -probably going directly to DoubleLinkedList representation 
		 *  -pre- or post-consideration of already present entries
		 * TODO IV. recursively find solutions
		 * V. profit!
		 * */
		int choiceCounter = 0;
		Integer[][] sudokuToSolve = TemplateSudokus.generateSudoku();
		HeaderCrown headerCrown = new HeaderCrown();
		
		/*generate DLL
		 * for each number generate all possible row/column/subsquare
		 * combinations
		 */
		
		//lets make some headers and organize them
		headerCrown = buildCrown();
		
		//lets build lots of nodes one for each possible choice
		for (int i = 1; i <= SUDOKU_DIMENSION; i++) { // for each number
			for (int j = 0; j < SUDOKU_DIMENSION; j++) { //row
				for (int k = 0; k < SUDOKU_DIMENSION; k++) { //column
					
					
				}
				
			}
			
		}
		
	}

	private static HeaderCrown buildCrown() {
		ColumnHeader columnHeader;
		HeaderCrown headerCrown = new HeaderCrown();
		for (int i = 1; i <= SUDOKU_DIMENSION; i++) {
			columnHeader = new ColumnHeader();
			headerCrown.add(columnHeader); //add to list
			columnHeader.introduceToCrown(headerCrown);
			// insert into linked list
			}
		return headerCrown;
	}



}
