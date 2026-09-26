package Rest_api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class Weather_Client {

	public static void main(String[] args) {

        String api_key = load_api_key();
        if (api_key == null || api_key.isBlank()) {
            System.err.println("Missing OPENWEATHER_API_KEY. Add it to .env or set it as an environment variable.");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter city: ");
        String city = sc.nextLine();
        sc.close();

  
        Client client = ClientBuilder.newClient();

        WebTarget target = client.target("http://api.openweathermap.org/data/2.5/weather")
                                 .queryParam("q", city)
                                 .queryParam("mode", "xml")
                                 .queryParam("units","metric")
                                 .queryParam("appid", api_key);

        String xml_response = target.request(MediaType.APPLICATION_XML).get(String.class);

        System.out.println("Weather XML: " + beautify_xml(xml_response));

        client.close();
    }

	private static String load_api_key() {
	    String apiKey = System.getenv("OPENWEATHER_API_KEY");
	    if (apiKey != null && !apiKey.isBlank()) {
	        return apiKey.trim();
	    }

	    Path[] envFiles = { Path.of(".env"), Path.of("..", ".env") };
	    for (Path envFile : envFiles) {
	        if (!Files.exists(envFile)) {
	            continue;
	        }

	        try {
	            for (String line : Files.readAllLines(envFile)) {
	                String trimmed = line.trim();
	                if (trimmed.startsWith("OPENWEATHER_API_KEY=")) {
	                    return trimmed.substring("OPENWEATHER_API_KEY=".length()).trim();
	                }
	            }
	        } catch (IOException e) {
	            System.err.println("Could not read " + envFile + ": " + e.getMessage());
	        }
	    }

	    return null;
	}

	
	private static String beautify_xml(String xml) {
	    return xml.replace("><", ">\n<");   // adds new lines between tags
	}
}
