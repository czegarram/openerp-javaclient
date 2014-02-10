package org.czegarram.template.helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class XmlRpcConnector {
	
	public static int Connect(String host, int port, String tinydb, String login, String password) throws MalformedURLException
	{
	  XmlRpcClient xmlrpcLogin = new XmlRpcClient();

	  XmlRpcClientConfigImpl xmlrpcConfigLogin = new XmlRpcClientConfigImpl();
	  xmlrpcConfigLogin.setEnabledForExtensions(true);
	  xmlrpcConfigLogin.setServerURL(new URL("http",host,port,"/xmlrpc/common"));

	  xmlrpcLogin.setConfig(xmlrpcConfigLogin);

	  try {
	    //Connect
	    Object[] params = new Object[] {tinydb,login,password};
	    Object id = xmlrpcLogin.execute("login", params);
	    if (id instanceof Integer)
	      return (Integer)id;
	    return -1;
	  }
	  catch (XmlRpcException e) {
	    //logger.warn("XmlException Error while logging to OpenERP: ",e);
		  System.out.println("XmlException Error while logging to OpenERP: "+ e.getMessage());
	    return -2;
	  }
	  catch (Exception e)
	  {
	    //logger.warn("Error while logging to OpenERP: ",e);
	    System.out.println("Error while logging to OpenERP: "+e.getMessage());
	    return -3;
	  }
	}
	
	public static Vector<String> getDatabaseList(String host, int port) throws MalformedURLException
	{
	  XmlRpcClient xmlrpcDb = new XmlRpcClient();

	  XmlRpcClientConfigImpl xmlrpcConfigDb = new XmlRpcClientConfigImpl();
	  xmlrpcConfigDb.setEnabledForExtensions(true);
	  xmlrpcConfigDb.setServerURL(new URL("http",host,port,"/xmlrpc/db"));

	  xmlrpcDb.setConfig(xmlrpcConfigDb);

	  try {
	    //Retrieve databases
		    Vector<Object> params = new Vector<Object>();
		    Object result = xmlrpcDb.execute("list", params);
		    Object[] a = (Object[]) result;
	
		    Vector<String> res = new Vector<String>();
		    for (int i = 0; i < a.length; i++) {
			    if (a[i] instanceof String)
			    {
			      res.addElement((String)a[i]);
			    }
		    }
		    return res;
	  }
	  catch (XmlRpcException e) {
		System.out.println("XmlException Error while logging to OpenERP: "+ e.getMessage());
	    return null;
	  }
	  catch (Exception e)
	  {
		System.out.println("Error while logging to OpenERP: "+e.getMessage());
	    return null;
	  }
	 }
	
	public static void test(String host, int port){
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL("http",host,port,"/xmlrpc/object"));
			config.setBasicUserName("admin");
			config.setBasicPassword("admin");
	    } catch (MalformedURLException e) {
	    	System.out.println("Error Malformed: "+e.getMessage());
	    }
	    
	    XmlRpcClient client = new XmlRpcClient();
	    client.setConfig(config);
	    
	    HashMap<String, Object> vals = new HashMap<String, Object>();
	    vals.put("name", "Mantavya Gajjar");
	    vals.put("ref", "MGA");
	    Object[] params = new Object[]{"terp", 1, "ruqu", "res.partner", "create", vals};
	       
		Object res = null;
		try {
			res = client.execute("execute", params);
		} catch (XmlRpcException e) {
			System.out.println("XmlException Error while logging to OpenERP2: "+ e.getMessage());
		}
		if(res != null){
			vals = new HashMap<String, Object>();
		    vals.put("name", "M Gajjar");
		    vals.put("type", "default");
		    vals.put("phone", "+91 79 400 500 48");
		    vals.put("mobile", "+91 94263 400 93");
		    vals.put("email", "mail@mantavyagajjar.in");
		    
		    vals.put("street", "Wallstreet-2");
		    vals.put("street2", "Nr Rail Crossing, Ellisbridge");
		    vals.put("city", "Ahmedabad");
		    vals.put("zip", "380 006");
		    vals.put("partner_id", res);
			params = new Object[]{"terp", 1, "a", "res.partner.address", "create", vals};
			try {
				res = client.execute("execute", params);
			} catch (XmlRpcException e) {
				System.out.println("XmlException Error while logging to OpenERP3: "+ e.getMessage());
			}
		}
	}
	
	
}
