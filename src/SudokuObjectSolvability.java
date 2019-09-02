import java.util.ArrayList;

public class SudokuObjectSolvability {
	private boolean isSolvable;
	private Integer[][] sudokuArray;
	private ArrayList<Integer[]> possibleEntries;

	  public void SudokuObjectSolveablility(Boolean isSolvable, Integer[][] sudokuArray, ArrayList<Integer[]> possibleEntries) {
	    this.isSolvable= isSolvable;
	    this.sudokuArray = sudokuArray;
	    this.setPossibleEntries(possibleEntries);
	  }

	public boolean isSolvable() {
		return isSolvable;
	}

	public void setSolvable(boolean isSolvable) {
		this.isSolvable = isSolvable;
	}

	public Integer[][] getSudokuArray() {
		return sudokuArray;
	}

	public void setSudokuArray(Integer[][] sudokuArray) {
		this.sudokuArray = sudokuArray;
	}

	public ArrayList<Integer[]> getPossibleEntries() {
		return possibleEntries;
	}

	public void setPossibleEntries(ArrayList<Integer[]> possibleEntries) {
		this.possibleEntries = possibleEntries;
	}
}
