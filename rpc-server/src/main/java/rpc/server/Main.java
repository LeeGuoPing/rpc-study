package rpc.server;

import java.io.File;

import rpc.api.IHelloService;
import rpc.server.dom.XmlParser;
import rpc.server.registcenter.RegistCenter;
import rpc.server.registcenter.RegistCenterImpl;

/**
 * 
 *  服务端发布服务的入口 
 *
 * @author liguoping  
 * @date 2018年6月27日  
 * @version 1.0  
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
		publishService();
	}

	private static void publishService() throws Exception {
		loadConfiguration();  // 加载配置文件
		RegistCenter registCenter = new RegistCenterImpl();
		
		
		registCenter.register(IHelloService.class, HelloServiceImpl.class);
		registCenter.start(XmlParser.HOST,XmlParser.PORT);
	}

	private static void loadConfiguration() throws Exception {
		String projectPath = System.getProperty("user.dir");
		String xmlPath = projectPath+File.separator+"src/main/resources/server.xml";
		System.out.println(xmlPath);
		XmlParser.loadConfigure(xmlPath);
		
	}
}
