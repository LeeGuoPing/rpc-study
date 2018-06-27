package rpc.server.registcenter;

import java.io.IOException;

/**
 * 
 * 注册中心
 * @author liguoping  
 * @date 2018年6月25日  
 * @version 1.0  
 *
 */
public interface RegistCenter {
	
	public void stop();
	
	public void start(String host,int port) throws IOException;
	
	public void register(Class serviceInterface,Class serviceImpl);
	
	public boolean isRunning();
	
}
