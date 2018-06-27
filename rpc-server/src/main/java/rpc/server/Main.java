package rpc.server;

import java.io.IOException;

import rpc.api.IHelloService;
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
	
	public static void main(String[] args) throws IOException {
		publishService();
	}

	private static void publishService() throws IOException {
		RegistCenter registCenter = new RegistCenterImpl(12345);
		registCenter.register(IHelloService.class, HelloServiceImpl.class);
		registCenter.start();
	}
}
