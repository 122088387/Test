package com.vmeet.netsocket.bean;

import com.vmeet.netsocket.tool.Constants;

import java.util.Arrays;

/**
 * socket封装头文件
 * 
 * @author sixgod
 * 
 */
public class PkgHead {
	private byte _VerNum; // 1Bytes 0  
	private String _MacFrom; // 6Bytes 1-6   
	private String _MacTo = "00-00-00-00-00-00"; // 6Bytes 7-12
	private String _LocalIp; // 4Bytes 13-16
	private InfoType _InfoType; // 1Bytes 17
	private int _ClientType; // 1Bytes 18
	private int _InfoLen = 0;// 2Bytes 19-20
	private long _DataLen = 0;// 8 Bytes 21-28
	private int _Port = 0; //2Bytes 29-30
    private String _GroupTo; // 6Bytes 31-36
	private byte[] _NetHeadByte = new byte[Constants.HeaderLength]; // 37Bytes

	public PkgHead() {
		_NetHeadByte[0] = 2;
	}
	
	@Override
	public String toString() {
		return "PkgHead [_VerNum=" + _VerNum + ", _MacFrom=" + _MacFrom
				+ ", _MacTo=" + _MacTo + ", _LocalIp=" + _LocalIp
				+ ", _InfoType=" + _InfoType + ", _ClientType=" + _ClientType
				+ ", _InfoLen=" + _InfoLen + ", _DataLen=" + _DataLen
				+ ", _NetHeadByte=" + Arrays.toString(_NetHeadByte) + "]";
	}

	public byte get_VerNum() {
		return _VerNum;
	}

	public void set_VerNum(byte _VerNum) {
		this._VerNum = _VerNum;
		_NetHeadByte[0] = _VerNum;
	}

	public String get_MacFrom() {
		return _MacFrom;
	}

	public void set_MacFrom(String _MacFrom) {
		this._MacFrom = _MacFrom;
		if(_MacFrom==null){
			return;
		}
		String[] strs1 = _MacFrom.split("-");
		for (int i = 0; i <= strs1.length - 1; i++) {
			_NetHeadByte[i + 1] = (byte) Integer.parseInt(strs1[i], 16);
		}
	}

	public String get_MacTo() {
		return _MacTo;
	}

	public void set_MacTo(String _MacTo) {
		this._MacTo = _MacTo;
		String[] strs1 = _MacTo.split("-");
		for (int i = 0; i <= strs1.length - 1; i++) {
			_NetHeadByte[i + 7] = (byte) Integer.parseInt(strs1[i], 16);
		}
	}

	public String get_LocalIp() {
		return _LocalIp;
	}

	public void set_LocalIp(String _LocalIp) {
		this._LocalIp = _LocalIp;
		if (_LocalIp == null) {
			_LocalIp = "192.168.2.1";
		}
		String[] strs3 = _LocalIp.split("\\.");
		for (int i = 0; i <= strs3.length - 1; i++) {
			_NetHeadByte[i + 13] = (byte) Integer.parseInt(strs3[i], 10);
		}
	}

	public InfoType get_InfoType() {
		return _InfoType;
	}

	public void set_InfoType(InfoType _InfoType) {
		this._InfoType = _InfoType;
		_NetHeadByte[17] = (byte) _InfoType.value();
	}

	public int get_ClientType() {
		return _ClientType;
	}

	public void set_ClientType(int _ClientType) {
		this._ClientType = _ClientType;
		_NetHeadByte[18] = (byte) _ClientType;
	}

	public int get_InfoLen() {
		return _InfoLen;
	}

	public void set_InfoLen(int _InfoLen) {
		this._InfoLen = _InfoLen;
		for (int i = 0; i < 2; i++) {
			_NetHeadByte[i + 19] = new Integer(_InfoLen & 0xff).byteValue();//
			_InfoLen = _InfoLen >> 8; // 向右便移
		}
	}

	public long get_DataLen() {
		return _DataLen;
	}

	public void set_DataLen(long _DataLen) {
		this._DataLen = _DataLen;
		for (int i = 0; i < 8; i++) {
			_NetHeadByte[i + 21] = new Long(_DataLen & 0xff).byteValue();//
			_DataLen = _DataLen >> 8; // 向右便宜
		}
	}
	
	public int get_Port() {
		return _Port;
	}

	public void set_Port(int _Port) {
		this._Port = _Port;
		for (int i = 0; i < 2; i++) {
			_NetHeadByte[i + 29] = new Integer(_Port & 0xff).byteValue();//
			_Port = _Port >> 8;
		}
	}

	public String get_GroupTo() {
		return _GroupTo;
	}

	public void set_GroupTo(String _GroupTo) {
		this._GroupTo = _GroupTo;
		String[] strs1 = _GroupTo.split("-");
		for (int i = 0; i <= strs1.length - 1; i++) {
			_NetHeadByte[i + 31] = (byte) Integer.parseInt(strs1[i], 16);
		}
	}

	public byte[] get_NetHeadByte() {
		return _NetHeadByte;
	}

	public void set_NetHeadByte(byte[] _NetHeadByte) {
		this._NetHeadByte = _NetHeadByte;

		_VerNum = _NetHeadByte[0];
		String str1 = Integer.toString(_NetHeadByte[1] & 0xff | 0x100, 16)
				.substring(1);
		for (int i = 1; i <= 5; i++) {
			str1 += "-"
					+ Integer.toString(_NetHeadByte[i + 1] & 0xff | 0x100, 16)
							.substring(1);
		}
		_MacFrom = str1.toUpperCase();
		String str2 = Integer.toString(_NetHeadByte[7] & 0xff | 0x100, 16)
				.substring(1);
		for (int i = 7; i <= 11; i++)
			str2 += "-"
					+ Integer.toString(_NetHeadByte[i + 1] & 0xff | 0x100, 16)
							.substring(1);
		_MacTo = str2.toUpperCase();

		String str3 = Integer.toString(_NetHeadByte[13] & 0xff, 10);
		for (int i = 13; i <= 15; i++)
			str3 += "." + Integer.toString(_NetHeadByte[i + 1] & 0xff, 10);
		_LocalIp = str3;

		_InfoType = InfoType.getInfoType(_NetHeadByte[17]);
		_ClientType = _NetHeadByte[18];
		_InfoLen = byteToShort(_NetHeadByte, 19);
		_DataLen = byteToLong(_NetHeadByte, 21);
		_Port = byteToShort(_NetHeadByte,29);
		String str4 = Integer.toString(_NetHeadByte[31] & 0xff | 0x100, 16)
				.substring(1);
		for (int i = 31; i <= 35; i++)
			str4 += "-"
					+ Integer.toString(_NetHeadByte[i + 1] & 0xff | 0x100, 16)
							.substring(1);
		_GroupTo = str4.toUpperCase();
	}

	/**
	 * byte字节数组转long类型值
	 * 
	 * @param b
	 * @param offset
	 * @return
	 */
	public static long byteToLong(byte[] b, int offset) {
		long s = 0;
		long s0 = b[0 + offset] & 0xff;//
		long s1 = b[1 + offset] & 0xff;
		long s2 = b[2 + offset] & 0xff;
		long s3 = b[3 + offset] & 0xff;
		long s4 = b[4 + offset] & 0xff;//
		long s5 = b[5 + offset] & 0xff;
		long s6 = b[6 + offset] & 0xff;
		long s7 = b[7 + offset] & 0xff;
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}

	/**
	 * byte字节数组转int类型
	 * 
	 * @param b
	 * @param i
	 * @return
	 */
	public static int byteToShort(byte[] b, int i) {
		int s = 0;
		short s0 = (short) (b[0 + i] & 0xff);
		short s1 = (short) (b[1 + i] & 0xff);
		s1 <<= 8;
		s = s0 | s1;
		return s & 0xffff;
	}

	public void Clear() {
		_NetHeadByte = null;
		_MacFrom = null;
		_MacTo = null;
		_LocalIp = null;
		_GroupTo = null;
	}
}
