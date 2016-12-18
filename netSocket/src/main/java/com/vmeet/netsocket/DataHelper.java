package com.vmeet.netsocket;

import com.vmeet.netsocket.bean.InfoType;
import com.vmeet.netsocket.bean.SocketObj;
import com.vmeet.netsocket.data.Col;
import com.vmeet.netsocket.data.Separator;
import com.vmeet.netsocket.data.Set;
import com.vmeet.netsocket.data.Tbl;

public class DataHelper {
	public static Tbl getTblFromDS(String SetName, String tblName, String SearchStr,String ip, int port,SocketObj socketObj)
    {
		String colString =  SocketUtil.getData(ip,port,SetName + Separator.at_Str + tblName,InfoType.GetCols,socketObj);
        Set set = new Set(SetName);
        Tbl tbl = new Tbl(tblName);
        tbl.set = set;
        set.tbls.add(tbl);
        int i = 0;
        for(String str : colString.split(";")){
        	if(str.length()==0) continue;
        	String[] strs = str.split(",");
        	Col col = new Col(strs[1]);
        	col.idx = i;
        	tbl.cols.add(col);
        	col.tbl = tbl;
        	i++; 
        }
        String rowStrings = SocketUtil.getData(ip, port,SetName + Separator.at_Str + tblName + Separator.at_Str + SearchStr, InfoType.SearchRows,socketObj);
        set.AddRow(rowStrings, tblName,true);
        return tbl;
    }

}
