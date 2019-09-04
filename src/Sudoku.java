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
	static final int SUDOKU_DIMENSION = 9;
	/** The sqrt dimension. */
	static final int SQRT_DIMENSION = (int) Math.sqrt(SUDOKU_DIMENSION);

	/**
	 * the main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Integer[][] sudokuToSolve = generateSudoku(SUDOKU_DIMENSION);

		printSudoku(sudokuToSolve);	
		if(solveSudoku(sudokuToSolve).isSolved()) {
			System.out.println("Solved!");
		}

	}// End of Main

	/**
	 * Solve sudoku, by calling itself.
	 *
	 * @param sudokuToSolveParam the sudoku to solve param
	 * @return the boolean
	 */
	private static SudokuObjectSolvability solveSudoku(Integer[][] sudokuToSolveParam) {
		Integer[][] sudokuToSolve = copySudokuArray(sudokuToSolveParam); 
		Integer[][] sudokuRandomEntry; //this will be given to called functions so we dont lose the "original" sudoku
		ArrayList<Integer[]> possibleEntries = new ArrayList<Integer[]>(); //a list with possible solutions to iterate through and call recursively
		SudokuObjectSolvability sudokuSolutionObject = new SudokuObjectSolvability();// this will be the current return object from solveSudokuclean to store all the interesting values
		boolean correctPath = false; 
		boolean isSolved = false; 
		/* 
		 * call solveSudokuClean and save the object to sudokuSolution
		 * this gives us: 
		 * Whether the sudoku is solved
		 * sudokuToSolve as an updated sudoku
		 * possibleSolutions as an iterable list of solutions to try
		 */
		sudokuSolutionObject = solveSudokuClean(sudokuToSolve);
		/* skip if solved */
		if (!sudokuSolutionObject.isSolved()) {
			/*update sudokuToSolve */
			sudokuToSolve = copySudokuArray(sudokuSolutionObject.getSudokuArray());
			/* generate a list of possible entries */
			possibleEntries = sudokuSolutionObject.getPossibleEntries();

			/*make a copy of sudokuToSolve and try an entry then run solveSudoku
			 * if the (recursive) solveSudoku returns isSolved we return */	
			for (Integer[] integers : possibleEntries) {
				sudokuRandomEntry= copySudokuArray(sudokuToSolve);
				insertIntegers(sudokuRandomEntry, integers);
				sudokuSolutionObject = solveSudoku(sudokuRandomEntry);
				if (sudokuSolutionObject.isSolved()) {
					return sudokuSolutionObject;
				}
			}

		}
		return sudokuSolutionObject;

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
		/* tempSudoku will carry all entries from the sudokuTosolve
		 * and all possible entries from each number run(numbers 1 to 
		 * SUDOKU_DIMENSION, if any field remains empty(=0) then the 
		 * sudoku is not solveable anymore*/
		Integer[][] tempSudoku = copySudokuArray(sudokuToSolve); 
		/* helperSudoku will take the possible positions for  the 
		 * numbers 1 - SUDOKU_DIMENSION each number at a time*/
		Integer[][] helperSudoku = generateEmptySudoku(SUDOKU_DIMENSION);

		/* run until no more solutions are found in one pass(1..n),
		 * numFoundSolutions will increase with the return value of
		 * findSolutionUnam 
		 */
		while (numFoundSolutions > 0) {
			numFoundSolutions = 0; 
			for (int i = 1; i <= SUDOKU_DIMENSION; i++) {
				/*  find all lines where you could put i put i in each empty space */
				helperSudoku = addEntriesPerLine(i, SUDOKU_DIMENSION,sudokuToSolve,helperSudoku);
				/*if there is already an entry for i in the column remove illegal i's in the helperSudoku */
				helperSudoku = removeEntriesColumn(i, SUDOKU_DIMENSION,sudokuToSolve,helperSudoku);
				/*if there is an illegal i in a subSquare, remove it*/
				helperSudoku = removeEntriesSubSquare(i,sudokuToSolve,helperSudoku);
				/* add found possibilities to tempSudoku */
				tempSudoku = addSudokuEntries(tempSudoku,helperSudoku);
				/* add found possibilities to possibleSolutions Arraylist */
				possibleSolutions = addSudokuPossibilities(i,helperSudoku,possibleSolutions);
				/* scan helperSudoku for qualified solutions and put them into place*/
				numFoundSolutions += findSolutionUnam(i,sudokuToSolve,helperSudoku);
				helperSudoku = generateEmptySudoku(SUDOKU_DIMENSION); //reset helperSudoku
			}
			
		}
		if (findEmptyFields(tempSudoku) > 0 && findEmptyFields(sudokuToSolve) > 0) {
			tempSudokuObject.setSolvable(false);
		}else if(findEmptyFields(sudokuToSolve) == 0) {
			tempSudokuObject.setSolved(true);
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
					tempSudokuParam[j][i]= helperSudokuParam[j][i].intValue();
				}
			}

		}

		return tempSudokuParam;
	}

	/**
	 * Find solution unam.
	 *
	 * @param numValue the num value
	 * @param sudokuToSolveParam the sudoku to solve param
	 * @param helperSudokuParam the helper sudoku param
	 * @return the int
	 */
	private static int findSolutionUnam(int numValue,Integer[][] sudokuToSolveParam,Integer[][] helperSudokuParam) {
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
		for (int i = 0; i < SUDOKU_DIMENSION; i++) { //iterate row
			for (int j = 0; j < SUDOKU_DIMENSION; j++) { //iterate column
				if (helperSudokuParam[j][i]== numValue) {
					/* scan for conflict
					 * ?conflict break:continue
					 * continue:
					 * remove i from helperSudoku
					 * put i into sudokuFind
					 */
					
					/*scan line*/
					for (int j2 = 0; j2 < SUDOKU_DIMENSION; j2++) {
						if (helperSudokuParam[j2][i]== numValue) {
							counterUnique++;
						}
					}
					/* scan column*/
					for (int j2 = 0; j2 < SUDOKU_DIMENSION; j2++) {
						if (helperSudokuParam[j][j2]== numValue) {
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
						subSquareX = (j/SQRT_DIMENSION);
						subSquareY = (i/SQRT_DIMENSION);
						offSetX =    (subSquareX*SQRT_DIMENSION);
						offSetY =	 (subSquareY*SQRT_DIMENSION);	
						//scan subsquare */
						for (int j2 = offSetY; j2 < offSetY+SQRT_DIMENSION; j2++) { //iterate row
							for (int k = offSetX ; k < offSetX+SQRT_DIMENSION ; k++) { //iterate column
								if (helperSudokuParam[k][j2]== numValue) {
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
					    
						sudokuToSolveParam[j][i] = numValue;
						helperSudokuParam[j][i] = 0;
						iSolvedSomething++;
						
						System.out.println(numValue +";"+j+","+i);
						System.out.println("new entries");	
						printSudoku(sudokuToSolveParam);

					}
					isUnique = false; //reset is Unique
	
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
	 * @param sudokuToSolveParam the sudoku to solve param
	 * @param helperSudokuParam the helper sudoku param
	 * @return the integer[][]
	 */
	private static Integer[][] addEntriesPerLine(int i, int sudokuRange, Integer[][] sudokuToSolveParam, Integer[][] helperSudokuParam) {
		boolean isMatched = false;
		boolean isEmpty = false;
		int numValue = i;
		// iterate over lines

		for (int k = 0; k < sudokuToSolveParam.length; k++) {
			// compare each item in a 'line' with i
			for (int j = 0; j < sudokuToSolveParam.length; j++) {
				// if any cell contains i -> set isMatched
				if (sudokuToSolveParam[j][k].intValue() == numValue) {
					isMatched = true;
					break;
				}

			}
			// if the number does not occur in the line fill empty spaces with i in helper
			// table
			if (isMatched == false) {
				for (int l = 0; l < sudokuToSolveParam.length; l++) {
					// fill empty cells with i
					if (sudokuToSolveParam[l][k].intValue() == 0) {
						helperSudokuParam[l][k] = numValue;

					}

				}
			}
			isMatched = false; //reset isMatched
		}
		return helperSudokuParam;
	} // End of findEmptyLine

	/**
	 * Removes the entries column.
	 *
	 * @param i the i
	 * @param sudokuRange the sudoku range
	 * @param sudokuToSolveParam the sudoku to solve param
	 * @param helperSudokuParam the helper sudoku param
	 * @return the integer[][]
	 */
	private static Integer[][] removeEntriesColumn(int i, int sudokuRange, Integer[][] sudokuToSolveParam, Integer[][] helperSudokuParam) {
		boolean isMatched = false;
		// iterate over columns
		for (int k = 0; k < sudokuRange; k++) {
			// compare each item in a column with i
			for (int j = 0; j < sudokuRange; j++) {
				// if any cell contains i -> set isMatched
				if (sudokuToSolveParam[k][j].intValue() == i) {
					isMatched = true;
					break;
				}

			}
			// if the number does occur in the column then replace all i entries with 0
			if (isMatched) {
				for (int l = 0; l < helperSudokuParam.length; l++) {
					// fill i cells in helper with 0
					if (helperSudokuParam[k][l].intValue() == i) {
						helperSudokuParam[k][l] = 0;
					}

				}

			}

			isMatched = false; //reset isMatched

		}
		return helperSudokuParam;
	}  // End of removeEntries

	/**
	 * Removes the entries sub square.
	 *
	 * @param i the i
	 * @param sudokuToSolveParam the sudoku to solve param
	 * @param helperSudokuParam the helper sudoku param
	 * @return the integer[][]
	 */
	private static Integer[][] removeEntriesSubSquare(int i, Integer[][] sudokuToSolveParam, Integer[][] helperSudokuParam) {
		boolean isMatched = false;
		int numVal = i;
		int[] subSquareOffset= new int[2];
		// iterate over subsquares
		for (int m = 0; m < SUDOKU_DIMENSION; m++) {
			subSquareOffset = calculateSubSquareOffset(m,SQRT_DIMENSION);
			for (int k = 0+ subSquareOffset[0] ; k < subSquareOffset[0]+SQRT_DIMENSION; k++) {
				// compare each item in subsquare with i
				for (int j= 0+ subSquareOffset[1]; j < subSquareOffset[1]+SQRT_DIMENSION; j++) {
					// if any cell contains i -> set isMatched
					if (sudokuToSolveParam[k][j].intValue() == numVal) {
						isMatched = true;
						break;
					}

				}
			}
			if (isMatched) { //if isMatched delete all occurrences of i within the subsquare in the helperSudoku
				for (int k = 0+ subSquareOffset[0] ; k < subSquareOffset[0]+SQRT_DIMENSION; k++) {
					for (int j= 0+ subSquareOffset[1]; j < subSquareOffset[1]+SQRT_DIMENSION; j++) {
						if (helperSudokuParam[k][j].intValue() == numVal) {
							helperSudokuParam[k][j] = 0; 
						}

					}
				}
			}
			isMatched = false; //reset isMatched

		}
		return helperSudokuParam;
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
			if (xCounter >= SQRT_DIMENSION) {
				xCounter = 0; //reset xCounter on line jump
				yCounter++;		
			}
		}
		returnOffset[0] = (int) (xCounter * SQRT_DIMENSION);
		returnOffset[1] = (int) (yCounter * SQRT_DIMENSION);
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
			{{5,6,0,8,4,7,0,0,0},
					{3,0,9,0,0,0,6,0,0},
					{0,0,8,0,0,0,0,0,0},
					{0,1,0,0,8,0,0,4,0},
					{7,9,0,6,0,2,0,1,8},
					{0,5,0,0,3,0,0,9,0},
					{0,0,0,0,0,0,2,0,0},
					{0,0,6,0,0,0,8,0,7},
					{0,0,0,3,1,6,0,5,9},
			}; /*
				{{0,0,0,0,4,0,0,0,0},
			{3,0,9,0,0,0,6,0,0},
			{0,0,8,0,0,0,0,0,0},
			{0,1,0,0,8,0,0,4,0},
			{7,9,0,6,0,2,0,1,8},
			{0,5,0,0,3,0,0,9,0},
			{0,0,0,0,0,0,2,0,0},
			{0,0,6,0,0,0,8,0,7},
			{0,0,0,3,1,6,0,5,9},
					 }; */
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
	 * Copy sudoku array.
	 * 
	 * deep copy a two dimensional sudokuArray
	 * this is essential
	 * 
	 * @return the integer[][]
	 */
	private static Integer[][] copySudokuArray(Integer[][] sudokuToCopy){
		Integer[][] freshCopy = new Integer[SUDOKU_DIMENSION][SUDOKU_DIMENSION];
		for (int i = 0; i < SUDOKU_DIMENSION; i++) {
			for (int j = 0; j < SUDOKU_DIMENSION; j++) {
				freshCopy [j][i] = sudokuToCopy[j][i].intValue();
			}
		}

		return freshCopy;
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
				if(sudokuToPrint[j2][j] != 0) {
				System.out.print(sudokuToPrint[j2][j]);
				} else {
					System.out.print("_");
				}
			}
			System.out.println();
		}

	} // End of printSudoku()

	/**
	 * Insert integers.
	 *
	 * Inserts given integer(numValue,x,y) into given sudoku
	 * 
	 * @param sudokuToSolveParam the sudoku to solve param
	 * @param integersParam the integers param
	 * @return 
	 * @return the integer[][]
	 */
	private static void insertIntegers(Integer[][] sudokuToSolveParam, Integer[] integersParam) {
		sudokuToSolveParam[integersParam[1]][integersParam[2]]= integersParam[0];
	} // End of insertIntegers()

}// End of Class
