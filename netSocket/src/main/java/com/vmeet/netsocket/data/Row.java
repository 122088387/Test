package com.vmeet.netsocket.data;

import java.util.ArrayList;

import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.Tool;
  
public class Row{
	public Tbl tbl;
	public String rowStr = "";
	public int idx;
	
	public String getVal(String colName){
		String s = "";
			int i1 = rowStr.indexOf(colName + Separator.Sign);
			if (i1 == -1)
				return "";
			int i2 = rowStr.indexOf(Separator.Comma, i1) == -1 ? rowStr.length() : rowStr.indexOf(Separator.Comma, i1);
			try
			{		
			s = rowStr.substring(i1, i2).replace(colName + Separator.Sign, "");
			}
			catch(Exception ee)
			{
				Tool.showLog(Constants.basePath,"getVal:" + rowStr);
				return "";
			}
		return s;
	}
	public void setVal(String colName,String value){
		int i1 = rowStr.indexOf(colName + Separator.Sign);
		if(i1==-1){
			if(rowStr.endsWith(Separator.semicolon))
				rowStr = rowStr.substring(0, rowStr.length()-1);
			rowStr += colName + Separator.Sign + value + Separator.Comma;
			return;
		}
		int i2 = 0;
		try{
			i2 = rowStr.indexOf(Separator.Comma,i1) == -1 ? rowStr.length() : rowStr.indexOf(Separator.Comma, i1);
		}catch(Exception e){
			Tool.showLog(Constants.basePath,"setVal:" + rowStr);
		}
		String s = rowStr.substring(i1,i2);
		String str = colName + Separator.Sign + value;
		rowStr = rowStr.replace(s, str);
	}

	public ArrayList<Row> getChildRows()
    {
        Tbl subTbl = null;
        for(Col col : tbl.cols){
        	if(col.type.equals("tbl"))
        		subTbl = tbl.set.getTblByTblName(col.name);
        }
        if(subTbl == null) return null;
        String FKname = "";
        String PKname = "";
        for(Col col : subTbl.cols){
        	if(col.Hidden.equals("FK")){
        		FKname = col.name;
        		PKname = col.selstr;
        		break;
        	}
        }
        return subTbl.search(new String[]{FKname + CharacterConstant.K_V + getVal(PKname)}, false);
    }
	/**
     *  重写equals方法，用于判断某个对象是否与当前对象相同
     *  在使用ArrayList.Contrains()方法是，会调用此方法，判断两个对象是否相同。
     *  如果不重写，则默认判断引用地址是否相同
     **/
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Row)){
			return false;			
		}else if(obj==this){
			return true;
		}else 
			return this.getVal("rowid1").equals(((Row)obj).getVal("rowid1"));
	}
	
}
