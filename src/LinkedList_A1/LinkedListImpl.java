/**
 * COMP 410
 *See inline comment descriptions for methods not described in interface.
 *
*/
package LinkedList_A1;

public class LinkedListImpl implements LIST_Interface {
	Node sentinel; //this will be the entry point to your linked list (the head)
  
	public LinkedListImpl(){//this constructor is needed for testing purposes. Please don't modify!
		sentinel=new Node(0); //Note that the root's data is not a true part of your data set!
		
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
	}
  
	//implement all methods in interface, and include the getRoot method we made for testing purposes. Feel free to implement private helper methods!
  
  	public Node getRoot(){ //leave this method as is, used by the grader to grab your linkedList easily.
  		return sentinel;
  	}

	public boolean insert(double elt, int index) {
		//inserts an element in linked list at specified index
		if (index > this.size()) return false;
		
 		Node insertedNode = new Node(elt);
 		
		Node prevNode = sentinel;
		for(int i = 0; i<index; i++) {
			prevNode = prevNode.next;
		}
		Node nextNode = prevNode.next;
		
		connectNodes(prevNode, insertedNode);
		connectNodes(insertedNode, nextNode);
		
		return true;
	}
	
	public boolean remove(int index) {
		//removes an element in linked list at specified index
		if (index >= this.size()) return false;
		
		Node prevNode = sentinel;
		for(int i = 0; i<index; i++) {
			prevNode = prevNode.next;
		}
		Node removedNode = prevNode.next;
		
		connectNodes(prevNode, removedNode.next);
		
		return true;
	}
	
	public double get(int index) {
		//returns value of node in linked list at specified index
		if(index >= size()) {
			System.out.println("NAN");
			return Double.NaN;
		}
		
		Node node = sentinel;
		for(int i = 0; i<=index; i++) {
			node = node.next;
		}
		return node.data;
	}

	public int size() {
		//returns the number of nodes (not including sentinel) in linked list
		int count = 0; 
		Node currentNode = sentinel;
		
		while(currentNode.next != sentinel) {
			currentNode = currentNode.next;
			count++;
		}
		return count;
	}

	public boolean isEmpty() {
		//returns true if there are no nodes (besides the sentinel) in linked list
		return (sentinel.next == sentinel && sentinel.prev == sentinel);
	}

	public void clear() {
		//removes all nodes (besides sentinel) from the linked list
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
	}
	
	
	private void connectNodes(Node node1, Node node2) {
		//assigns next and prev so that node1 and node2 are properly connected in order
		node1.next = node2;
		node2.prev = node1;
	}
}