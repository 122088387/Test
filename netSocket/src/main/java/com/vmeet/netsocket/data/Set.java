package com.vmeet.netsocket.data;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import com.vmeet.netsocket.tool.Constants;
import com.vmeet.netsocket.tool.Tool;

import android.text.TextUtils;
import android.util.Log;

public class Set {
	public String name;
	public List<Tbl> tbls = new ArrayList<Tbl>();
	
	public Set(){
		this.name = "newName";
	}
	public Set(String setName){
		this.name = setName;
	}
	public void addTbl(Tbl tbl){
		tbls.add(tbl);
		tbl.set = this;
	}
	
	/**
	 * 根据表的名字得到表
	 * @param tblName 表的名字
	 * @return
	 */
	public Tbl getTblByTblName(String tblName){
		Tbl tbl = null;
		for(Tbl tbl1: tbls){
			if(tbl1.name.equals(tblName)){
				tbl = tbl1;
				return tbl;
			}
		}
		return null;
	}

	/**
	 * 将所有的表存放到公共变量set中
	 */
	public synchronized void viewDidLoad(String colsPath,String rowsPath) {
		this.tbls.clear();
		ArrayList<Tbl> tbls;
		try {
			tbls = LoadTbls(colsPath);
			for (Tbl tbl : tbls) {
				tblRowsValueInit(tbl,rowsPath);
				this.tbls.add(tbl);
			}
		} catch (IOException e) {
		}
		
	}
	
	/**
	 * 得到cols文件夹下的所有的表
	 * 
	 * @return
	 * @throws IOException 
	 */
	public ArrayList<Tbl> LoadTbls(String colsPath) throws IOException {
		ArrayList<Tbl> tbls = new ArrayList<Tbl>();
		File parentPath = new File(colsPath);
		if (parentPath.exists() && parentPath.isDirectory()) {
			File[] files = parentPath.listFiles();
			for (File file : files) {
				Tbl tbl = new Tbl();
				tbl.loadCols(file);
				tbl.set = this;
				tbls.add(tbl);
			}
		}
//		String[] str = context.getAssets().list(schemaPath);
//		for (String string : str) {
//			InputStream inputStream = context.getAssets().open(schemaPath+"/"+string);
//			String tblName = string.replaceAll(".txt", "");
//			Tbl tbl = GenTblObjectFromFile(inputStream,tblName);
//			tbl.set=this;
//			tbls.add(tbl);
//		}
		return tbls;
	}

	private Tbl GenTblObjectFromFile(InputStream inputStream, String tblName) {
		Tbl tbl = new Tbl(tblName);
		String tblStr = "";
		Log.i("TAG", "得到的表" + tblName);
		try {
			if (inputStream != null) {
				DataInputStream dIs = new DataInputStream(inputStream);
				int length = dIs.available();
				byte[] buffer = new byte[length];
				dIs.read(buffer);
				tblStr = EncodingUtils.getString(buffer, "UTF-8");
				dIs.close();
				inputStream.close();
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
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
			col.tbl = tbl;
			if (col.type.equals("tblname")) {
				tbl.headText = col.headTxt;
			} else {
				tbl.cols.add(col);
			}
		}
		return tbl;
	}
	
	/**
	 * 对表进行初始化
	 * 
	 * @param tbl
	 */
	private void tblRowsValueInit(Tbl tbl,String filePath) {
		ArrayList<Row> rows = tbl.loadRows(filePath);
		if (rows.size() > 0) {
			int rowIdx = 0;
			for (Row row : rows) {
				row.idx = rowIdx;
				row.tbl = tbl;
				rowIdx++;
			}
			tbl.rows = rows;
		} else if (rows.size() <= 0 && tbl.name.equals("sendtblnotice")) {
			Row row1 = new Row();
			row1.idx = 0;
			row1.tbl = tbl;
			rows.add(row1);
			tbl.rows = rows;
		}
	}
	

	/**
	 * 若表结构不存在，就直接将发来的row串存到文件中
	 */
	public void AddStringRow(String tblName,String schemaPath,String rowString){
		String filePath = schemaPath + tblName + ".txt";
		if(!tblName.equals("userlist")){
			rowString = rowString + CharacterConstant.K_K + CharacterConstant.ROW_ROW;
		}
		try {
			File file = new File(filePath);
			if(!file.exists()){
				File dir = new File(file.getParent());
				dir.mkdirs();
				file.createNewFile();
			}
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(rowString.getBytes("utf-8"));
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在表中新添加row
	 * @param rowString
	 * @param tblName
	 * @param append
	 */
	public void AddRow(String rowString, String tblName,boolean append) {
		Tool.showLog(Constants.basePath,"addRow中传过来的值:" + rowString);
		Tbl tbl = this.getTblByTblName(tblName);
		String[] rowStrs;
		rowStrs = rowString.split(CharacterConstant.ROW_ROW);			
		if (!append) {
			Iterator<Row> iterator=tbl.rows.iterator();
			String colName=null;
			String colVal=null;
			colName="dep";
			while(iterator.hasNext()){
				Row row = iterator.next();
				colVal = row.getVal(colName);
				if(!colVal.equals("我的")){
					iterator.remove();
				}
			}
//			tbl.rows.clear();
		}
		for (String rowStr : rowStrs) {
			if (rowStr == null || rowStr.equals(""))
				continue;
			Row row = new Row();
			row.rowStr = rowStr;
			row.idx = tbl.rows.size();
			row.tbl = tbl;
			if(!tbl.isHaveRow(row)){
				tbl.rows.add(row);
			}
		}
	}
	
	/**
	 * 未读条数
	 * @param tbl0
	 * @return
	 */
	public int unRead(Tbl tbl0) {
		int intOAMsgCount = 0;
		if (tbl0 == null) {
			for (Tbl tbl : tbls) {
				if (tbl.name.equals("recmail")) {
					for (Row row : tbl.rows) {
						if ("0".equals(row.getVal("Isread"))||TextUtils.isEmpty(row.getVal("Isread"))) {
							intOAMsgCount++;
						}
					}
					break;
				} else if (tbl.name.equals("recnotice")) {
					for (Row row : tbl.rows) {
						if (!"1".equals(row.getVal("Isread"))) {
							intOAMsgCount++;
						}
					}
					break;
				}
			}
		} else {
			if (tbl0.name.equals("recmail")) {
				for (Row row : tbl0.rows) {
					if ("0".equals(row.getVal("Isread"))) {
						intOAMsgCount++;
					}
				}
			} else if (tbl0.name.equals("recnotice")) {
				for (Row row : tbl0.rows) {
					if (!"1".equals(row.getVal("Isread"))) {
						intOAMsgCount++;
					}
				}
			}
		}
		return intOAMsgCount;

	}

}
