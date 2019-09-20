package at.codersbay.sudoku.custom.dlx;

// TODO: Auto-generated Javadoc
/**
 * the Class TemplateSudokus.
 * 
 * content:
 * 
 * copyright: 2019 mario oberwalder
 * 
 * contact: mario.oberwalder@gmail.com
 */
public class TemplateSudokus {

	/**
	 * Generate sudoku.
	 *	
	 *tempInteger [column][row]
	 *
	 * @return the integer[][]
	 */
	public static Integer[][] generateSudoku() {
		//tempInteger [column][row]
		Integer[][] tempInteger = 
				/*	{{5,6,0,8,4,7,0,0,0},
					{3,0,9,0,0,0,6,0,0},
					{0,0,8,0,0,0,0,0,0},
					{0,1,0,0,8,0,0,4,0},
					{7,9,0,6,0,2,0,1,8},
					{0,5,0,0,3,0,0,9,0},
					{0,0,0,0,0,0,2,0,0},
					{0,0,6,0,0,0,8,0,7},
					{0,0,0,3,1,6,0,5,9},
			}; 
				{{0,0,0,0,0,0,0,0,0},
			{0,0,9,0,0,0,6,0,0},
			{0,0,8,0,0,0,0,0,0},
			{0,1,0,0,8,0,0,4,0},
			{7,9,0,6,0,2,0,1,8},
			{0,5,0,0,3,0,0,9,0},
			{0,0,0,0,0,0,2,0,0},
			{0,0,6,0,0,0,8,0,7},
			{0,0,0,3,1,6,0,5,9},
					 }; */
					{{0,0,0,0,0,0,8,4,0},
					{0,0,0,0,5,0,0,0,2},
					{0,5,7,3,0,0,9,0,0},
					{1,2,0,7,4,0,0,0,0},
					{0,0,0,0,6,8,7,0,0},
					{4,0,0,0,0,9,0,0,0},
					{0,0,0,0,7,0,4,0,6},
					{9,1,0,0,0,0,0,0,0},
					{0,0,3,0,0,0,0,0,0},
			}; /*
		   {{3,0,0,0,6,0,2,0,0},
			{0,0,0,0,0,0,0,0,0},
			{4,2,0,0,0,0,6,0,0},
			{0,8,0,0,0,0,9,0,3},
			{2,0,0,0,0,1,0,0,0},
			{1,4,0,8,0,9,0,0,0},
			{0,0,0,3,1,0,0,4,7},
			{0,6,0,0,0,5,0,0,0},
			{0,0,0,7,0,0,8,3,0},
					 }; */
		return tempInteger;
	}

	/**
	 * Generate empty sudoku.
	 * 
	 * tempInteger [column][row]
	 *
	 * @return the integer[][]
	 */
	public static Integer[][] generateEmptySudoku() {
		//tempInteger [column][row]
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
}
