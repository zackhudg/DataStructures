package DiGraph_A5;

import java.util.Comparator;

public class PathComparator implements Comparator<ShortestPathInfo>{

	@Override
	public int compare(ShortestPathInfo arg0, ShortestPathInfo arg1) {
		if(arg0.getTotalWeight() < arg1.getTotalWeight()) return -1;
		else if(arg0.getTotalWeight() > arg1.getTotalWeight()) return 1;
		else return 0;
	}

}
