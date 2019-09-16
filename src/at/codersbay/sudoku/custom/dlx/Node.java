package at.codersbay.sudoku.custom.dlx;

public class Node {
	private Node prevNode;
	private Node nextNode;
	private Node upNode;
	private Node downNode;
	private ColumnHeader columnHead;
	
	public ColumnHeader getColumnHead() {
		return columnHead;
	}
	public void setColumnHead(ColumnHeader columnHead) {
		this.columnHead = columnHead;
	}
	public Node getPrevNode() {
		return prevNode;
	}
	public void setPrevNode(Node prevNode) {
		this.prevNode = prevNode;
	}
	public Node getNextNode() {
		return nextNode;
	}
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
	public Node getUpNode() {
		return upNode;
	}
	public void setUpNode(Node upNode) {
		this.upNode = upNode;
	}
	public Node getDownNode() {
		return downNode;
	}
	public void setDownNode(Node downNode) {
		this.downNode = downNode;
	}
	
}
