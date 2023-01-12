package BST_A2;

public class BST_Node {
  String data;
  BST_Node left;
  BST_Node right;
  
  BST_Node(String data){ this.data=data; }

  // --- used for testing  ----------------------------------------------
  //
  // leave these 3 methods in, as is

  public String getData(){ return data; }
  public BST_Node getLeft(){ return left; }
  public BST_Node getRight(){ return right; }

  // --- end used for testing -------------------------------------------

  
  // --- fill in these methods ------------------------------------------
  //
  // at the moment, they are stubs returning false 
  // or some appropriate "fake" value
  //
  // you make them work properly
  // add the meat of correct implementation logic to them

  // you MAY change the signatures if you wish...
  // make the take more or different parameters
  // have them return different types
  //
  // you may use recursive or iterative implementations

  
  public boolean containsNode(String s){
	  BST_Node currentNode = this;
	  
	  for(int i = 0; i <= getHeight(); i++) {
		  if(currentNode == null) return false;
		  
		  int compare = s.compareTo(currentNode.data);
		  
		  if(compare>0) currentNode = currentNode.right;
		  else if(compare<0) currentNode = currentNode.left;
		  else return true;
	  }
	  
	  if(currentNode == null) return false;
	  return s.equals(currentNode.data);
  }
  
  public boolean insertNode(String s){
	  BST_Node currentNode = this;
	  
	  for(int i = 0; i <= getHeight(); i++) {
		  int compare = s.compareTo(currentNode.data);
		  
		  if(compare>0) {
			  if(currentNode.right == null) {
				  currentNode.right = new BST_Node(s);
				  return true;
			  }
			  else currentNode = currentNode.right;
		  }
		  else if(compare<0) {
			  if(currentNode.left == null) {
				  currentNode.left = new BST_Node(s);
				  return true;
			  }
			  else currentNode = currentNode.left;
		  }
		  else return false;
	  }
	  return false;  
  }
  
  public boolean removeNode(String s){
	  BST_Node parentNode = null;
	  BST_Node childNode = this;
	  
	  for(int i = 0; i <= getHeight(); i++) {
		  if(childNode == null) return false;
		  
		  int compare = s.compareTo(childNode.data);
		  
		  if(compare>0) {
			  parentNode = childNode;
			  childNode = childNode.right;
		  }
		  else if(compare<0) {
			  parentNode = childNode;
			  childNode = childNode.left;
		  }
		  else break;
	  }
	  
	  if(childNode == null) return false;
	  
	  //if childNode has no children
	  if(childNode.left == null && childNode.right == null) {	
		  if(childNode.equals(parentNode.left)) parentNode.left = null;
		  else parentNode.right = null;
	  }
	  
	  //if childNode has 2 children
	  else if(childNode.left != null && childNode.right != null) {
		  BST_Node minNode = childNode.right;
		  while(minNode.left != null) minNode = minNode.left;
		  childNode.data = minNode.data;
		  if(minNode.equals(childNode.right)) childNode.right = minNode.right;
		  else {
			  BST_Node minNodeParent = childNode.right;
			  while(minNodeParent.left != minNode) minNodeParent = minNodeParent.left;
			  minNodeParent.left = minNode.right;
		  }
	  }
	  
	  //if childNode has 1 child to the left:
	  else if(childNode.left != null && childNode.right == null) {
		  if(this == childNode) {
			  this.data = this.left.data;
			  BST_Node left = this.left.left;
			  BST_Node right = this.left.right;
			  this.left = left;
			  this.right = right;
		  }
		  else if(childNode.equals(parentNode.left)) parentNode.left = childNode.left;
		  else parentNode.right = childNode.left;
	  }
	  
	  //if childNode has 1 child to the right:
	  else if(childNode.left == null && childNode.right != null) {
		  if(this == childNode) {
			  this.data = this.right.data;
			  BST_Node left = this.right.left;
			  BST_Node right = this.right.right;
			  this.left = left;
			  this.right = right;
		  }
		  else if(childNode.equals(parentNode.left)) parentNode.left = childNode.right;
		  else parentNode.right = childNode.right;
	  }
	  
	  return true;
  }
  
  public BST_Node findMin(){
	  BST_Node currentNode = this;
	  while(currentNode.left != null) currentNode = currentNode.left;
	  return currentNode;
  }
  
  public BST_Node findMax(){
	  BST_Node currentNode = this;
	  while(currentNode.right != null) currentNode = currentNode.right;
	  return currentNode; 
  }
  
  public int getHeight() {
	  //calls recursive getHeight method
	  return getHeight(this, 0);
  }
  
  // --- end fill in these methods --------------------------------------

  private int getHeight(BST_Node currentNode, int currentHeight){
	  int maxHeight = currentHeight;
	  if(currentNode.left != null) maxHeight = getHeight(currentNode.left, currentHeight+1);
	  if(currentNode.right != null) {
		 int rHeight = getHeight(currentNode.right, currentHeight+1); 
		 if (rHeight > maxHeight) maxHeight = rHeight;
		 
	  }
	  return maxHeight;
  }
  
  // --------------------------------------------------------------------
  // you may add any other methods you want to get the job done
  // --------------------------------------------------------------------
  
  public String toString(){
    return "Data: "+this.data+", Left: "+((this.left!=null)?left.data:"null")
            +",Right: "+((this.right!=null)?right.data:"null");
  }
}