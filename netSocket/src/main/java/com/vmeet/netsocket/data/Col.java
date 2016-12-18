package com.vmeet.netsocket.data;

public class Col{
	public Tbl tbl;
	public int idx;
	public String name;
	public String headTxt="";
	public String type = "varchar";
	public String Hidden="" ;//隐藏字段
	public String selstr="";//值
	public String group = "";
	public String section = "";
	public Col(){
		
	}
	public Col(String ColName){
        this.name = ColName;
    }
	public enum colType{
		varchar(0),
		decimal(1),
		datetime(2),
		radiobox(3),
		textarea(4),
		htext(5),
		select(6),
		img(7),
		sign(8),
		calendar(9),
		button(10),
		ltext(11),
		sp(12),
		text(13),
		timestamp(14),
		number(15);
		
		private int _value;

		private colType(int value) {
			_value = value;
		}

		public int value() {
			return _value;
		}
		
		public static colType getInfoType(int val){
			for(colType InfoType2 :colType.values()){
				if (InfoType2.value() == val) {
					return InfoType2;
				}
			}
			return null;
		}
	}
	
	public String toString(){
		return idx + "," + name + "," + headTxt + "," + type + "," + Hidden + "," + selstr + "," + group + "," + section;
	}
}
