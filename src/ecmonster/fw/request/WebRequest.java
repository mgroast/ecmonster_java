package ecmonster.fw.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WebRequest {
	/**
	 * パラメータ値
	 */
	private Map<String, List<Parameter>> paramMap;
	
	/**
	 * コンストラクタ
	 */
	public WebRequest() {
		this.paramMap = new HashMap<>();
	}
	
	/**
	 * パラメータ値の取得
	 * @param key
	 * @return
	 */
	public Parameter getParameter(String key) {
		if (!paramMap.containsKey(key)) {
			return new Parameter(null);
		}
		List<Parameter> params = paramMap.get(key);
		if (params.isEmpty()) {
			return null;
		}
		return params.get(0);
	}
	
	/**
	 * パラメータ値の取得
	 * @param key
	 * @return
	 */
	public List<Parameter> getParameters(String key) {
		if (paramMap.containsKey(key)) {
			return new ArrayList<Parameter>();
		}
		return paramMap.get(key);
	}
	
	/**
	 * 
	 * @param key
	 * @param values
	 */
	public void setParameter(String key, String[] values) {
		List<Parameter> params = new ArrayList<>();
		for (String value : values) {
			params.add(new Parameter(value));
		}
		this.paramMap.put(key, params);
	}
	
	/**
	 * 文字列に変換
	 */
	@Override
	public String toString() {
		String str = "Parameter={key=value} -> ";
		for (Entry<String, List<Parameter>> entry : paramMap.entrySet()) {
			str += "{" + entry.getKey() + "=" + entry.getValue() + "} ";
		}
		return str;
	}
}
