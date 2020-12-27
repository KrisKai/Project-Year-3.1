package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import data.BufferManager;
import data.DataListener;
import ui.LoginError;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class SocketServer extends Thread {
	
	private DataListener mDataListener;
	private BufferManager mBufferManager;
	private int port = 12321;
	private String userName;
	private String passWord;
	private String ip = "192.168.0.101";
	public SocketServer() {
		
	}
	public SocketServer(String username, String password, String ip, int port) {
	    this.userName = username;
	    this.passWord = password;
	    this.ip = ip;
	    this.port = port;
	}
	public int getPort() {
		return port;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Socket socket = null;
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		//Socket socket;
		ByteArrayOutputStream byteArray = null;
		try {
			//mServer = new ServerSocket(port);
			socket = new Socket();
			//while (!Thread.currentThread().isInterrupted()) {
				if (byteArray != null) {
					byteArray.reset();}
				else
				{
					byteArray = new ByteArrayOutputStream();
					socket.connect(new InetSocketAddress(ip, port), 10000); // hard-code server address
				//socket = mServer.accept();
				System.out.println("new socket");
				inputStream = new BufferedInputStream(socket.getInputStream());
				outputStream = new BufferedOutputStream(socket.getOutputStream());
				
				byte[] buff = new byte[256];
				byte[] imageBuff = null;
				int len = 0;
				String msg = null;
				// read msg
				while ((len = inputStream.read(buff)) != -1) {
					
					msg = new String(buff, 0, len);
					// JSON analysis
	                JsonParser parser = new JsonParser();
	                boolean isJSON = true;
	                JsonElement element = null;
	                try {
	                    element =  parser.parse(msg);
	                    System.out.println("message: "+msg);
	                }
	                catch (JsonParseException e) {
	                    System.out.println("exception: " + e);
	                    isJSON = false;
	                }
	                if (isJSON && element != null) {
	                    JsonObject obj = element.getAsJsonObject();
	                    JsonElement checkUser = obj.get("username");
	                    JsonElement checkPass = obj.get("password");
	                    if(checkUser.getAsString().equals(userName)&&checkPass.getAsString().equals(passWord)) {
	                    	System.out.println("Checked");
	                    	element = obj.get("type");
		                    if (element != null && element.getAsString().equals("data")) {
		                        element = obj.get("length");
		                        int length = element.getAsInt();
		                        element = obj.get("width");
		                        int width = element.getAsInt();
		                        element = obj.get("height");
		                        int height = element.getAsInt();
		                        imageBuff = new byte[length];
	                            mBufferManager = new BufferManager(length, width, height);
	                            mBufferManager.setOnDataListener(mDataListener);
	                            break;
		                    }
	                    }
	                    else {
	                    	System.out.println("Username or Password is not correct!");
	                    	LoginError error = new LoginError();
	                    	break;
	                    }
	                }
	                else {
	                    byteArray.write(buff, 0, len);
	                    break;
	                }
				}
				
				if (imageBuff != null) {
				    JsonObject jsonObj = new JsonObject();
		            jsonObj.addProperty("state", "ok");
		            outputStream.write(jsonObj.toString().getBytes());
		            outputStream.flush();
		            System.out.println(imageBuff);
		            // read image data
				    while ((len = inputStream.read(imageBuff)) != -1) {
	                    mBufferManager.fillBuffer(imageBuff, len);
	                    System.out.println(imageBuff);
	                    System.out.println(3);
	                }
				}
				
				if (mBufferManager != null) {
					mBufferManager.close();
					System.out.println(4);
				}
			}
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void setOnDataListener(DataListener listener) {
		mDataListener = listener;
	}
}
