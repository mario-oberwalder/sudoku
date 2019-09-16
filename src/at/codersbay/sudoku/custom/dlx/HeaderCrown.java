package at.codersbay.sudoku.custom.dlx;

import java.util.ArrayList;

public class HeaderCrown {
 ArrayList<ColumnHeader> listOfColumnHeaders = new ArrayList<ColumnHeader>();
 
public boolean add(ColumnHeader e) {
	return listOfColumnHeaders.add(e);
}

public boolean remove(Object o) {
	return listOfColumnHeaders.remove(o);
}
 
}
