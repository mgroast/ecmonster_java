package ecmonster.fw.attribute;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * スコープ値の管理クラス
 *
 */
public class Attributes implements Serializable {
	
	/**
	 * スコープ値全体
	 */
	private Map<String, Attribute> attributeMap;
	
	/**
	 * メッセージ管理クラス
	 */
	private Messages messages;
	
	/**
	 * 認証情報
	 */
	private Object principal;
	
	/**
	 * スコープ値全体を削除できるか
	 */
	private boolean complete;
	
	/**
	 * スコープ値全体を無効化できるか
	 */
	private boolean invalidate;
	
	/**
	 * コンストラクタ
	 */
	public Attributes() {
		super();
		this.attributeMap = new HashMap<>();
		this.messages = new Messages();
	}
	
	/**
	 * キーを利用してスコープ値を返す
	 * @param key
	 * @return スコープ値
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key) {
		Attribute attribute = attributeMap.get(key);
		if (attribute == null) {
			return null;
		}
		return (T) attribute.getValue();
	}
	
	/**
	 * スコープ値全体を返却
	 * @return
	 */
	public Map<String, Attribute> getAttributeMap() {
		return attributeMap;
	}
	
	/**
	 * キーを利用してスコープ値を設定
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, Object value) {
		attributeMap.put(key, new Attribute(value, true));
	}
	
	/**
	 * キーを利用してスコープ値を設定
	 * @param key
	 * @param value
	 * @param removable
	 */
	public void setAttribute(String key, Object value, boolean removable) {
		attributeMap.put(key, new Attribute(value, removable));
	}
	
	/**
	 * メッセージ管理クラスの取得
	 * @return
	 */
	public Messages getMessages() {
		return messages;
	}
	
	/**
	 * メッセージ管理クラスの設定
	 * @param messages
	 */
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	
	/**
	 * 認証情報の取得
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getPrincipal() {
		return (T) principal;
	}
	
	/**
	 * 認証情報の設定
	 * @param principal
	 */
	public void setPrincipal(Object principal) {
		this.principal = principal;
	}
	
	/**
	 * スコープ値全体を削除できる状態に設定
	 */
	public void complete() {
		complete = true;
	}
	
	/**
	 * スコープ値全体を削除できない状態に設定
	 */
	public void unComplete() {
		complete = false;
	}
	
	/**
	 * スコープ値全体が削除できる状態か取得
	 * @return
	 */
	public boolean isComplete() {
		return complete;
	}
	
	/**
	 * スコープ値全体を無効化にできる状態に設定
	 */
	public void invalidate() {
		invalidate = true;
	}
	
	/**
	 * スコープ値全体を無効かできるか取得
	 * @return
	 */
	public boolean isInvalidate() {
		return invalidate;
	}
	
	/**
	 * 文字列に変換
	 */
	@Override
	public String toString() {
		String str = "Principal=" + principal;
		str += ",Messages=" + messages;
		str += ",Attribute={key=value} -> ";
		for (Entry<String, Attribute> entry : attributeMap.entrySet()) {
			str += "{" + entry.getKey() + "=" + entry.getValue() + "} ";
		}
		return str;
	}
}
