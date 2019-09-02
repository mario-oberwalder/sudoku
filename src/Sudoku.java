import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * the Class Sudoku.
 * 
 * content:
 * 
 * copyright: 2019 mario oberwalder
 * 
 * contact: mario.oberwalder@gmail.com
 */
public class Sudoku {
	
	/** The sudoku dimension. */
	static int sudokuDimension = 9;
	
	/** The sqrt dimension. */
	static int sqrtDimension = (int) Math.sqrt(sudokuDimension);
	
	/** The sudoku to solve. */
	static Integer[][] sudokuToSolve = generateSudoku(sudokuDimension);
	
	/** The helper sudoku. */
	static Integer[][] helperSudoku = generateEmptySudoku(sudokuDimension);
	
	/** The possible sudoku. */
	static Integer[][] possibleSudoku = generateSudoku(sudokuDimension);

	/**
	 * the main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		printSudoku(sudokuToSolve); // debug print
		/* try to solve the sudoku without guessing to reduce speculation/computing
		* later on recursive backtracking function
		* */
		sudokuToSolve = solveSudokuClean(sudokuToSolve).getSudokuArray(); 
		/* beware here the recursive magic happens */
		//solveSudoku(sudokuToSolve); 

	}// End of Main




	/**
	 * Solve sudoku.
	 *
	 * @param sudokuToSolveParam the sudoku to solve param
	 * @return the boolean
	 */
	private static Boolean solveSudoku(Integer[][] sudokuToSolveParam) {
		Integer[][] sudokuToSolveMethod = sudokuToSolveParam;
		ArrayList<Integer[]> possibleSolutions = new ArrayList<Integer[]>();
		SudokuObjectSolvability sudokuSolution = new SudokuObjectSolvability();
		Integer[][] sudokuSolutionVariant;
		boolean correctPath = false;
		/* run clean and update current sudoku solution
		 * solve and update current sudoku until no progress 
		 */
		sudokuSolution = solveSudokuClean(sudokuToSolveMethod);
		/*save resulting array*/
		sudokuToSolveMethod = sudokuSolution.getSudokuArray();
		/* generate a list from possible solutions
		 */
		possibleSolutions = sudokuSolution.getPossibleEntries();
		/*
		* try a solution
		* either get false if you went down to a unsolvable sudoku
		* or get true if you went down the right path
		*/
		for (Integer[] integers : possibleSolutions) {
			sudokuSolutionVariant = insertIntegers(sudokuToSolveMethod, integers);
			if (solveSudoku(sudokuSolutionVariant)) {
				correctPath = true;
			}
		}
		return correctPath;
	}

	



	/**
	 * Insert integers.
	 *
	 * @param sudokuToSolveParam the sudoku to solve param
	 * @param integersParam the integers param
	 * @return the integer[][]
	 */
	private static Integer[][] insertIntegers(Integer[][] sudokuToSolveParam, Integer[] integersParam) {
		sudokuToSolveParam[integersParam[1]][integersParam[2]]= integersParam[0];
		return null;
	}


	/**
	 * Solve sudoku specc.
	 *
	 * @param sudokuToSolveMethodParam the sudoku to solve method param
	 * @return the boolean
	 */
	private static Boolean solveSudokuSpecc(Integer[][] sudokuToSolveMethodParam) {
		Integer[][] sudokuToSolveSpecc = sudokuToSolveMethodParam;

		return true;

	}

	/**
	 * Solve sudoku clean.
	 *
	 * looks for unambiguous solutions and puts them into sudokuToSolve
	 * should cut down on computation time and might solve some sudokus on its own  
	 * also checks if sudoku is still solvable 
	 *
	 * @param sudokuToSolve the sudoku to solve
	 * @return the integer[][]
	 */
	static SudokuObjectSolvability solveSudokuClean(Integer[][] sudokuToSolve) {
		int numEmptyFields = findEmptyFields(sudokuToSolve);
		int numFoundSolutions = 1;
		ArrayList<Integer[]> possibleSolutions = new ArrayList<Integer[]>();
		SudokuObjectSolvability tempSudokuObject = new SudokuObjectSolvability();
		tempSudokuObject.setSolvable(true);
		/* tempSudoku will get filled with entries and possible entries,
		 * if any field remains empty(value = 0) then the sudoku is not solvable
		 */
		Integer[][] tempSudoku = sudokuToSolve; 
		
		/* run until no more solutions are found in one pass(1..n),
		 * foundSolutions will increase with the return value of
		 * findSolutionUnam 
		 */
		while (numFoundSolutions > 0) {
			numFoundSolutions = 0; 
			for (int i = 1; i <= sudokuDimension; i++) {
				/* 
				 * find all lines where you could put i
				 * put i in each empty space 
				 */
				addEntriesPerLine(i, sudokuDimension);

				/*
				 * if there is already an entry for i in the column remove
				 * illegal i's in the helperSudoku
				 */
				removeEntriesColumn(i, sudokuDimension);

				/*
				 * if there is an illegal i in a subSquare, remove it
				 */
				removeEntriesSubSquare(i,sudokuDimension);
				
				/* add found possibilities to tempSudoku */
				tempSudoku = addSudokuEntries(tempSudoku,helperSudoku);
				
				/* add found possibilities to possibleSolutions Arraylist */
				possibleSolutions = addSudokuPossibilities(i,helperSudoku,possibleSolutions);
				
				/*
				 * scan helperSudoku for qualified solutions 
				 * and put them into place
				 */
				numFoundSolutions += findSolutionUnam(i,sudokuToSolve);

			}
			/*if there are fields without entry that have no possible solution && there are free space in the "solved" sudoku*/
			if (findEmptyFields(tempSudoku) < 1 && findEmptyFields(sudokuToSolve) > 0) {
				tempSudokuObject.setSolvable(false); //set solvable false
			}
		}
		/*return sudokuToSolve object */
		tempSudokuObject.setPossibleEntries(possibleSolutions);
		tempSudokuObject.setSudokuArray(sudokuToSolve);
		return tempSudokuObject;
	}// End of solveSudoku

	/**
	 * Adds the sudoku possibilities.
	 *
	 * @param iParameter the i parameter
	 * @param sudokuToSolveParam the sudoku to solve param
	 * @param possibleSolutionsParam the possible solutions param
	 * @return the array list
	 */
	private static ArrayList<Integer[]> addSudokuPossibilities(int iParameter, Integer[][] sudokuToSolveParam,
			ArrayList<Integer[]> possibleSolutionsParam) {
		for (int i = 0; i < sudokuToSolveParam.length; i++) {
			for (int j = 0; j < sudokuToSolveParam.length; j++) {
				if (sudokuToSolveParam[j][i] != 0) {
					Integer[] e = {iParameter,j,i};
					possibleSolutionsParam.add(e);
				}
				
			}
		}
		return possibleSolutionsParam;
	}


	/**
	 * Adds the entries from helperSudokuParam to tempSudokuParam
	 * and returns the result.
	 *
	 * @param tempSudokuParam the temp sudoku param
	 * @param helperSudokuParam the helper sudoku param
	 * @return the integer[][]
	 */
	private static Integer[][] addSudokuEntries(Integer[][] tempSudokuParam, Integer[][] helperSudokuParam) {
		for (int i = 0; i < tempSudokuParam.length; i++) {
			for (int j = 0; j < tempSudokuParam.length; j++) {
				if (helperSudokuParam[j][i] != 0) {
					tempSudokuParam[j][i]= helperSudokuParam[j][i];
				}
			}
			
		}
		return tempSudokuParam;
	}

	/**
	 * Find solution unam.
	 *
	 * @param numValue the num value
	 * @param sudokuFind the sudoku find
	 * @return the int
	 */
	private static int findSolutionUnam(int numValue,Integer[][] sudokuFind) {
		boolean isUnique = false;
		int counterUnique = 0;
		int iSolvedSomething = 0;
		int subSquareX = 0;
		int subSquareY = 0;
		int offSetX=0;
		int offSetY= 0;
		
		/* 
		 * if i occurs exactly once per line/column combination,
		 * it is valid and should be put into place 
		 * 
		 * also
		 * 
		 * if its the only one potential i in a subSquare its valid too
		 */
		/* find i in helperSudoku */
		for (int i = 0; i < helperSudoku.length; i++) { //iterate row
			for (int j = 0; j < helperSudoku.length; j++) { //iterate column
				 if (helperSudoku[j][i]== numValue) {
					 /* scan for conflict
					  * ?conflict break:continue
					  * continue:
					  * remove i from helperSudoku
					  * put i into sudokuFind
					  */
					 /*scan line*/
					 for (int j2 = 0; j2 < sudokuFind.length; j2++) {
						 if (helperSudoku[j2][i]== numValue) {
							 counterUnique++;
						 }
					 }
					 /* scan column*/
					 for (int j2 = 0; j2 < sudokuFind.length; j2++) {
						if (helperSudoku[j][j2]== numValue) {
							 counterUnique++;
						 }
					}
					/*we expect the counter to be 2:
					 * 1 for finding itself in the line and 1 for 
					 * finding itself in the column
					 */
					if (counterUnique == 2) {
						isUnique = true;
					}
					counterUnique = 0; //reset counterUnique
					
					 /* scan subsquare */
					if (!isUnique) {
						/* what subsquare am i in? */
						subSquareX = (j/sqrtDimension);
						subSquareY = (i/sqrtDimension);
						offSetX =    (subSquareX*sqrtDimension);
						offSetY =	 (subSquareY*sqrtDimension);	
						//scan subsquare */
						for (int j2 = offSetY; j2 < offSetY+sqrtDimension; j2++) { //iterate row
							for (int k = offSetX ; k < offSetX+sqrtDimension ; k++) { //iterate column
								if (helperSudoku[k][j2]== numValue) {
									counterUnique++;
								}
							}	
						}
						if (counterUnique == 1) {
							isUnique = true;
						}
					}
					/* 
					 * if we hit a unique unambiguous solution
					 * we delete the entry from the helperSudoku and 
					 * put it into the sudokuToSolve
					 */
					if (isUnique) {
						sudokuToSolve[j][i] = numValue;
						helperSudoku[j][i] = 0;
						iSolvedSomething++;
					}
					isUnique = false; //reset is Unique
				System.out.println("new entries");	
				printSudoku(sudokuToSolve);
					 
				 }
				
			}
			
		}
		return iSolvedSomething;
	} // End of FindSolutionUnam

	/**
	 * Check line.
	 *
	 * @param i             the i is the current number checked for in the sudoku
	 * @param sudokuRange   the sudoku range
	 */
	private static void addEntriesPerLine(int i, int sudokuRange) {
		boolean isMatched = false;
		boolean isEmpty = false;
		int numValue = i;
		// iterate over lines

		for (int k = 0; k < sudokuToSolve.length; k++) {
			// compare each item in a 'line' with i
			for (int j = 0; j < sudokuToSolve.length; j++) {
				// if any cell contains i -> set isMatched
				if (sudokuToSolve[j][k].intValue() == numValue) {
					isMatched = true;
					break;
				}

			}
			// if the number does not occur in the line fill empty spaces with i in helper
			// table
			if (isMatched == false) {
				for (int l = 0; l < sudokuToSolve.length; l++) {
					// fill empty cells with i
					if (sudokuToSolve[l][k].intValue() == 0) {
						helperSudoku[l][k] = numValue;

					}
			
				}
			}
			isMatched = false; //reset isMatched
		}
	} // End of findEmptyLine
	
	/**
	 * Removes the entries column.
	 *
	 * @param i the i
	 * @param sudokuRange the sudoku range
	 */
	private static void removeEntriesColumn(int i, int sudokuRange) {
		boolean isMatched = false;
		// iterate over columns
		for (int k = 0; k < sudokuRange; k++) {
			// compare each item in a column with i
			for (int j = 0; j < sudokuRange; j++) {
				// if any cell contains i -> set isMatched
				if (sudokuToSolve[k][j].intValue() == i) {
					isMatched = true;
					break;
				}
				
			}
			// if the number does occur in the column then replace all i entries with 0
			if (isMatched) {
				for (int l = 0; l < helperSudoku.length; l++) {
					// fill i cells in helper with 0
					if (helperSudoku[k][l].intValue() == i) {
						helperSudoku[k][l] = 0;
					}

				}

			}
			
			isMatched = false; //reset isMatched

		}
	}  // End of removeEntries
	
	/**
	 * Removes the entries sub square.
	 *
	 * @param i the i
	 * @param sudokuRange the sudoku range
	 */
	private static void removeEntriesSubSquare(int i, int sudokuRange) {
		boolean isMatched = false;
		int numVal = i;
		int[] subSquareOffset= new int[2];
		// iterate over subsquares
		for (int m = 0; m < sudokuRange; m++) {
			subSquareOffset = calculateSubSquareOffset(m,sudokuRange);
			for (int k = 0+ subSquareOffset[0] ; k < subSquareOffset[0]+sqrtDimension; k++) {
				// compare each item in subsquare with i
				for (int j= 0+ subSquareOffset[1]; j < subSquareOffset[1]+sqrtDimension; j++) {
					// if any cell contains i -> set isMatched
					if (sudokuToSolve[k][j].intValue() == numVal) {
						isMatched = true;
					}

				}
			}
			if (isMatched) { //if isMatched delete all occurrences of i within the subsquare in the helperSudoku
				for (int k = 0+ subSquareOffset[0] ; k < subSquareOffset[0]+sqrtDimension; k++) {
					for (int j= 0+ subSquareOffset[1]; j < subSquareOffset[1]+sqrtDimension; j++) {
						if (helperSudoku[k][j].intValue() == numVal) {
							helperSudoku[k][j] = 0; 
						}

					}
				}
			}
			isMatched = false; //reset isMatched

		}
		
	}//end of removeEntriesSubSquare

	/**
	 * Calculate sub square offset.
	 *
	 * @param subSquareNum the sub square num
	 * @param squareDimension the square dimension
	 * @return the int[]
	 */
	private static int[] calculateSubSquareOffset(int subSquareNum, int squareDimension) {
		int[] returnOffset = new int[2];// field 0 is x offset, 1 is y
		int xCounter = 0;
		int yCounter = 0;
		
		for (int i = 0; i < subSquareNum; i++) {
			xCounter++;
			if (xCounter >= sqrtDimension) {
				xCounter = 0; //reset xCounter on line jump
				yCounter++;		
			}
		}
		returnOffset[0] = (int) (xCounter * sqrtDimension);
		returnOffset[1] = (int) (yCounter * sqrtDimension);
		return returnOffset;
	}

	/**
	 * Generate sudoku.
	 *
	 * @param i the i
	 * @return the integer[][]
	 */
	private static Integer[][] generateSudoku(int i) {
		//tempInteger [column][row]
		Integer[][] tempInteger = 
//			{{5,6,0,8,4,7,0,0,0},
//			 {3,0,9,0,0,0,6,0,0},
//			 {0,0,8,0,0,0,0,0,0},
//			 {0,1,0,0,8,0,0,4,0},
//			 {7,9,0,6,0,2,0,1,8},
//			 {0,5,0,0,3,0,0,9,0},
//			 {0,0,0,0,0,0,2,0,0},
//			 {0,0,6,0,0,0,8,0,7},
//			 {0,0,0,3,1,6,0,5,9},
//			 };
			{{0,0,0,0,4,0,0,0,0},
			{3,0,9,0,0,0,6,0,0},
			{0,0,8,0,0,0,0,0,0},
			{0,1,0,0,8,0,0,4,0},
			{7,9,0,6,0,2,0,1,8},
			{0,5,0,0,3,0,0,9,0},
			{0,0,0,0,0,0,2,0,0},
			{0,0,6,0,0,0,8,0,7},
			{0,0,0,3,1,6,0,5,9},
					 };
		return tempInteger;
	}

	/**
	 * Generate empty sudoku.
	 *
	 * @param i the i
	 * @return the integer[][]
	 */
	private static Integer[][] generateEmptySudoku(int i) {
		Integer[][] tempInteger = 
			{{0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0},
			 };
		return tempInteger;
	}

	/**
	 * Find empty fields(contains 0) in an Integer[][] Array.
	 *
	 * @param sudokuToSolve the sudoku to solve
	 * @return the int
	 */
	private static int findEmptyFields(Integer[][] sudokuToSolve) {
		int retNumEmpty = 0;
		for (int i = 0; i < sudokuToSolve.length; i++) {
			for (int j = 0; j < sudokuToSolve.length; j++) {
				if (sudokuToSolve[j][i] == 0) {
					retNumEmpty++;
				}
			}
		}
		return retNumEmpty;
	} //End of findEmptyFields
	
	/**
	 * Prints the sudoku.
	 *
	 * @param sudokuToPrint the sudoku to print
	 */
	private static void printSudoku(Integer[][] sudokuToPrint) {
		for (int j = 0; j < sudokuToPrint.length; j++) {
			for (int j2 = 0; j2 < sudokuToPrint.length; j2++) {
				System.out.print(sudokuToPrint[j2][j]);
			}
			System.out.println();
		}

	} // End of printSudoku()
	
}// End of Class
