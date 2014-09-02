package net.duguying.cms.config;

import net.duguying.cms.controller.IndexController;
import net.duguying.cms.controller.PasswordController;
import net.duguying.cms.controller.PostController;
import net.duguying.cms.controller.UserController;

import com.jfinal.config.*;

public class CmsConfig extends JFinalConfig {
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setBaseViewPath("/tpl");
	}

	public void configRoute(Routes me) {
		 me.add("/", IndexController.class);
		 me.add("/post", PostController.class,"post");
		 me.add("/user", UserController.class,"user");
		 me.add("/password", PasswordController.class,"password");
	}

	public void configPlugin(Plugins me) {
	}

	public void configInterceptor(Interceptors me) {
	}

	public void configHandler(Handlers me) {
	}
}