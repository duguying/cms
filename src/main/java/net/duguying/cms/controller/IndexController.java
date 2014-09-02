package net.duguying.cms.controller;

import com.jfinal.core.Controller;

public class IndexController extends Controller {
	public void index() {
		renderText("This is index page.");
	}
}
