package ecmonster.fw.exception;

/**
 * 例外
 *
 */
public class FWException extends RuntimeException {

	/**
	 * コンストラクタ
	 */
	public FWException() {
		super();
	}
	
	/**
	 * コンストラクタ
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writeableStackTrace
	 */
	public FWException(
			String message,
			Throwable cause,
			boolean enableSuppression,
			boolean writeableStackTrace) {
		super(message, cause, enableSuppression, writeableStackTrace);
	}
	
	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public FWException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * 
	 * @param message
	 */
	public FWException(String message) {
		super(message);
	}
	
	/**
	 * 
	 * @param cause
	 */
	public FWException(Throwable cause) {
		super(cause);
	}
}
