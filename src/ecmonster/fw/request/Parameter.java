package ecmonster.fw.request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parameter {
	/**
	 * パラメータ値
	 */
	private String value;
	
	/**
	 * コンストラクタ
	 * @param value
	 */
	public Parameter(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getString() {
		return value;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getInt() {
		if (value == null) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 
	 * @param pattern
	 * @return
	 */
	public Date getDate(String pattern) {
		if (value == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(pattern).parse(value);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getDate() {
		return getDate("yyyy/MM/dd");
	}
	
	/**
	 * パラメータ値が空かどうかを返す
	 * nullの場合はtrue
	 * @return
	 */
	public boolean isEmpty() {
		if (value == null) {
			return true;
		}
		return value.isEmpty();
	}
	
	
}
