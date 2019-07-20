package ecmonster.game.controller;

import ecmonster.fw.attribute.Attributes;
import ecmonster.fw.attribute.Messages;
import ecmonster.fw.request.Parameter;
import ecmonster.fw.request.WebRequest;

public class AuthController {

	/**
	 * ログインページの表示
	 * @return
	 */
	public String signin(
			WebRequest webRequest,
			Attributes attributes) {
		return "user/signin";
	}
	
	/**
	 * 認証処理
	 * @return
	 */
	public String auth(
			WebRequest webRequest,
			Attributes attributes) {
		//----- 入力値バリデーションチェック
		Messages messages = attributes.getMessages();
		messages.clear();
		Parameter paramId = webRequest.getParameter("id");
		Parameter paramPass = webRequest.getParameter("pass");
		if (paramId.isEmpty()) {
			messages.addErrorMessage("IDが未入力です。");
		}
		if (paramPass.isEmpty()) {
			messages.addErrorMessage("パスワードが未入力です。");
		}
		if (messages.hasErrorMessage()) {
			return "user/signin";
		}
		
		//----- 認証処理
		String id = paramId.getString();
		String pass = paramPass.getString();
		
		System.out.println(id);
		System.out.println(pass);
		
		return "user/signin";
	}
}
