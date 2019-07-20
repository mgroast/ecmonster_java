package ecmonster.fw;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ecmonster.fw.attribute.Attribute;
import ecmonster.fw.attribute.Attributes;
import ecmonster.fw.attribute.Messages;
import ecmonster.fw.exception.SecurityException;
import ecmonster.fw.exception.SessionElementException;
import ecmonster.fw.exception.TransactionException;
import ecmonster.fw.request.WebRequest;

/**
 * フロントコントローラ
 * 
 */
public class FWFrontController extends HttpServlet {

	/**
	 * JSPファイルパスのprefix
	 */
	protected String nextPagePrefix;
	
	/**
	 * JSPファイルパスのsuffix
	 */
	protected String nextPageSuffix;
	
	/**
	 * 認証情報用のキー
	 */
	protected String principalKey;
	
	/**
	 * 情報メッセージ用のキー
	 */
	protected String infoMessagesKey;
	
	/**
	 * エラーメッセージ用のキー
	 */
	protected String errorMessagesKey;
	
	/**
	 * スコープ値管理クラスのキー
	 */
	protected String attributesKey;
	
	/**
	 * コンストラクタ
	 */
	public FWFrontController() {
		this.nextPagePrefix = "/WEB-INF/views/";
		this.nextPageSuffix = ".jsp";
		this.principalKey = "principal";
		this.infoMessagesKey = "infoMessages";
		this.errorMessagesKey = "errorMessages";
		this.attributesKey = "attributes";
	}

	/**
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		execute(req, res);
	}

	/**
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		execute(req, res);
	}

	/**
	 * 
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	private void execute(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String pathInfo = req.getPathInfo();
		if (pathInfo == null) {
			pathInfo = "";
		}
		printDebug("106 PathInfo=" + pathInfo);
		
		WebRequest webRequest = createWebRequest(req);
		printDebug("109 WebRequest=" + webRequest);
		
		Attributes attributes = createAttributes(req);
		try {
			String nextPath = null;
			//----- 認証情報の確認
			try {
				securityCheck(pathInfo, webRequest, attributes);
				nextPath = createNextPath(pathInfo, webRequest, attributes);
			} catch (SecurityException e) {
				nextPath = securityExceptionHandle(pathInfo, webRequest, attributes, e);
			} catch (SessionElementException e) {
				nextPath = sessionElementExceptionHandle(pathInfo, webRequest, attributes, e);
			} catch (TransactionException e) {
				nextPath = transactionExceptionHandle(pathInfo, webRequest, attributes, e);
			}
			//----- 次のパスが存在しない
			if (nextPath == null) {
				throw new RuntimeException("not found NextPath");
			}
			//----- 先頭文字の確認
			// controller
			if (nextPath.startsWith("/")) {
				String servletPath = req.getServletPath();
				nextPath = servletPath + nextPath;
			// jsp
			} else {
				printDebug("Attributes=" + attributes);
				distributeAttributes(req, attributes);
				nextPath = nextPagePrefix + nextPath + nextPageSuffix;
			}
			//----- 次のパスへ遷移
			printDebug("NextPath=" + nextPath);
			req.getRequestDispatcher(nextPath).forward(req, res);
		} catch (Exception e) {
			String nextPath = globalExceptionHandle(pathInfo, webRequest, attributes, e);
			printDebug("145 Attributes=" + attributes);
			distributeAttributes(req, attributes);
			nextPath = nextPagePrefix + nextPath + nextPageSuffix;
			printDebug("148 NextPath=" + nextPath);
			req.getRequestDispatcher(nextPath).forward(req, res);
		}
	}
	
	/**
	 * リクエスト管理クラスの作成
	 * @param req
	 * @return
	 */
	protected WebRequest createWebRequest(HttpServletRequest req) {
		WebRequest webRequest = new WebRequest();
		for (Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
			webRequest.setParameter(entry.getKey(), entry.getValue());
		}
		return webRequest;
	}
	
	/**
	 * スコープ値管理クラスの作成
	 * @param req
	 * @return
	 */
	protected Attributes createAttributes(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Attributes attributes = (Attributes)session.getAttribute(attributesKey);
		if (attributes == null) {
			attributes = new Attributes();
			attributes.setMessages(createMessages());
		}
		return attributes;
	}
	
	/**
	 * スコープ値管理クラスの値をWebスコープに設定
	 * @param req
	 * @param attributes
	 */
	protected void distributeAttributes(
			HttpServletRequest req,
			Attributes attributes) {
		HttpSession session = req.getSession();
		
		// セッションの無効
		if (attributes.isInvalidate()) {
			session.invalidate();
		} else {
			Map<String, Attribute> attributeMap = new HashMap<>(attributes.getAttributeMap());
			for (Entry<String, Attribute> entry : attributeMap.entrySet()) {
				String key = entry.getKey();
				Attribute attribute = entry.getValue();
				// 値設定
				req.setAttribute(key, attribute.getValue());
				// 削除
				if (attributes.isComplete()) {
					if (attribute.isRemovable()) {
						attributes.getAttributeMap().remove(key);
					}
				}
				attributes.unComplete();
				req.setAttribute(principalKey, attributes.getPrincipal());
				session.setAttribute(attributesKey, attributes);
			}
		}
		// メッセージ情報の設定
		Messages messages = attributes.getMessages();
		req.setAttribute(infoMessagesKey, new ArrayList<>(messages.getInfoMessages()));
		req.setAttribute(errorMessagesKey, new ArrayList<>(messages.getErrorMessages()));
		messages.clear();
	}
	
	/**
	 * メッセージ管理クラスの作成
	 * @return
	 */
	protected Messages createMessages() {
		return new Messages();
	}
	
	/**
	 * デバッグメッセージの出力
	 * @param message
	 */
	protected void printDebug(String message) {
		System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		System.out.println(" Debug " + message);
	}
	
	/**
	 * 次ページのパスを作成
	 * @param pathInfo
	 * @param webRequest
	 * @param attributes
	 * @return
	 */
	protected String createNextPath(String pathInfo, WebRequest webRequest, Attributes attributes) {
		return "default";
	}
	
	/**
	 * 認証情報の確認
	 * @param pathInfo
	 * @param webRequest
	 * @param attributes
	 * @throws SecurityException
	 */
	protected void securityCheck(String pathInfo, WebRequest webRequest, Attributes attributes) 
			throws SecurityException {
		
	}
	
	/**
	 * 認証情報が不正な場合の例外処理
	 * @param pathInfo
	 * @param webRequest
	 * @param attributes
	 * @param e
	 * @return
	 */
	protected String securityExceptionHandle(
			String pathInfo,
			WebRequest webRequest,
			Attributes attributes,
			SecurityException e) {
		throw e;
	}
	
	/**
	 * セッション情報が不正な場合の例外処理
	 * @param pathInfo
	 * @param webRequest
	 * @param attributes
	 * @param e
	 * @return
	 */
	protected String sessionElementExceptionHandle(
			String pathInfo,
			WebRequest webRequest,
			Attributes attributes,
			SessionElementException e) {
		throw e;
	}
	
	/**
	 * トランザクション管理が不正な場合の例外処理
	 * @param pathInfo
	 * @param webRequest
	 * @param attributes
	 * @param e
	 * @return
	 */
	protected String transactionExceptionHandle(
			String pathInfo,
			WebRequest webRequest,
			Attributes attributes,
			TransactionException e) {
		throw e;
	}
	
	/**
	 * 全ての例外を処理
	 * @param pathInfo
	 * @param webRequest
	 * @param attributes
	 * @param e
	 * @return
	 */
	protected String globalExceptionHandle(
			String pathInfo,
			WebRequest webRequest,
			Attributes attributes,
			Exception e) {
		e.printStackTrace();
		attributes.invalidate();
		return "error";
	}
}
