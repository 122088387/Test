package com.vmeet.netsocket.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.text.TextUtils;
import android.util.Log;

import com.vmeet.netsocket.tool.DateTool;
import com.vmeet.netsocket.tool.FileTool;

public class Tbl {
	public String tblStr = "";
	public String name;
	public Set set;
	public String headText="";
	public ColList cols ;
	public ArrayList<Row> rows ;
	
	public Tbl(){
		rows = new ArrayList<Row>();
		cols = new ColList(this);
	}
	public Tbl(String tblName){
		name = tblName;
		rows = new ArrayList<Row>();
		cols = new ColList(this);
	}
	/**
	 * 根据传入的路径得到表
	 * @param path
	 * @return
	 */
	public void loadCols(File file){
		if(!file.exists()) return;
		if (file.getName().endsWith(".bap"))return;
		String tblName = file.getName().replace(".txt", "");
		this.name = tblName;
		String tblStr = FileTool.readTextForFile(file);
		String[] arrColumns = tblStr.split(";");
		for (String strColumn : arrColumns) {
			if (strColumn.length() == 0)
				continue;
			Col col = new Col();
			String[] cols = strColumn.split(",",9);
			Log.i("TAG", cols.length + "" + tblName);
			col.name = cols[1];
			col.headTxt = cols[2];
			col.type = cols[3];
			col.Hidden = cols[4];
			col.selstr = cols[5];
			col.group = cols[6];
			col.section = cols[7];
			col.tbl = this;
			col.idx = this.cols.size();
			if (col.type.equals("tblname")) {
				this.headText = col.headTxt;
			} else {
				this.cols.add(col);
			}
		}
	}
	
	/**
	 * 加载相应的表
	 * 
	 * @param tbl
	 * @return
	 */
	public ArrayList<Row> loadRows(String path) {
		ArrayList<Row> rows = new ArrayList<Row>();
		String getStr = "";
		String filePath = path + "/" + this.name + ".txt";
		File file = new File(filePath);
		if (!file.exists()) {
			return rows;
		} else {
			getStr = FileTool.readTextForFile(file);
		}
		String[] rowStrs= getStr.split(CharacterConstant.ROW_ROW);
		for (String rowStr : rowStrs) {
			if (TextUtils.isEmpty(rowStr))
				continue;
			Row row = new Row();
			row.tbl = this;
	        row.rowStr = rowStr;
			rows.add(row);
		}
		return rows;
	}
	/**
	 * 保存表
	 * 
	 * @param tbl
	 */
	public void save(String setRowsPath) {
		String filePath = setRowsPath + "/" + this.name + ".txt";
		String saveStr = "";
		for (Row row : this.rows) {
			 saveStr+= row.rowStr + CharacterConstant.ROW_ROW;
		}
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				File dir = new File(file.getParent());
				dir.mkdirs();
				file.createNewFile();
			}
			FileTool.writeTextToFile(file, saveStr, false);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			
		}
	}

	/**
	 * 保存表
	 *
	 * @param tbl
	 */
	public void save(String setRowsPath,boolean append) {
		String filePath = setRowsPath + "/" + this.name + ".txt";
		String saveStr = "";
		for (Row row : this.rows) {
			saveStr+= row.rowStr + CharacterConstant.ROW_ROW;
		}
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				File dir = new File(file.getParent());
				dir.mkdirs();
				file.createNewFile();
			}
			FileTool.writeTextToFile(file, saveStr, append);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {

		}
	}

	/**
	 * 保存表
	 * 
	 * @param tbl
	 */
	public void saveTblStr(String setRowsPath) {
		String filePath = setRowsPath + "/" + this.name + ".txt";
		String saveStr = this.tblStr;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				File dir = new File(file.getParent());
				dir.mkdirs();
				file.createNewFile();
			}
			FileTool.writeTextToFile(file, saveStr, false);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			
		}
	}
	
	public Row newRow(){
		Row row = new Row();
		row.tbl = this;
		row.idx = rows.size();
		return row;
	}

	public void addRow(Row row){
		this.rows.add(row);
	}
	
	/**
	 * 在表中新添加row,适合添加一条row
	 * @param rowString
	 * @param tblName
	 * @param append
	 */
	public Row AddRow(String rowString,boolean append) {
		String[] rowStrs;
		rowStrs = rowString.split(CharacterConstant.ROW_ROW);			
		if (!append) {
			this.rows.clear();
		}
		for (String rowStr : rowStrs) {
			if (rowStr == null || rowStr.equals(""))
				continue;
			rowStr = rowStr + CharacterConstant.K_K;
			Row row = new Row();
			row.rowStr = rowStr;
			row.idx = this.rows.size();
			row.tbl = this;
			if(this.name.equals("tblnotice")&&row.getVal("AlarmTime").equals("")){
				row.setVal("AlarmTime", DateTool.convertLong2String(System.currentTimeMillis()));
			}
			if(!this.isHaveRow(row)){
				this.rows.add(row);
				return row;//???
			}
		}
		return null;
	}


	/**
	 * 添加多条row
	 * @param rowString
	 */
	public void addRows(String rowString){
		String rowStrs[] = rowString.split(Separator.semicolon);
		for (String rowStr : rowStrs){
			if (rowStr == null || rowStr.equals(""))
				continue;
			Row row = new Row();
			row.rowStr = rowStr;
			row.idx = this.rows.size();
			row.tbl = this;
			this.rows.add(row);
		}
	}



	/**
	 * 根据发来的条件删除指定row
	 * @param tbl
	 * @param delStr
	 */
	public void deleteRow(String delStr){
		String str[] = new String[]{delStr};
		ArrayList<Row> rows = this.search(str, false);//要删除的rows
		Iterator<Row> iterator = this.rows.iterator();
		while(iterator.hasNext()){
			Row row = iterator.next();
			if(rows.contains(row)){
				iterator.remove();
			}
		}
	}
	/**
	 * 删除指定row
	 * @param row
	 */
	public void deleteRow(Row row1){
		Iterator<Row> iterator = this.rows.iterator();
		while(iterator.hasNext()){
			Row row = iterator.next();
			if(row1.equals(row)){
				iterator.remove();
			}
		}
	}
	
	/**
	 * * 根据发来的条件删除指定rows集合
	 * @param filters 条件
	 * @param rows 要查询的rows集合
	 */
	//因为我在row中重写了equls方法，所以此处是根据rowid字段判断是否包含此row，如果判断的是其他字段则不能用contains进行判断
	public void deleteRows(ArrayList<String> filters,ArrayList<Row> rows){
		ArrayList<Row> getRows = search(filters,rows,false);
		Iterator<Row> iterator = rows.iterator();
		while(iterator.hasNext()){
			Row row = iterator.next();
			if(getRows.contains(row)){ 
				iterator.remove();
			}
		}
	}
	
	/**
	 * 从tbl的rows集合中将传入的rows集合删除
	 * @param getRows
	 */
	public void deleteRows(ArrayList<Row> getRows){
		Iterator<Row> iterator = this.rows.iterator();
		while(iterator.hasNext()){
			Row row = iterator.next();
			if(getRows.contains(row)){ 
				iterator.remove();
			}
		}
	}
	/**
	 * 更新传入的row
	 * @param row
	 */
	public void updateRow(Row row){
		if(!this.rows.contains(row)){
			this.rows.add(row);
		}else{
			int index = this.rows.indexOf(row);
			this.rows.set(index, row);
		}
	}
	
	/**
	 * 根据传入的条件进行查询，且关系
	 * @param strs 数组参数
	 * @param like 是否模糊查询
	 * @return
	 */
	public ArrayList<Row> search(String[] strs,Boolean like){
		ArrayList<Row> rows1 = new ArrayList<Row>();
		for(Row row : rows){
			boolean ok = true;
			for (String str : strs) {
				if (str.equals(""))continue;
				if (!str.contains(CharacterConstant.K_V))continue;
				if (str.split(CharacterConstant.K_V).length<2) continue;      
				String colString = str.split(CharacterConstant.K_V)[0];
				String valString = str.split(CharacterConstant.K_V)[1];
				if (!row.rowStr.contains(colString + CharacterConstant.K_V)) {
					ok = false;
					break;
				}
					if (like) {
						if (!row.getVal(colString).contains(valString)) {
							ok = false;
							break;
						} 
						}else {
							if (!row.rowStr.contains(str)) {
								ok = false;
								break;
							}
						}
			}
			if(rows1.contains(row)) continue;
			if(ok) rows1.add(row);
		}
		return rows1;
	}

	/**
	 * 根据传入的条件进行查询，且关系
	 * @param strs 数组参数
	 * @param like 是否模糊查询
	 * @return
	 */
	public ArrayList<Row> search(String[] strs,ArrayList<Row> rows,Boolean like){
		ArrayList<Row> rows1 = new ArrayList<Row>();
		for(Row row : rows){
			boolean ok = true;
			for (String str : strs) {
				if (str.equals(""))continue;
				if (!str.contains(CharacterConstant.K_V))continue;
				if (str.split(CharacterConstant.K_V).length<2) continue;      
				String colString = str.split(CharacterConstant.K_V)[0];
				String valString = str.split(CharacterConstant.K_V)[1];
				if (!row.rowStr.contains(colString + CharacterConstant.K_V)) {
					ok = false;
					break;
				}
					if (like) {
						if (!row.getVal(colString).contains(valString)) {
							ok = false;
							break;
						} 
						}else {
							if (!row.rowStr.contains(str)) {
								ok = false;
								break;
							}
						}
			}
			if(rows1.contains(row)) continue;
			if(ok) rows1.add(row);
		}
		return rows1;
	}

	public ArrayList<Row> searchNot(String[] colNames,ArrayList<Row> rows){
		ArrayList<Row> rows1 = new ArrayList<Row>();
		for(Row row : rows){
			boolean ok = true;
			for (String str : colNames) {
				if (str.equals(""))continue;
				if (!str.contains(CharacterConstant.K_V))continue;
				if (str.split(CharacterConstant.K_V).length<2) continue;
				if (row.rowStr.contains(str)) {
						ok = false;
						break;
					}
			}
			if(rows1.contains(row)) continue;
			if(ok) rows1.add(row);
		}
		return rows1;
	}
	
	/**
	 * 根据传入的条件进行查询，且关系
	 * @param strs 查询条件
	 * @param rows 要查询的row的集合
	 * @param like 是否模糊查询
	 * @return
	 */
	public ArrayList<Row> search(ArrayList<String> strs,ArrayList<Row> rows,Boolean like){
		ArrayList<Row> rows1 = new ArrayList<Row>();
		for(Row row : rows){ 
			boolean ok = true;
			for (String str : strs) { 
				if (str.equals(""))continue;
				if (!str.contains(CharacterConstant.K_V))continue;
				if (str.split(CharacterConstant.K_V).length<2) continue;      
				String colString = str.split(CharacterConstant.K_V)[0];
				String valString = str.split(CharacterConstant.K_V)[1];
				if (!row.rowStr.contains(colString + CharacterConstant.K_V)) {
					ok = false;
					break;
				}
				if (cols.get(colString).type!=null&&cols.get(colString).type.equals("timestamp")) {
					String d1 = valString.split("∮")[0];
					String d2 = valString.split("∮")[1];
					if (d2.equals(""))
						d2 = "3000";
					if (row.getVal(colString).compareTo(d1) < 0) {  
						ok = false;
						break;
					}
					if (row.getVal(colString).compareTo(d2) > 0) {
						ok = false;
						break;
					}
				}else{
					if (like) {
						if (!row.getVal(colString).contains(valString)) {
							ok = false;
							break;
						} 
						}else {
							if(!row.getVal(colString).equals(valString)){
								ok = false;
								break;
							}
						}
				}
			}
				if(rows1.contains(row)) continue;
				if(ok) rows1.add(row);
		}
		return rows1;
	}
	/**
	 * 根据传入的条件进行查询，或关系
	 * @param strs 查询条件
	 * @param rows 要查询的row的集合
	 * @return
	 */
	public ArrayList<Row> search(String[] strs,ArrayList<Row> rows){
		ArrayList<Row> rows1 = new ArrayList<Row>();
		for(Row row : rows){ 
			boolean ok = false;
			for (String str : strs) { 
				if (str.equals(""))continue;
				if (!str.contains(CharacterConstant.K_V))continue;
				String colString = str.split(CharacterConstant.K_V)[0];
				String valString = str.split(CharacterConstant.K_V)[1];
				if (!row.rowStr.contains(colString + CharacterConstant.K_V)) {
					ok = false;
					continue;
				}
				if (row.getVal(colString).contains(valString)) {
					ok = true;
					break;
				}
			}
				if(rows1.contains(row)) continue;
				if(ok) rows1.add(row);
		}
		return rows1;
	}
	/**
	 * 将传入的rows转换为string
	 * @param rows
	 * @return
	 */
	public String RowsToString(ArrayList<Row> rows){
		String getStr = "";
		for(Row row : rows){
			getStr = getStr + row.rowStr + CharacterConstant.ROW_ROW;
		}
		return getStr;
	}
	
	/**
	 * 判断传入的row在tbl中是否存在,根据唯一标示rowid1确定
	 * @param row
	 * @return
	 */
	public Boolean isHaveRow(Row row) {
		if(rows.contains(row)){
			int index = rows.indexOf(row);
			rows.set(index, row);
			return true;
		}
		return false;
	}
	
	public ArrayList<String> getStr(String colName,ArrayList<Row> rows){
		ArrayList<String> strs = new ArrayList<String>();
		for(Row row : rows){
			if(!strs.contains(row.getVal(colName))){
				strs.add(row.getVal(colName));
			}
		}
		return strs;
	}
	public HashMap<String, ArrayList<Row>> getHash(String colName,ArrayList<Row> rows){
		HashMap<String, ArrayList<Row>> groups = new HashMap<String, ArrayList<Row>>();
		for(Row row : rows){
			if(!groups.containsKey(row.getVal(colName))){
				ArrayList<Row> grouprows = new ArrayList<Row>();
				groups.put(row.getVal(colName),grouprows);
			}
			groups.get(row.getVal(colName)).add(row);
		}
		return groups;
	}
}
