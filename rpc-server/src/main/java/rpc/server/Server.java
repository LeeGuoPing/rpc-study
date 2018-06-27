package rpc.server;

import java.io.IOException;

import rpc.api.IHelloService;
import rpc.server.registcenter.RegistCenter;
import rpc.server.registcenter.RegistCenterImpl;

public class Server {
	
	public static void main(String[] args) throws IOException {
		publishService();
	}

	private static void publishService() throws IOException {
		RegistCenter registCenter = new RegistCenterImpl(12345);
		registCenter.register(IHelloService.class, HelloServiceImpl.class);
		registCenter.start();
	}
}
