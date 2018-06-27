package rpc.server;

import rpc.api.APIUser;
import rpc.api.IHelloService;

public class HelloServiceImpl implements IHelloService{

	public String sayHi(String name,APIUser user) {
		
		return "Hi, " + name+"User name:"+user.getName();
		
	}

}
