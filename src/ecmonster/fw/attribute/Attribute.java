package ecmonster.fw.attribute;

import java.io.Serializable;

public class Attribute implements Serializable {
	
	/**
	 * スコープ値
	 */
	private Object value;
	
	/**
	 * 削除可能かどうか
	 */
	private boolean removable;
	
	/**
	 * コンストラクタ
	 * @param value
	 * @param removable
	 */
	public Attribute(Object value, boolean removable) {
		super();
		this.value = value;
		this.removable = removable;
	}
	
	/**
	 * スコープ値を返す
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * 削除可能かどうかを返す
	 * 
	 * @return
	 */
	public boolean isRemovable() {
		return removable;
	}
	
	/**
	 * 文字列に変換
	 */
	public String toString() {
		return String.valueOf(value);
	}
}
