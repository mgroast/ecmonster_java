package ecmonster.fw.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ecmonster.fw.exception.TransactionException;

/**
 * 
 * トランザクション管理用クラス
 *
 */
public class TransactionManager implements AutoCloseable {
	
	/**
	 * データソースを利用するか
	 */
	protected boolean datasource;
	
	/**
	 * ドライバ名
	 */
	protected String driverName;
	
	/**
	 * データベースURL
	 */
	protected String dbUrl;
	
	
	/**
	 * データベースユーザ名
	 */
	protected String dbUser;
	
	/**
	 * データベースパスワード
	 */
	protected String dbPassword;
	
	/**
	 * ルックアップ名
	 */
	protected String lookupName;
	
	/**
	 * コネクション
	 */
	private Connection connection;
	
	/**
	 * コミット済か
	 */
	private boolean commited;
	
	
	public TransactionManager() {
		this.driverName = "org.postgresql.Driver";
		this.dbUrl = "jdbc:postgresql://localhost:5432/ecmonster";
		this.dbUser = "postgres";
		this.dbPassword = "postgres";
		this.lookupName = "java:comp/env/jdbc/ecmonster";
	}
	
	/**
	 * コネクションの取得
	 * @return コネクション
	 */
	public Connection getConnection() {
		if (connection == null) {
			try {
				if (datasource) {
					InitialContext ctx = new InitialContext();
					DataSource ds = (DataSource)ctx.lookup(lookupName);
					connection = ds.getConnection();
				} else {
					Class.forName(driverName);
					connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				}
				connection.setAutoCommit(false);
			} catch (ClassNotFoundException | NamingException | SQLException e) {
				throw new TransactionException(e);
			}
		}
		return connection;
	}
	
	
	/**
	 * コミット
	 */
	public void commit() {
		if (connection == null) {
			throw new TransactionException("not open transaction");
		}
		commited = true;
	}
	
	/**
	 * ロールバック
	 */
	public void rollback() {
		if (connection == null) {
			throw new TransactionException("not open transaction");
		}
		commited = false;
	}
	
	/*
	 * 
	 * @see java.lang.AutoCloseable#close()
	 */
	public void close() {
		try {
			if (connection != null) {
				if (commited) {
					connection.commit();
				} else {
					connection.rollback();
				}
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			throw new TransactionException(e);
		}
	}

}
