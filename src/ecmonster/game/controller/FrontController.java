package ecmonster.game.controller;

import javax.servlet.annotation.WebServlet;

import ecmonster.fw.FWFrontController;
import ecmonster.fw.attribute.Attributes;
import ecmonster.fw.exception.SecurityException;
import ecmonster.fw.request.WebRequest;

@WebServlet("/fc/*")
public class FrontController extends FWFrontController {

	/**
	 * 
	 */
	@Override
	protected String createNextPath(
			String pathInfo,
			WebRequest webRequest,
			Attributes attributes) {
		String nextPath = null;

		if (pathInfo.equals("/")) {
			nextPath = "index";
		}
		else if (pathInfo.equals("/item")) {
			nextPath = new ItemController().showDetail(webRequest, attributes);
		}
		else if (pathInfo.equals("/auth/signin")) {
			nextPath = new AuthController().signin(webRequest, attributes);
		}
		else if (pathInfo.equals("/auth/auth")) {
			nextPath = new AuthController().auth(webRequest, attributes);
		}
		else if (pathInfo.equals("/home")) {
			nextPath = "home";
		}
		
		return nextPath;
	}
	
	/**
	 * 
	 */
	@Override
	protected String securityExceptionHandle(
			String pathInfo,
			WebRequest webRequest,
			Attributes attributes,
			SecurityException e) {
		return "error";
	}
	
}
