package at.codersbay.sudoku.custom.dlx;

public class ColumnHeader  extends Node{
	private int Size;
	private String name;
	
	public ColumnHeader () {
		this.setColumnHead(this);
	}
	
	
	public void appendToColumn(Node node) {
		Node upNode = null;
		Node downNode = null;
		
		if(this.getUpNode() != null) {
			upNode =this.getUpNode();
		} else {
			upNode = node;
		}		
		
		if(this.getDownNode() != null) {
			downNode =this.getDownNode();
		} else {
			downNode = node;
		}		
		
		upNode.setDownNode(node);
		node.setUpNode(upNode);
		node.setDownNode(downNode);
		downNode.setUpNode(node);
	}
	
	public void removeFromDLL() {
		this.getPrevNode().setNextNode(this.getNextNode());
		this.getNextNode().setPrevNode(this.getPrevNode());
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

	public int getIndexOf(HeaderCrown headerCrown) {
		return headerCrown.listOfColumnHeaders.indexOf(this);
	}




	
	
}
