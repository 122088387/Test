package com.vmeet.netsocket;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.vmeet.netsocket.data.Row;
import com.vmeet.netsocket.data.Separator;
import com.vmeet.netsocket.data.Set;
import com.vmeet.netsocket.data.Tbl;
import com.vmeet.netsocket.tool.DateTool;
import com.vmeet.netsocket.tool.FileTool;

import android.os.Environment;
import android.util.Log;

public class Helper4Data {
	public static String dataPath = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/com.vmeet.ds/";;
	public static HashMap<String, Set>setHashMap = new HashMap<String, Set>();
	public static void creatSet(String setName,String setColsPath,String setRowsPath) {
			File fileCol = new File(setColsPath);
			if(!fileCol.exists()){
				fileCol.mkdirs();	
			}
			File fileRow = new File(setRowsPath);
			if(!fileRow.exists())
				fileRow.mkdirs();
			if(!setHashMap.containsKey(setName)){
				Set set = new Set(setName);
				setHashMap.put(setName, set);
			}
	}
	public static Set getSet(String setName){
		Set set = setHashMap.get(setName);
		if(set!=null)
			return set;
		return null;
	}
	public static Tbl getTbl(String setName,String tblName){
		Tbl tbl = new Tbl(tblName);
		
		return tbl;
	}
	public static void dropSet(String setName,String setColsPath,String setRowsPath){
		File fileCol = new File(setColsPath);
		if(fileCol.exists()) FileTool.deleteFile(fileCol);
		File fileRow = new File(setRowsPath);
		if(fileRow.exists()) FileTool.deleteFile(fileRow);
	}
	
	 public static void creatTbl(String setName, String tblName,String setColsPath){
			File fileCol = new File(setColsPath);
			if(!fileCol.exists()){
				fileCol.mkdirs();	
			}
				String tblColsFilePath = setColsPath + "/" + tblName + ".txt";
				File tblColsFile = new File(tblColsFilePath);
				if(!tblColsFile.exists()){
					try {
						tblColsFile.createNewFile();
					} catch (IOException e) {
						Log.e("mmm", e.toString());
					}
					String colTtr = setName + "@" + tblName + "@" + "1,rowid1,,text,N,,,N;2,rowCreatTime,,text,N,,,N;3,rowCreatMac,,text,N,,,N";
					FileTool.writeTextToFile(tblColsFile, colTtr,false);
				}
	    }
	
	 public static void dropTbl(String setName,String tblName,String setColsPath,String setRowsPath){
		 String tblColsFilePath = setColsPath + "/" + tblName + ".txt";
		 File file = new File(tblColsFilePath);
		 if(file.exists()) file.delete();
		 String tblRowFilePath = setRowsPath + "/" +  tblName + ".txt";
		 File file1 = new File(tblRowFilePath);
		 if(file1.exists()) file1.delete();
	 }
	 
	 public static void AddCol(String setName, String TblName,String setColsPath, String colStr){ //一次只能增加一个Col,col与col之间的;又可能会多从而出现col为空字符串的问题
		 String tblColsFilePath = setColsPath + "/" + TblName + ".txt";
		 File file = new File(tblColsFilePath);
		 if(!file.exists()) creatTbl(setName, TblName,setColsPath);
		 String colsStr = FileTool.readTextForFile(file);//原先已经存在的col的集合
	     String[] strs = colsStr.split(";");
	     for(String str : strs){
	    	 if(str.equals(""))continue;
	    	 if(str.split(",")[1].equals(colStr.split(",")[1])){
	    		 colsStr = colsStr.replace(str,colStr);
	    		 FileTool.writeTextToFile(file, colsStr,false);
	    		 return;
	    	 }
	     }
	     colsStr += ";" + colStr;
	     FileTool.writeTextToFile(file, colsStr,false);
	    }
	 
	 public static void DelCol(String setName, String TblName,String setColsPath, String colName)//???表不存在为什么还能删除col,为什么要创建表
	    {
		 String tblColsFilePath = setColsPath + "/" + TblName + ".txt";
		 File file = new File(tblColsFilePath);
		 if(!file.exists()) creatTbl(setName, TblName,setColsPath);
	     String colsStr = FileTool.readTextForFile(file);
	     String[] strs = colsStr.split(";");
	     for(String str : strs){
	    	 if(str.equals(""))continue;
	    	 if(str.split(",")[1].equals(colName)){
	    		 colsStr = colsStr.replace(str, "");
	    		 FileTool.writeTextToFile(file, colsStr,false);
	    		 return;
	    	 }
	     }
	    }
	 /**
	  * 添加row
	  * @param setName
	  * @param tblName
	  * @param rowsStr1
	  */
	 public static void AddRows(String setName, String tblName,String rowsStr1,String setRowsPath) //是否需要排重，返回一个rowid1或返回一个完整的row
	    {
		 String tblRowFilePath = setRowsPath + "/"   + tblName + ".txt";
		 File file = new File(tblRowFilePath);
		 if(!file.exists()){
			 file.getParentFile().mkdirs();
			 try {
				file.createNewFile();
			} catch (IOException e) {
				Log.e("mmm", e.toString());
			}
//			 FileTool.writeTextToFile(file, setName+Separator.at_Str + tblName + Separator.at_Str,false);
		 }
		 //直接写入文件
		 if(!rowsStr1.equals(""))
			 FileTool.writeTextToFile(file, rowsStr1 + Separator.semicolon ,true);
	    }

	public static String searchRows(String setName, String TblName, String SearchStr,String setRowsPath) //需要处理时间端问题
	    {
		 String tblRowFilePath = setRowsPath + "/" + TblName + ".txt";
		 File file = new File(tblRowFilePath);
		 if(!file.exists()) return setName + Separator.at_Str + TblName + Separator.at_Str;
		 String rowsStr = FileTool.readTextForFile(file);
	     String[] strs = SearchStr.split(Separator.Comma);
	     String[] rowStrs = rowsStr.split(Separator.semicolon);
	     String getRowStr = "";
	     for(String rowStr : rowStrs){
	    	 boolean ok = true;
	    	 for(String str : strs){
	    		 if(!rowStr.contains(str)){ok = false;break;}
	    		 if(ok) getRowStr += rowStr + Separator.semicolon;
	    	 }
	     }
	        return getRowStr;
	    }
	
	 public static String searchRowsLike(String setName, String TblName, String SearchStr,String setRowsPath) //重要
	    {
		 String tblRowFilePath = setRowsPath + "/" + TblName + ".txt";
		 File file = new File(tblRowFilePath);
		 if(!file.exists()) return setName + Separator.at_Str + TblName + Separator.at_Str;
		 String rowsStr = FileTool.readTextForFile(file);
	     String[] strs = SearchStr.split(Separator.Comma);
	     String[] rowStrs = rowsStr.split(Separator.semicolon);
	     String getRowStr = "";
	     for(String rowStr:rowStrs){
	    	 boolean ok = true;
	    	 for(String str : strs){
	    		 if(str.equals("")){ok = false;break;}
	    		 if(!str.contains(Separator.Sign)) { ok = false; break; }
	    		 String colstring = str.split(Separator.Sign)[0];
	    		 String valstring = str.split(Separator.Sign)[1];
	    		 int i1 = rowStr.indexOf(colstring + Separator.Sign);
	    		 if(i1 == -1) { ok = false; break; }
	    		 int i2 = rowStr.indexOf(Separator.Comma, i1) == -1 ? rowStr.length() : rowStr.indexOf(Separator.Comma, i1);
	    		 String s = rowStr.substring(i1, i2 - i1).replace(colstring + Separator.Sign, "");
	    		 if(!s.contains(valstring)){ ok = false; break; }
	    	 }
	    	 if(ok) getRowStr += rowStr + Separator.semicolon;
	     }
	        return getRowStr;
	    }
	
	 /**
	  * 在文件中删除多条row
	  * @param setName
	  * @param tblName
	  * @param searchStr
	  * @param setRowsPath
	  */
	 public static void delRows(String setName, String tblName, String searchStr,String setRowsPath)
	    {
		 String tblRowFilePath = setRowsPath + "/" + tblName + ".txt";
	     File file = new File(tblRowFilePath);
	     if(!file.exists()) return;
	     String rowsStr =  FileTool.readTextForFile(file);
	     String[] strs = searchStr.split(Separator.Comma);
		 String[] rowStrs = rowsStr.split(Separator.semicolon);
		 String getRowStr = "";
		 for(String rowStr : rowStrs){
			 if(rowStr.equals(""))continue;
			 boolean ok = true;
			 getRowStr = rowStr;
			 for(String str : strs){
				 if(str.equals("")){ok = false; break;}
				 if(!str.contains(Separator.Sign)) { ok = false; break; }
				 String colString = str.split(Separator.Sign)[0];
				 String valString = str.split(Separator.Sign)[1];
				 int i1 = rowStr.indexOf(colString + Separator.Sign);
				 if(i1==-1){ok = false;break;}
				 int i2 = rowStr.indexOf(Separator.Comma, i1) == -1 ? rowStr.length() : rowStr.indexOf(Separator.Comma, i1);
				 Log.e("i2", i1+"");
				 Log.e("i2", i2+"");
				 Log.e("i2", rowStr);
				 String s = rowStr.substring(i1, i2).replace(colString + Separator.Sign, "");
				 if(!s.equals(valString)){ok = false; break;}
			 }
			 if(ok) rowsStr = rowsStr.replace(getRowStr + Separator.semicolon, "");
		 }
		 FileTool.writeTextToFile(file, rowsStr, false);
	    }
	
	 /**
	  * 在文件中删除单个row
	  * @param setName
	  * @param tblName
	  * @param searchStr
	  * @param setRowsPath
	  */
	 public static void delRow(String setName, String tblName, String searchStr,String setRowsPath){
		 String tblRowFilePath = setRowsPath + "/" + tblName + ".txt";
	     File file = new File(tblRowFilePath);
	     if(!file.exists()) return;
	     String rowsStr =  FileTool.readTextForFile(file);
	     String str = searchStr;
		 String[] rowStrs = rowsStr.split(Separator.semicolon);
		 String getRowStr = "";
		 boolean ok = true;
		 for(String rowStr : rowStrs){
			 if(rowStr.equals(""))continue;
			     getRowStr = rowStr;
				 if(str.equals("")){ok = false; break;}
				 if(!str.contains(Separator.Sign)) { ok = false; break; }
				 String colString = str.split(Separator.Sign)[0];
				 String valString = str.split(Separator.Sign)[1];
				 int i1 = rowStr.indexOf(colString + Separator.Sign);
				 if(i1==-1){ok = false;continue;}
				 int i2 = rowStr.indexOf(Separator.Comma, i1) == -1 ? rowStr.length() : rowStr.indexOf(Separator.Comma, i1);
				 String s = rowStr.substring(i1, i2).replace(colString + Separator.Sign, "");
				 if(!s.equals(valString)){
					 ok = false; continue;
				  }
				 else{
					 ok = true; break;
				 }
		 }
		 if(ok) {
			 rowsStr = rowsStr.replace(getRowStr + Separator.semicolon, "");
			 FileTool.writeTextToFile(file, rowsStr, false);
		 }
	 }
	 
	 public static void updateRows(String setName, String tblName, String rowsStr1, String searchStr,String setRowsPath)
	    {
		 String tblRowFilePath = setRowsPath + "/"  + tblName + ".txt";
		 File file = new File(tblRowFilePath);
		 if(!file.exists()) return;
		 String rowsStr = FileTool.readTextForFile(file);
		 String[] strs = searchStr.split(Separator.Comma);
		 String[] rowStrs = rowsStr.split(Separator.semicolon);
		 String getRowStrs = "";
	     String[] strs1 = rowsStr1.split(Separator.Comma);
	     for(String rowStr: rowStrs){
	    	 boolean ok = true;
	    	 String getRowStr = rowStr;
	    	 for(String str:strs){
	    		 if(str.equals("")){ok = false; break;}
	    		 if(!str.contains(Separator.Sign)) { ok = false; break; }
	    		 String colstring = str.split(Separator.Sign)[0];
	             String valstring = str.split(Separator.Sign)[1];
	             int i1 = rowStr.indexOf(colstring + Separator.Sign);
	             if(i1==-1){ok = false;break;}
	             int i2 = rowStr.indexOf(Separator.Comma, i1) == -1?rowStr.length():rowStr.indexOf(Separator.Comma, i1);
	             String s = rowStr.substring(i1, i2-i1).replace(colstring + Separator.Sign, "");
	             if(!s.equals(valstring)){ok = false;break;}
	    	 }
	    	 if(ok){
	    		 for(String str : strs1){
	    			 if(str.equals("")) continue;
	    			 if(!str.contains(Separator.Sign)) continue;
	    			 String colstring = str.split(Separator.Sign)[0];
	    			 String valstring = str.split(Separator.Sign)[1];
	    			 int i1 = getRowStr.indexOf(colstring + Separator.Sign);
	    			 if(i1 == -1) continue;
	    			 int i2 = getRowStr.indexOf(Separator.Comma, i1) == -1 ? getRowStr.length() : getRowStr.indexOf(Separator.Comma, i1);
	    			 String s = getRowStr.substring(i1, i2 - i1);
	    			 getRowStr = getRowStr.replace(s, str);
	    		 }
	    	 }
	    	 getRowStrs += getRowStr + Separator.semicolon;
	     }
	     	FileTool.writeTextToFile(file, getRowStrs, false);
	    }

	// 根据datetime进行排序
	private static void collection(ArrayList<Row> rows) {
		Collections.sort(rows, new Comparator<Row>() {
			@Override
			public int compare(Row lhs, Row rhs) {
				String Timestamp1 = lhs.getVal("datetime");
				String Timestamp2 = rhs.getVal("datetime");
				long lTimestamp1 = DateTool.convertString2long(Timestamp1);
				long lTimestamp2 = DateTool.convertString2long(Timestamp2);
				return (int) (lTimestamp1 - lTimestamp2);
			}
		});
	}
	}
