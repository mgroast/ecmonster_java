package ecmonster.fw.exception;

/**
 * トランザクション管理で例外が発生した場合に送出される例外
 *
 */
public class TransactionException extends FWException {
	
	/**
	 * コンストラクタ
	 * @param cause
	 */
	public TransactionException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * コンストラクタ
	 * @param message
	 */
	public TransactionException(String message) {
		super(message);
	}
}
