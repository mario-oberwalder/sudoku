package at.codersbay.sudoku.custom.dlx;

public class ColumnHeader  extends Node{
	private int Size;
	private String name;
	
	/**
	 * Introduce to list.
	 *
	 * Insert a ColumnHeader at the last position in the "crown"
	 * the crown is a ring shaped horizontal DoubleLinkedList-structure 
	 *
	 * @param myMasterHead the my master head
	 */
	public void introduceToCrown(HeaderCrown headerCrown) {
		int currentIndex = headerCrown.listOfColumnHeaders.indexOf(this);
		/*correction so that setPrevNode wont be a null pointer for the first node */
		if (headerCrown.listOfColumnHeaders.size() < 1) {
			currentIndex++;
		} 
		this.setPrevNode(headerCrown.listOfColumnHeaders.get(currentIndex-1));
		this.setNextNode(headerCrown.listOfColumnHeaders.get(0));

	}
		
	
	
	public void createCrown(HeaderCrown headerCrown) {
		// TODO Auto-generated method stub
		
	}
	
	public int getSize() {
		return Size;
	}
	public void setSize(int size) {
		Size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	
	
}
