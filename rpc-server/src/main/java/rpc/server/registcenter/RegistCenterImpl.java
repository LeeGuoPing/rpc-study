package rpc.server.registcenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.midi.MidiDevice.Info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistCenterImpl implements RegistCenter {

	private static final ExecutorService threadPool = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private static final Map<String, Class> registMap = new HashMap<String, Class>();

	private static boolean isRunning = false;

	private static final Logger log = LoggerFactory.getLogger(RegistCenterImpl.class);

	public RegistCenterImpl() {
	}

	public void stop() {
		isRunning = false;
		threadPool.shutdown();

	}

	public void start(String host,int port) throws IOException {
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(host,port));
		log.info("start server");
		try {
			while (true) {
				threadPool.execute(new ServiceTask(serverSocket.accept()));
			}
		} finally {
			serverSocket.close();
		}

	}

	public void register(Class serviceInterface, Class serviceImpl) {
		log.info("serviceInterface {},serviceImpl {}", serviceInterface, serviceImpl);
		registMap.put(serviceInterface.getName(), serviceImpl);

	}

	public boolean isRunning() {
		return isRunning;
	}

	private class ServiceTask implements Runnable {

		Socket client = null;

		ServiceTask(Socket client) {
			this.client = client;
		}

		public void run() {

			ObjectInputStream input = null;
			ObjectOutputStream output = null;
			// 将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
			try {
				input = new ObjectInputStream(client.getInputStream());
				String serviceName = input.readUTF();
				String methodName = input.readUTF();
				log.info("服务端接收到的serviceName: {},methodName: {}",serviceName,methodName);
				Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
				Object[] arguments = (Object[]) input.readObject();
				if (registMap.get(serviceName) == null) {
					throw new ClassNotFoundException(serviceName + "not found");
				}

				// 利用反射调用本地方法
				Class serviceClass = registMap.get(serviceName);
				Method method = serviceClass.getMethod(methodName, parameterTypes);
				Object result = method.invoke(serviceClass.newInstance(), arguments);
				log.info("服务端执行本地方法结果:{} ",result);
				// 将操作结果反序列化,通过socket发送给客户端
				output = new ObjectOutputStream(client.getOutputStream());
				output.writeObject(result);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (client != null) {
					try {
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

}
