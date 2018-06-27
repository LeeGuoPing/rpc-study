package rpc.server.dom;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * 解析xml文件
 *
 * @author liguoping
 * @date 2018年6月27日
 * @version 1.0
 *
 */
public class XmlParser {
	
	public static String HOST;
	public static int PORT;
	
	public static void main(String[] args) throws Exception {
		String projectPath = System.getProperty("user.dir");
		String xmlPath = projectPath+File.separator+"src/main/resources/server.xml";
		System.out.println(xmlPath);
		loadConfigure(xmlPath);
	}
	
	/**
	 *  加载xml配置文件
	 * @param xmlPath
	 * @throws Exception
	 */
	public static void loadConfigure(String xmlPath) throws Exception {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File(xmlPath));
		Element root = doc.getRootElement();
		Element propertyElement = root.element("property");
		Element connectionElement = propertyElement.element("connection");
		PORT = Integer.parseInt(connectionElement.attributeValue("port"));
		HOST = connectionElement.attributeValue("ip");
		System.out.println("加载配置文件,port: "+PORT+",host: "+HOST);
		
		
	}
}
