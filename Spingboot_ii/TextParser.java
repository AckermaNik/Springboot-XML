package hy452.ws.spring_client;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TextParser {
	
	  public String annotate_text(String original_text, String json_resp) throws Exception {
	        ObjectMapper mapper = new ObjectMapper(); /*parse the json string into a java tree*/
	        JsonNode root = mapper.readTree(json_resp);
	        
	        String annotated = original_text;

	        if (!root.has("Resources")) {
	            return original_text; // no links found
	        }
        

	        for (JsonNode res : root.get("Resources")) {
	            String surface = res.get("@surfaceForm").asText();
	            String uri = res.get("@URI").asText();

	            String link = "<a href=\"" + uri + "\" target=\"_blank\">" + surface + "</a>"; /*when pressed navigate to a new page*/

	            annotated = annotated.replace(surface, link);
	        }
	        return annotated;
	    }

}
