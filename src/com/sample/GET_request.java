package com.sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class GET_request {
	
	static String phoneNumber = "9067654321";
	
	public static void main(String[] args) {
	try {
		GET_request.call_me();
	} 
	catch (Exception e) {
        e.printStackTrace();
	}
}
	public static void call_me() throws Exception {
		String url = "http://172.30.13.118:8090/utility/v2/otp/to/63" + phoneNumber + "/template/110";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        //optional default is GET
        con.setRequestMethod("GET");
        
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL: " + url);
        System.out.println("Response Code: " + responseCode);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
        }
        in.close();
        
        //print Response in String
        System.out.println(response.toString());
        
        //Read "message:" JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("Result after Reading JSON Response");
        System.out.println("Message -- "+myResponse.getString("message"));
        
        //Extract number from string
        String OTP = myResponse.getString("message");
        OTP = OTP.replaceAll("\\D+","");
        System.out.println("OTP: " + OTP);
	    }
}