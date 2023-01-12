package SPLT_A4;

public class BST_Node {
  String data;
  BST_Node left;
  BST_Node right;
  BST_Node par; //parent...not necessarily required, but can be useful in splay tree
  boolean success;
  
  BST_Node(String data){ 
    this.data=data;
    this.left=null;
    this.right=null;
    this.par=null;
    this.success = true;
  }
  
  BST_Node(String data, BST_Node left,BST_Node right,BST_Node par){ //feel free to modify this constructor to suit your needs
    this.data=data;
    this.left=left;
    this.right=right;
    this.par=par;
    this.success = true;
  }

  // --- used for testing  ----------------------------------------------
  //
  // leave these 3 methods in, as is (meaning also make sure they do in fact return data,left,right respectively)

  public String getData(){ return data; }
  public BST_Node getLeft(){ return left; }
  public BST_Node getRight(){ return right; }

  // --- end used for testing -------------------------------------------

  
  // --- Some example methods that could be helpful ------------------------------------------
  //
  // add the meat of correct implementation logic to them if you wish

  // you MAY change the signatures if you wish...names too (we will not grade on delegation for this assignment)
  // make them take more or different parameters
  // have them return different types
  //
  // you may use recursive or iterative implementations

  /*
  public BST_Node containsNode(String s){ return false; } //note: I personally find it easiest to make this return a Node,(that being the node splayed to root), you are however free to do what you wish.
  public BST_Node insertNode(String s){ return false; } //Really same logic as above note
  public boolean removeNode(String s){ return false; } //I personal do not use the removeNode internal method in my impl since it is rather easily done in SPLT, feel free to try to delegate this out, however we do not "remove" like we do in BST
  public BST_Node findMin(){ return left; } 
  public BST_Node findMax(){ return right; }
  public int getHeight(){ return 0; }
  
  */
  
  public BST_Node containsNode(String s){ //it was me
		if(data.equals(s)) {
			success = true;
			return splay(this);
			//return this;
		}
		if(data.compareTo(s)>0){//s lexiconically less than data
			if(left==null) {
				success = false;
				return splay(this);
				//return null;
			}
			return left.containsNode(s);
		}
		if(data.compareTo(s)<0){
			if(right==null) {
				success = false;
				return splay(this);
				//return null;
			}
			return right.containsNode(s);
		}
		return null; //shouldn't hit
	}
  
	public BST_Node insertNode(String s){
		if(data.compareTo(s)>0){
			if(left==null){
				left=new BST_Node(s);
				left.par = this;
				left.success = true;
				return splay(left);
				//return left;
			}
			return left.insertNode(s);
		}
		if(data.compareTo(s)<0){
			if(right==null){
				right=new BST_Node(s);
				right.par = this;
				right.success = true;
				return splay(right);
				//return right;
			}
			return right.insertNode(s);
		}
		this.success = false;
		return splay(this);
		//return this;//ie we have a duplicate
	}
	public BST_Node removeNode(String s){ //old return was boolean type
		//if(data==null)return false;
		//if(data.equals(s)){		//should happen here every time w/ a splay tree because of contains check in SPLT
		if(left!=null){
			left.par = null;
			left = left.findMax();
			left.right = right;
			if(right != null) right.par = left;
			//left.success = true;	
			return left;
		}
		else if (right != null){
			right.par = null;
			//right.success = true;
			return right;
		}
		
		return null; //shouldn't hit
		//}
//		
//		else if(data.compareTo(s)>0){
//			if(left==null)return false;
//			if(!left.removeNode(s))return false;
//			if(left.data==null)left=null;
//			return true;
//		}
//		else if(data.compareTo(s)<0){
//			if(right==null)return false;
//			if(!right.removeNode(s))return false;
//			if(right.data==null)right=null;
//			return true;
//		}
		
		//return false;
	}
	public BST_Node findMin(){
		if(left!=null) {
			return left.findMin();
		}
		success = true;
		return splay(this);
		//return this
	}
	public BST_Node findMax(){
		if(right!=null) {
			return right.findMax();
		}
		success = true;
		return splay(this);
		//return this
	}
	public int getHeight(){
		int l=0;
		int r=0;
		if(left!=null)l+=left.getHeight()+1;
		if(right!=null)r+=right.getHeight()+1;
		return Integer.max(l, r);
	}
	public String toString(){
		return "Data: "+this.data+", Left: "+((this.left!=null)?left.data:"null")+",Right: "+((this.right!=null)?right.data:"null");
	}

	private BST_Node splay(BST_Node toSplay) {
		if(toSplay.par == null) return toSplay;	//root
		
		//zig
		else if(toSplay.par.par == null) {	//depth == 1
			if(toSplay.par.right == toSplay) {
				rotateLeft(toSplay);
			}
			else {
				rotateRight(toSplay);
			}
		}
		
		else {	//depth >= 2
			
			//zigzig
			BST_Node gpar = toSplay.par.par;
		
			if(gpar.left != null && gpar.left.left == toSplay) {
				rotateRight(toSplay.par);
				rotateRight(toSplay);
			}
			//zigzag
			else if(gpar.left != null && gpar.left.right == toSplay) {
				rotateLeft(toSplay);
				rotateRight(toSplay);
			}

			//zigzig
			else if (gpar.right != null && gpar.right.right == toSplay) {
				rotateLeft(toSplay.par);
				rotateLeft(toSplay);
			}
			//zigzag
			else if(gpar.right != null && gpar.right.left == toSplay) {
				rotateRight(toSplay);
				rotateLeft(toSplay);
			}
			
			
			if(toSplay.par != null) {	//toSplay not at root yet, continue splaying
				splay(toSplay);
			}
		}
		return toSplay;
	}
	//you could have this return or take in whatever you want..so long as it will do the job internally. As a caller of SPLT functions, I should really have no idea if you are "splaying or not"
    //I of course, will be checking with tests and by eye to make sure you are indeed splaying
    //Pro tip: Making individual methods for rotateLeft and rotateRight might be a good idea!

  // --- end example methods --------------------------------------

	private void rotateLeft(BST_Node toSplay) {
		BST_Node par = toSplay.par;
		BST_Node flip = toSplay.left;
		BST_Node gpar = par.par;
		
		toSplay.left = par;
		par.right = flip;
		toSplay.par = par.par;
		par.par = toSplay;
		if(flip != null) flip.par = par;
		
		if(gpar!= null && gpar.right == par) gpar.right = toSplay;
		else if(gpar!= null && gpar.left == par) gpar.left = toSplay;
	}
  
	private void rotateRight(BST_Node toSplay) {
		BST_Node par = toSplay.par;
		BST_Node flip = toSplay.right;
		BST_Node gpar = par.par;
		
		toSplay.right = par;
		par.left = flip;
		toSplay.par = par.par;
		par.par = toSplay;
		if(flip != null) flip.par = par;
		
		if(gpar!= null && gpar.right == par) gpar.right = toSplay;
		else if(gpar!= null && gpar.left == par) gpar.left = toSplay;
	}

  // --------------------------------------------------------------------
  // you may add any other methods you want to get the job done
  // --------------------------------------------------------------------
  
  
}