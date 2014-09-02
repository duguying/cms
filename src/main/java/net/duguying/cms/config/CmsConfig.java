package net.duguying.cms.config;
import net.duguying.cms.controller.HelloController;
import net.duguying.cms.controller.IndexController;

import com.jfinal.config.*;

public class CmsConfig extends JFinalConfig {
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}
	
	public void configRoute(Routes me) {
		me.add("/hello", HelloController.class);
		me.add("/", IndexController.class);
	}
	
	public void configPlugin(Plugins me) {}
	public void configInterceptor(Interceptors me) {}
	public void configHandler(Handlers me) {}
}