package com.ultracash.upi.api.services;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class trail {

	public static void main(String[] args) {
		
		try {
			String json = "{\"credType\":\"PIN\",\"credSubType\":\"MPIN\",\"credDataValue\":\"1.0|TXOFi\\/yt6nim0rASkg40Un+tjbFNjYuiIahgejl4Q\\/jPR1D59sfuEtvDhHgz9D4XQJ\\/8zFh2PFXo\\nvybYcDEn6gvrCe7unExyioKAxctYYKQWZnd5yGsA7rewuiyCXh6QxVzmlhGZGG80ZNldsKbL2G7C\\n0bP66DWuQKS\\/Tpqu+U\\/lm3VA+IVWkkIm5Xtt6AtJmkNW1x3PZqAGk\\/9+dj9dSklrr9ynRzP2PZ9\\/\\nUfDAiYW0uCp5RSq9lsItxp93PS\\/d0dQ13Dr2\\/LtXPR0bmNLMJpl19Fp7+L\\/arjHmKerMX3NBOdQt\\nK9R+YN5O\\/iojUXESahZFDdts9\\/4cX2sCnb1HvA==\\n\",\"deviceId\":\"352356074698416\"}";

			JSONObject jsonObject = new JSONObject(json);

			String credDataValue = jsonObject.getString("credDataValue");
			System.out.println(credDataValue);
			credDataValue = credDataValue.replaceAll("\n", "");
			System.out.println(credDataValue);
			String credType = jsonObject.getString("credType");
			System.out.println(credType);
			String credSubType = jsonObject.getString("credSubType");
			System.out.println(credSubType);
			String deviceId = jsonObject.getString("deviceId");
			System.out.println(deviceId);
			System.out.println(json.toString());
			System.out.println("======================");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
