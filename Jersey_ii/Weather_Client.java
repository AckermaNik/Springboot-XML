package Rest_api;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class Weather_Client {

	public static void main(String[] args) {

        String api_key = "61f1b0bf4f594115218c1fa3b1fda2e1";
        
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

	
	private static String beautify_xml(String xml) {
	    return xml.replace("><", ">\n<");   // adds new lines between tags
	}
}