package at.codersbay.sudoku.custom.dlx;

import java.util.ArrayList;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * the Class HeaderCrown.
 * 
 * content:
 * 
 * copyright: 2019 mario oberwalder
 * 
 * contact: mario.oberwalder@gmail.com
 */
public class HeaderCrown {

	/** The list of column headers. */
	ArrayList<ColumnHeader> listOfColumnHeaders = new ArrayList<ColumnHeader>();
	ArrayList<ColumnHeader> unselectedHeaders = new ArrayList<ColumnHeader>();
	Integer[] solutionArray = new Integer[81];
	/**
	 * Adds the.
	 *
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean add(ColumnHeader e) {
		unselectedHeaders.add(e);
		return listOfColumnHeaders.add(e);
		
	}

	/**
	 * Removes the.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	public boolean remove(Object o) {
		return listOfColumnHeaders.remove(o);
	}

	/**
	 * Generate header crown.
	 *
	 * generate the DLL header structure according to SUDOKU_DIMENSION
	 * and constraints 
	 *
	 * @return the header crown
	 */
	public void generateHeaderCrown() {
		ColumnHeader previousNumberHeader;
		ColumnHeader numberHeader = new ColumnHeader();
		/* build header structure for constraints
		 * for each number generate a header according to the constraints
		 */

		/* build header structure for constraints */

		/* build header structure for cell constraint */
		for (int j = 0; j < SudokuNotSeppuku.DIMENSION_SQRD; j++) {
			previousNumberHeader = numberHeader;
			numberHeader = new ColumnHeader();
			numberHeader.setName("c"+ j);
			this.add(numberHeader);
			previousNumberHeader.setNextNode(numberHeader);
			numberHeader.setPrevNode(previousNumberHeader);
		}
		/* build header structure for row constraint */
		for (int j = 1; j <= SudokuNotSeppuku.DIMENSION_SQRD; j++) {
			previousNumberHeader = numberHeader;
			numberHeader = new ColumnHeader();
			numberHeader.setName("r"+ (j/SudokuNotSeppuku.SUDOKU_DIMENSION)+(j%SudokuNotSeppuku.SUDOKU_DIMENSION));
			this.add(numberHeader);
			previousNumberHeader.setNextNode(numberHeader);
			numberHeader.setPrevNode(previousNumberHeader);
		}

		/* build header structure for column constraint */
		for (int j = 1; j <= SudokuNotSeppuku.DIMENSION_SQRD; j++) {
			previousNumberHeader = numberHeader;
			numberHeader = new ColumnHeader();
			numberHeader.setName("col"+ (j/SudokuNotSeppuku.SUDOKU_DIMENSION)+(j%SudokuNotSeppuku.SUDOKU_DIMENSION));
			this.add(numberHeader);
			previousNumberHeader.setNextNode(numberHeader);
			numberHeader.setPrevNode(previousNumberHeader);
		}
		/* build header structure for sub square constraint */
		for (int j = 1; j <= SudokuNotSeppuku.DIMENSION_SQRD; j++) {
			previousNumberHeader = numberHeader;
			numberHeader = new ColumnHeader();
			numberHeader.setName("s"+ (j/SudokuNotSeppuku.SUDOKU_DIMENSION)+(j%SudokuNotSeppuku.SUDOKU_DIMENSION));
			this.add(numberHeader);
			previousNumberHeader.setNextNode(numberHeader);
			numberHeader.setPrevNode(previousNumberHeader);
		}

		/*stitch the columnHeaders together */
		numberHeader.setNextNode(this.listOfColumnHeaders.get(0)); 
		this.listOfColumnHeaders.get(0).setPrevNode(numberHeader);
	}

	/**
	 * Generate nodes.
	 * 
	 * now that we have the crown lets add the notes to the DLL
	 * 
	 */
	public void generateNodes() {
		Node choiceSubNode = new Node();
		Node choicePrevSubNode = new Node();

		/*for the 81 cells generate a node for each number/cell*/
		for (int i = 0; i < SudokuNotSeppuku.DIMENSION_SQRD; i++) {
			choicePrevSubNode = this.listOfColumnHeaders.get(i);
			for (int j = 0; j < SudokuNotSeppuku.SUDOKU_DIMENSION; j++) {
				RowHeader rowHeader = new RowHeader(j);
				choiceSubNode = new Node(rowHeader);
				choiceSubNode.setColumnHead(this.listOfColumnHeaders.get(i));
				choicePrevSubNode.setDownNode(choiceSubNode);
				choiceSubNode.setUpNode(choicePrevSubNode);
				choicePrevSubNode = choiceSubNode;
			}
			/* stitching the nodes together at the end = beginning 
			 * 
			 * important: when stitching together we make sure 
			 * that the columnHead has its connections 
			 * but we stitch together the nodes 
			 * excluding the columnHead
			 * 
			 * */
			choiceSubNode.setDownNode(this.listOfColumnHeaders.get(i).getDownNode());
			this.listOfColumnHeaders.get(i).setUpNode(choiceSubNode);
			this.listOfColumnHeaders.get(i).getDownNode().setUpNode(choiceSubNode);
		}

		/*for each cellConstraintNode generate the corresponding constraint nodes
		 * there are n= SUDOKU_DIMENSION nodes for each header
		 * also generate row heads
		 * */
		for (int i = 0; i < SudokuNotSeppuku.DIMENSION_SQRD; i++) {
			Node cellConstraintNode = this.listOfColumnHeaders.get(i).getDownNode();
			for (int j = 0; j < SudokuNotSeppuku.SUDOKU_DIMENSION; j++) {
				Node rowConstraintNode = new Node(cellConstraintNode.getRowHead());
				Node colConstraintNode = new Node(cellConstraintNode.getRowHead());
				Node subConstraintNode = new Node(cellConstraintNode.getRowHead());
				int offsetDimension = SudokuNotSeppuku.DIMENSION_SQRD;
				
				int rowOffset = offsetDimension;
				int colOffset = 2*offsetDimension;
				int subOffset = 3*offsetDimension;
				
				rowOffset +=  j + (i/ SudokuNotSeppuku.SUDOKU_DIMENSION)* SudokuNotSeppuku.SUDOKU_DIMENSION;
				colOffset += (j + (i*SudokuNotSeppuku.SUDOKU_DIMENSION))% SudokuNotSeppuku.DIMENSION_SQRD;
				subOffset +=  j + ((i/SudokuNotSeppuku.SUDOKU_DIMENSION)/SudokuNotSeppuku.SQRT_DIMENSION)*SudokuNotSeppuku.SQRT_DIMENSION*SudokuNotSeppuku.SUDOKU_DIMENSION +
						((i%SudokuNotSeppuku.SUDOKU_DIMENSION)%SudokuNotSeppuku.SQRT_DIMENSION)*SudokuNotSeppuku.SUDOKU_DIMENSION;
				/*set columnHead */
				rowConstraintNode.setColumnHead(this.listOfColumnHeaders.get(rowOffset)); 
				colConstraintNode.setColumnHead(this.listOfColumnHeaders.get(colOffset)); 
				subConstraintNode.setColumnHead(this.listOfColumnHeaders.get(subOffset)); 
				
				/*vertical DLL connections*/
				this.listOfColumnHeaders.get(rowOffset).appendToColumn(rowConstraintNode); 
				this.listOfColumnHeaders.get(colOffset).appendToColumn(colConstraintNode);
				this.listOfColumnHeaders.get(subOffset).appendToColumn(subConstraintNode);
			    /*horizontal DLL connections forward */
				cellConstraintNode.setNextNode(rowConstraintNode);
				rowConstraintNode.setNextNode(colConstraintNode);
				colConstraintNode.setNextNode(subConstraintNode);
				subConstraintNode.setNextNode(cellConstraintNode);
				/*horizontal DLL connections backward*/
				cellConstraintNode.setPrevNode(subConstraintNode);
				rowConstraintNode.setPrevNode(cellConstraintNode);
				colConstraintNode.setPrevNode(rowConstraintNode);
				subConstraintNode.setPrevNode(colConstraintNode);
				
				
				
				cellConstraintNode = cellConstraintNode.getDownNode(); //travel down a node
			}
			
			
			
		}
	}

	public void print() {
		ColumnHeader columnHeader = this.listOfColumnHeaders.get(0);
		for (int i = 0; i < 400; i++) {
			if(i%324 == 0) {
				System.out.println();
			}
			System.out.print(columnHeader.getName()+"-");		
			columnHeader = (ColumnHeader) columnHeader.getNextNode();
		}
	}

	public void generateSize() {
		for (ColumnHeader columnHeader : this.listOfColumnHeaders)
		{
			columnHeader.setSize();
		}
		
		
	}



}


