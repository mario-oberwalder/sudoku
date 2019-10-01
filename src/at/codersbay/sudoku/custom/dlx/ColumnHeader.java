package at.codersbay.sudoku.custom.dlx;

// TODO: Auto-generated Javadoc
/**
 * the Class ColumnHeader.
 * 
 * content:
 * 
 * copyright: 2019 mario oberwalder
 * 
 * contact: mario.oberwalder@gmail.com
 */
public class ColumnHeader  extends Node{
	
	/** The Size. */
	private int Size;
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new column header.
	 */
	public ColumnHeader () {
		this.setColumnHead(this);
	}
	
	
	/**
	 * Append to column.
	 *
	 * @param node the node
	 */
	public void appendToColumn(Node node) {
		
		/*see that the node gets its connections and keep columnHead up to date*/
		Node upNode = null;
		Node downNode = null;
		
		if(this.getUpNode() != null) {
			upNode =this.getUpNode();
		} else {
			upNode = node;
			this.setUpNode(node);
			this.setDownNode(node);
		}		
		
		if(this.getDownNode() != null) {
			downNode =this.getDownNode();
		} else {
			downNode = node;
			this.setUpNode(node);
			this.setDownNode(node);
		}		
		
		upNode.setDownNode(node);
		node.setUpNode(upNode);
		node.setDownNode(downNode);
		downNode.setUpNode(node);
		this.setUpNode(node);
		
	}
	
	/**
	 * Removes the from DLL.
	 */
	public void removeFromDLL() {
		this.getPrevNode().setNextNode(this.getNextNode());
		this.getNextNode().setPrevNode(this.getPrevNode());
	}
	
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return Size;
	}
	
	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize() {
		this.Size=0;
		Node startNode = this.getDownNode();
		Node nextNode = startNode.getDownNode();
		
		while(nextNode != startNode) {
			Size++;
			nextNode = nextNode.getDownNode();
		}
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the index of.
	 *
	 * @param headerCrown the header crown
	 * @return the index of
	 */
	public int getIndexOf(HeaderCrown headerCrown) {
		return headerCrown.listOfColumnHeaders.indexOf(this);
	}




	
	
}
