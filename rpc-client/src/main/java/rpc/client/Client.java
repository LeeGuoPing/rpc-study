package rpc.client;

import java.net.InetSocketAddress;

import rpc.api.APIUser;
import rpc.api.IHelloService;
/**
 * 
 * 可以扩展读取配置文件  
 *
 * @author liguoping  
 * @date 2018年6月25日  
 * @version 1.0  
 *
 */
public class Client {
	
	public static void main(String[] args) {
		IHelloService service = RPCClient.getRemoteProxyObj(IHelloService.class, new InetSocketAddress("172.20.13.208", 12345));
		APIUser user = new APIUser();
		user.setName("李国平");
		user.setAge(100);
        System.out.println(service.sayHi("test",user));
	}
}
