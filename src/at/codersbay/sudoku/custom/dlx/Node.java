package at.codersbay.sudoku.custom.dlx;

// TODO: Auto-generated Javadoc
/**
 * the Class Node.
 * 
 * content:
 * 
 * copyright: 2019 mario oberwalder
 * 
 * contact: mario.oberwalder@gmail.com
 */
public class Node {
	
	/** The prev node. */
	private Node prevNode;
	
	/** The next node. */
	private Node nextNode;
	
	/** The up node. */
	private Node upNode;
	
	/** The down node. */
	private Node downNode;
	
	/** The column head. */
	private ColumnHeader columnHead;
	
	private RowHeader rowHead;
	
	public Node() {
	
	}
	
	public Node(RowHeader rowHeader) {
		this.rowHead = rowHeader;
	}

	/**
	 * Gets the column head.
	 *
	 * @return the column head
	 */
	public ColumnHeader getColumnHead() {
		return columnHead;
	}
	
	/**
	 * Sets the column head.
	 *
	 * @param columnHead the new column head
	 */
	public void setColumnHead(ColumnHeader columnHead) {
		this.columnHead = columnHead;
	}
	
	/**
	 * Gets the prev node.
	 *
	 * @return the prev node
	 */
	public Node getPrevNode() {
		return prevNode;
	}
	
	/**
	 * Sets the prev node.
	 *
	 * @param prevNode the new prev node
	 */
	public void setPrevNode(Node prevNode) {
		this.prevNode = prevNode;
	}
	
	/**
	 * Gets the next node.
	 *
	 * @return the next node
	 */
	public Node getNextNode() {
		return nextNode;
	}
	
	/**
	 * Sets the next node.
	 *
	 * @param nextNode the new next node
	 */
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
	
	/**
	 * Gets the up node.
	 *
	 * @return the up node
	 */
	public Node getUpNode() {
		return upNode;
	}
	
	/**
	 * Sets the up node.
	 *
	 * @param upNode the new up node
	 */
	public void setUpNode(Node upNode) {
		this.upNode = upNode;
	}
	
	/**
	 * Gets the down node.
	 *
	 * @return the down node
	 */
	public Node getDownNode() {
		return downNode;
	}
	
	/**
	 * Sets the down node.
	 *
	 * @param downNode the new down node
	 */
	public void setDownNode(Node downNode) {
		this.downNode = downNode;
	}

	public RowHeader getRowHead() {
		return rowHead;
	}

	public void setRowHead(RowHeader rowHead) {
		this.rowHead = rowHead;
	}
	
}
