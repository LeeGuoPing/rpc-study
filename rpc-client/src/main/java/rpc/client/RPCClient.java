package rpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 
 * 客户端的代理对象  
 *
 * @author liguoping  
 * @date 2018年6月25日  
 * @version 1.0  
 *
 */
public class RPCClient<T>{
	/**
	 * 
	 * @param serviceInterface 接口类
	 * @param inetSocketAddress 服务端的ip和端口号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getRemoteProxyObj(final Class<?> serviceInterface,final InetSocketAddress inetSocketAddress){
		
		return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface},new InvocationHandler() {
			
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Socket socket = null;
				ObjectOutputStream output = null;
				ObjectInputStream input = null;
				
				try {
					socket = new Socket();
					socket.connect(inetSocketAddress);
					output = new ObjectOutputStream(socket.getOutputStream());
					output.writeUTF(serviceInterface.getName());
					output.writeUTF(method.getName());
					output.writeObject(method.getParameterTypes());
					output.writeObject(args);
					input = new ObjectInputStream(socket.getInputStream());
					return input.readObject();
				} finally {
					if (socket != null) socket.close();
                    if (output != null) output.close();
                    if (input != null) input.close();
				}
			}
		});
	}
}
