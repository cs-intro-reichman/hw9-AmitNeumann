/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */		
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size) {
            throw new IllegalArgumentException(
                    "index must be between 0 and size");
        }
        Node current= first;
        for(int i=0;i<index;i++){
            current=current.next;
        }
        return current;
	}
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if(index < 0 || index > size) {
            throw new IllegalArgumentException(
                    "index must be between 0 and size");
        }
        Node blockNode = new Node(block);
        // handles empty list 
        if(size==0){
            first=blockNode;
            last=blockNode;
            size++; 
            return;
        }
        // add as first
        if(index==0){
                blockNode.next=first;
                first=blockNode;
                size++;
                return;
        }
         else if(index==size){
            last.next=blockNode;
            last=blockNode;
            size++;
            return;
        }
        int count=0;
        Node current=first;
        Node previous=null;
        while(count!=index){
            previous=current;
            current=current.next;
            count++;
        }
        // count==index
        previous.next=blockNode;
        blockNode.next=current;
        size++;

	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		this.add(size, block);
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		this.add(0, block);
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if(index < 0 || index > size) {
            throw new IllegalArgumentException(
                    "index must be between 0 and size");
        }
        Node current=first;
        for(int i=0;i<index;i++){
            current=current.next;
        }
        return current.block;

	}		

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		Node current=first;
        int count=0;
        while((current!=null)){
            if(current.block.equals(block)){
                return count;
            }
            current=current.next;
            count++;
        }
        // block was not found
        return -1;

	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		if (size==0) {
            throw new IllegalStateException("empty list");
        }
        Node prev=null;
        Node current=first;
        // if its the first one
        if(node==first){
            first=first.next;
            size--;
            if(first==null) last=null;
            return;
        }
        // its the last
        if(node==last){
            while (current.next != last) {
                current = current.next;
            }
            last=current;
            last.next=null;
            size--;
            return;
        }
        while(current!=node && current!=null){ // in the middle somewhere
            prev=current;
            current=current.next;
        } 
        if(current==node){
            prev.next=current.next;
            size--;
            return;
        }
            // Node not found
            throw new IllegalArgumentException("Node not found in the list");

	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if (size==0) { // empty list case
            throw new IllegalStateException("empty list");
        }
        if(index < 0 || index > size) { // error index case
            throw new IllegalArgumentException(
                    "index must be between 0 and size");
        }
        if(index==0){ // first node case
            this.remove(first);
            return;
        }
        if(index==size){ // last node case
            this.remove(last);
            return;
        }
        // in the middle
        Node current=first;
        Node prev=null;
        for(int i=0;i<index;i++){ // finding the node in the remove index
            prev=current;
            current=current.next;
        }
        prev.next = current.next;
        size--; 

	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
        if(block==null) throw new IllegalArgumentException("not a valid memory block");
        // first node
        if(first.block.equals(block)) {
            this.remove(0);
        }
        //last node 
        if(last.block.equals(block)) {
            this.remove(size);
        }
        Node current=first;
        while(current!=null){
            if(current.block.equals(block)){
                this.remove(current);
                return;
            }
            current=current.next;
        }
        throw new IllegalArgumentException("memory block is not in this list");
	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		Node current=first;
        String print="Memory Block List: [ ";
        while(current!=null){
            print+=""+current.block;
            current=current.next;
            if(current!=null){
                print+=" , "; // punctuation
            }
        }
        print+=" ]";
        return print;
    }

}