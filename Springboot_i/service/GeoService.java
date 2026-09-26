package ht452.ws.rest_springboot.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.*;

@Service

public class GeoService{
	
	private Map<String, Element> countries_map = new HashMap<>(); /*mapped by code*/
    private Map<String, Element> rivers_map = new HashMap<>();
    Map<String, Element> countries_by_id = new HashMap<>(); /*mapped by their id*/
    private Document doc;
    
    @PostConstruct
    public void init_once() {
    	
    	 
    	
    	try {
    		InputStream file = new ClassPathResource("xmldata_mondial-3.0.xml").getInputStream();
    		
	    	// create parser
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder;
			
			builder = factory.newDocumentBuilder();
			
	        // parse to tree DOM and normalize the white spaces
			
			doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			
			
			//countries
            NodeList countries = doc.getElementsByTagName("country");
            for (int i = 0; i < countries.getLength(); i++) {
                Element country = (Element) countries.item(i);
                String code = country.getAttribute("datacode");
                String id = country.getAttribute("id");
                countries_map.put(code,country); //I search based on coutry's datacode
                countries_by_id.put(id, country);
            }
            
            // rivers
            
            NodeList rivers = doc.getElementsByTagName("river");
            for (int i = 0; i < rivers.getLength(); i++) {
                Element river = (Element) rivers.item(i);
                String name = river.getAttribute("name"); //I search based on rivers's name
                rivers_map.put(name,river);
            }
            
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         	
    }
	
    
    /* COUNTRY FUCTIONS */
    
    @Operation(summary = "Get country name by code")   
    public String get_country_name(String code) {
        Element country = countries_map.get(code);
        
        if(country!=null) {
        	return country.getAttribute("name");
        }
        return null;
    }
    
    @Operation(summary = "Get country capital by code")
    public String country_capital_name(String code) {
        Element country = countries_map.get(code);
        
        if(country!=null) {
        	String capital = country.getAttribute("capital");
        	
        	NodeList cities = country.getElementsByTagName("city");
        	
        	for (int i = 0; i < cities.getLength(); i++) {
                Element city = (Element) cities.item(i);
                if (city.getAttribute("id").equals(capital)) {
                    return city.getElementsByTagName("name").item(0).getTextContent().trim();
                }
            }
        }
        return null;
    }
    
    @Operation(summary = "Get country political system by code")
    public String get_country_political_system(String code) {
        Element country = countries_map.get(code.toUpperCase());
        
        if(country!=null) {
        	
        	return country.getAttribute("government");
        
        }
        return null;
    }
    
    @Operation(summary = "Get country population by code")
    public long get_country_pop(String code) {
        Element country = countries_map.get(code);
        
        if(country!=null) {
        	String pop= country.getAttribute("population");
        	
        	if(!pop.isEmpty()) return Long.parseLong(pop);
        	
        	return -5;
        }
        return -5;
    }
    
    
    @Operation(summary = "Get country total area by code")
    public long get_country_total_area(String code) {
    	
    	Element country = countries_map.get(code);
        
        if(country!=null) {
        	String area= country.getAttribute("total_area");
        	
        	if(!area.isEmpty()) return Long.parseLong(area);
        	
        	return -5;
        }
        return -5;
    	
    }
    
    /* RIVER FUNCTIONS*/
    
    @Operation(summary = "Get the countries a river passes by")
    public List<String> get_river_passes_through_countries(String key){
    	
    	List<String> names = new ArrayList<>();
    	
    	Element river = rivers_map.get(key);
    	
    	NodeList located_countries = river.getElementsByTagName("located");
    	
    	for (int i = 0; i < located_countries.getLength(); i++) {
    		Element country = (Element) located_countries.item(i);
    		String  country_id= country.getAttribute("country");
    		
    		String country_name=countries_by_id.get(country_id).getAttribute("name");
    		names.add(country_name);
    	}
    	
    	
    	return names;
    	
    	
    }
    
    
    @Operation(summary = "Get the outfall of a river")
    public String get_river_outfall(String key) {
    	
    	Element river = rivers_map.get(key);
    	
    	NodeList outfall = river.getElementsByTagName("to");
    	
    	for (int i = 0; i < outfall.getLength(); i++) {
    		Element element = (Element) outfall.item(i);

    		
    		String type= element.getAttribute("type");
    		String id = element.getAttribute("water");
    		
    		NodeList outfalls = doc.getElementsByTagName(type);
    		
    		for (int j = 0; j < outfalls.getLength(); j++) {
                Element thing = (Element) outfalls.item(j);
                
                if (thing.getAttribute("id").equals(id)) {
                	return thing.getAttribute("name").trim();
                	
                }
    		
    		}
    	}
    	
    	return null;
    	
    }
    
}