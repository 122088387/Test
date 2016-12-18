package com.vmeet.netsocket.data;

import java.util.ArrayList;

public class ColList extends ArrayList<Col>{
	Tbl tbl;
	public ColList(Tbl tbl1){
		this.tbl = tbl1;
	}
	
	public Col get(String colName){
		for(Col col : this){
			if(col.name.equals(colName)) 
				return col;
		}
		return null;
	}

	@Override
	public Col get(int index) {
		for(Col col : this){
			if(col.idx==index) return col;
		}
		return null;
	}
}
