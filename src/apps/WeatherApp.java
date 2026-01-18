package apps;

import java.net.*;
import java.io.*;

public class WeatherApp {
	
	private final String apiKey = "231010605248e05474d3a255a01915a1";
	
	// Cache weather data to avoid repeated API calls
	private String cachedResponse = "";
	private long lastFetchTime = 0;
	private final long CACHE_DURATION = 600000; // 10 minutes in milliseconds
	
	public String result(String c, ChatContextApp context) {
		
		// Check if we have cached data that's still fresh
		if (!cachedResponse.isEmpty() && (System.currentTimeMillis() - lastFetchTime) < CACHE_DURATION) {
			double temp = extractDouble(cachedResponse, "\"temp\":");
			String description = extractString(cachedResponse, "\"description\":\"", "\"");
			return "The weather in " + context.userCity + " is " + description + " with a temperature of " + (int)temp + "°F.";
		}
		
		try {
			
			// Default city ID for Houston
			int cityID = 4736476;

			// build URL
			String urlString = "https://api.openweathermap.org/data/2.5/weather?id=" + cityID + "&lang=en&appid=" + apiKey + "&units=imperial";

            // open connection
			URI uri = new URI(urlString);
			URL url = uri.toURL();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			
			// Get response
			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				return "Sorry, I was unable to fetch the weather right now.";
			}
			
			// Read response more efficiently
			InputStream input = conn.getInputStream();
			byte[] buffer = new byte[8192];
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			int bytesRead;
			while ((bytesRead = input.read(buffer)) != -1) {
				result.write(buffer, 0, bytesRead);
			}
			String jsonResponse = result.toString();
			input.close();
			
			// Cache the response
			cachedResponse = jsonResponse;
			lastFetchTime = System.currentTimeMillis();
			
			// Parse JSON manually
			double temp = extractDouble(jsonResponse, "\"temp\":");
			String description = extractString(jsonResponse, "\"description\":\"", "\"");
			
			if (temp == -999 || description.isEmpty()) {
				return "Sorry, I was unable to parse the weather data.";
			}

            return "The weather in " + context.userCity + " is " + description + " with a temperature of " + (int)temp + "°F.";

        } catch (Exception e) {
            return "Sorry, I was unable to fetch the weather right now.";
        }
	}
	
	// Helper method to extract double value from JSON
	private double extractDouble(String json, String key) {
		int index = json.indexOf(key);
		if (index == -1) return -999;
		
		int start = index + key.length();
		int end = json.indexOf(",", start);
		if (end == -1) end = json.indexOf("}", start);
		
		try {
			return Double.parseDouble(json.substring(start, end).trim());
		} catch (Exception e) {
			return -999;
		}
	}
	
	// Helper method to extract string value from JSON
	private String extractString(String json, String startKey, String endKey) {
		int start = json.indexOf(startKey);
		if (start == -1) return "";
		
		start += startKey.length();
		int end = json.indexOf(endKey, start);
		if (end == -1) return "";
		
		return json.substring(start, end);
	}

	public int check(String c) {
		
		if(c.contains("weather") || c.contains("temperature") || c.contains("rain")) {
			return 8;
		}else if(c.contains("forecast")) {
			return 6;
		}else {
			return 0;
		}
	}

}
