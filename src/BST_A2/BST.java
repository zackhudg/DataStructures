package BST_A2;

public class BST implements BST_Interface {
  public BST_Node root;
  int size;
  
  public BST(){ size=0; root=null; }
  
  
  //used for testing, please leave as is
  public BST_Node getRoot(){ return root; }

	public boolean insert(String s) {
		boolean result;
		if(empty()) {
			root = new BST_Node(s);
			result = true;
		}
		else result = root.insertNode(s);
		if(result) size++;
		return result;
	}
	
	public boolean remove(String s) {
		boolean result;
		if(empty()) return false;
		else if (size == 1) {
			if(root.data == s) {
				root = null;
				result = true;
			}
			else result = false;
		}
		else result = root.removeNode(s);
		if(result) size--;
		return result;
	}
	
	public String findMin() {
		if(empty()) return null;
		else return root.findMin().data;
	}
	
	public String findMax() {
		if(empty()) return null;
		else return root.findMax().data;
	}
	
	public boolean empty() {
		return size==0;
	}
	
	
	public boolean contains(String s) {
		if(empty()) return false;
		else return root.containsNode(s);
	}
	
	public int size() {
		return size;
	}
	
	public int height() {
		if(empty()) return -1;
		else return root.getHeight();
	}

}