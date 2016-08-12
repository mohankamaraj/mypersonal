package mohan.inventorycheckor;

import java.util.Comparator;

public class PriceComparator implements Comparator<TargteInventoryItem>{
	
	public int compare(TargteInventoryItem o1, TargteInventoryItem o2) {
		int returnVal = 0;
		if(o1.getPrice() < o2.getPrice()){
	        returnVal =  -1;
	    }else if(o1.getPrice() > o2.getPrice()){
	        returnVal =  1;
	    }else if(o1.getPrice() == o2.getPrice()){
	        returnVal =  0;
	    }
	    return returnVal;
	}
}
