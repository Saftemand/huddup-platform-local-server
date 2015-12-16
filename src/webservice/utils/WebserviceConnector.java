package webservice.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class WebserviceConnector {

	public static void main(String[] args){
		getConnectionId(9999);
	}
	
	public static String getConnectionId(int port){
		String connectionId = "";
		
		try {

			URL url = new URL("http://letsplay-huddup.rhcloud.com/connectServerToCloud");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			String input = "{\"ip\":\"" + ip + "\",\"port\":\"" + port + "\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			JSONObject obj = new JSONObject();
			JSONParser parser = new JSONParser();
			
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				obj = (JSONObject) parser.parse(output);
			}

			connectionId = ""+obj.get("connectionId");
			
			conn.disconnect();

		  } catch (Exception e) {

			e.printStackTrace();

		  } 
		
		return connectionId;
	}
	
}
