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
	public void appendToColumn(Node node) {
		Node upNode = null;
		if(this.getUpNode() != null) {
			upNode =this.getUpNode();
		} else {
			upNode = this;
		}		
		Node downNode = this;
		upNode.setDownNode(node);
		node.setUpNode(upNode);
		node.setDownNode(downNode);
		downNode.setUpNode(node);
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
