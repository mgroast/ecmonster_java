package ecmonster.fw.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Messages implements Serializable {

	/**
	 * エラーメッセージ
	 */
	private List<String> errorMessages;
	
	/**
	 * 情報メッセージ
	 */
	private List<String> infoMessages;
	
	/**
	 * コンストラクタ
	 */
	public Messages() {
		super();
		this.errorMessages = new ArrayList<>();
		this.infoMessages = new ArrayList<>();
	}
	
	/**
	 * エラーメッセージの取得
	 * @return
	 */
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	
	/**
	 * 情報メッセージの取得
	 * @return
	 */
	public List<String> getInfoMessages() {
		return infoMessages;
	}
	
	/**
	 * 情報メッセージが存在するかを返す
	 * @return true:存在する
	 */
	public boolean hasInfoMessage() {
		return !infoMessages.isEmpty();
	}
	
	/**
	 * エラーメッセージが存在するかを返す
	 * @return true:存在する
	 */
	public boolean hasErrorMessage() {
		return !errorMessages.isEmpty();
	}
	
	/**
	 * エラーメッセージを追加する
	 * @param message
	 */
	public void addErrorMessage(String message) {
		this.errorMessages.add(message);
	}
	
	/**
	 * メッセージを削除する
	 */
	public void clear() {
		this.infoMessages.clear();
		this.errorMessages.clear();
	}
	
	/**
	 * 文字列に変換する
	 */
	@Override
	public String toString() {
		return "Messages [errorMessages=" + errorMessages + ", infoMessages=" + infoMessages + "]";
	}
}
