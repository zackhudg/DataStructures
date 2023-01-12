package MinBinHeap_A3;

public class MinBinHeap implements Heap_Interface {
  private EntryPair[] array; //load this array
  private int size;
  private static final int arraySize = 10000; //Everything in the array will initially 
                                              //be null. This is ok! Just build out 
                                              //from array[1]

  public MinBinHeap() {
    this.array = new EntryPair[arraySize];
    size = 0;
    array[0] = new EntryPair(null, -100000); //0th will be unused for simplicity 
                                             //of child/parent computations...
                                             //the book/animation page both do this.
  }
    
  //Please do not remove or modify this method! Used to test your entire Heap.
  @Override
  public EntryPair[] getHeap() { 
    return this.array;
  }

  @Override
  public void insert(EntryPair entry) {
	if(entry.getPriority() >= 0) {  
		size++;
		int entryIndex = size;
		if(entryIndex != 1) {
			int parentIndex = entryIndex/2; //java rounds down integers w/ .5 (according to my testing so far)
			EntryPair parent = array[parentIndex];
			while(entry.getPriority() < parent.getPriority()) {
				array[entryIndex] = parent;		// adds the newest leaf to the branch
				
				entryIndex = parentIndex;
				//if(entryIndex == 1) break;	//don't think this will be needed with the 0th entry, we'll see
				parentIndex = entryIndex/2;
				parent = array[parentIndex];
			}
		}
		array[entryIndex] = entry;
	}
  }

  @Override
  public void delMin() {
	  System.out.println("delMin. Size:"+size);
	EntryPair leaf = array[size];
	array[size] = null;
	size--;
	
	int hole = 1;
	bubbleDown(hole, leaf);
	
  }

  @Override
  public EntryPair getMin() {
	return array[1];
  }

  @Override
  public int size() {
	return size;
  }

  @Override
  public void build(EntryPair[] entries) {
	size = 0;
	for(int i = 0; i<entries.length; i++) {	//adds all entries from entries array to the bheap array
		if(entries[i] != null && entries[i].getPriority() >= 0) {
			size++;
			array[size] = entries[i];
		}
	}
	int parentIndex = size/2;
	for(int i = parentIndex; i>0; i--) {
		bubbleDown(i, array[i]);
	}
  }
  
  
  private void bubbleDown(int hole, EntryPair bubblingPair) {
	   
		if(hole*2 < array.length && array[hole*2] != null) {	//left branch exists 
			EntryPair left = array[hole*2];
			if((hole*2)+1 < array.length && array[(hole*2)+1] != null) {	//right branch exists
				EntryPair right = array[(hole*2)+1];
				while(bubblingPair.getPriority() > left.getPriority() || bubblingPair.getPriority() > right.getPriority()) {
					if(left.getPriority() < right.getPriority()) {
						array[hole] = array[hole*2];
						hole = hole*2;
					}
					else {
						array[hole] = array[(hole*2)+1];
						hole = (hole*2)+1;
					}
					
					if(hole*2 < array.length && array[hole*2] != null) left = array[hole*2];
					else break; //no left, can't continue down
					
					if((hole*2)+1 < array.length && array[(hole*2)+1] != null) right = array[(hole*2)+1];
					else {	// root/hole only has left branch, which must lead to last node according to heap structure
						if(bubblingPair.getPriority() > left.getPriority()) {
							array[hole] = array[hole*2];
							hole = hole*2;
						}
						break;
					}
					
				}
			}
			else {	// root/hole only has left branch, which must lead to last node according to heap structure
				if(bubblingPair.getPriority() > left.getPriority()) {
					array[hole] = array[hole*2];
					hole = hole*2;
				}
			}
		}
		array[hole] = bubblingPair;
		//else: cannot bubble down if no branches exist
  }
}