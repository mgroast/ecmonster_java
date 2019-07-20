package ecmonster.game.controller;

import ecmonster.fw.attribute.Attributes;
import ecmonster.fw.request.WebRequest;

public class ItemController {
	public String showDetail(
			WebRequest webRequest,
			Attributes attributes) {
		return "item";
	}
}
