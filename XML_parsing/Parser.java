package xml_parser;


import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

	public static void main(String[] args) {
		
		 try {
	            File file = new File("xmldata_mondial-3.0.xml");

	            // create parser
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            
	            // create XPath object
	            XPathFactory xPathFactory = XPathFactory.newInstance();
	            XPath xpath = xPathFactory.newXPath();

	            // parse to tree DOM and normalize the white spaces
	            Document doc = builder.parse(file);
	            doc.getDocumentElement().normalize();

	            // i question
	            Element root=doc.getDocumentElement();
	            System.out.println("Root element: " + root.getNodeName());
	            System.out.println("Root's children: ");
	            
	            NodeList children = root.getChildNodes();
	            
	            for (int i = 0; i < children.getLength(); i++) {
	                Node node = children.item(i);
	                
	                // Only print ELEMENT_NODEs (ignore text nodes, whitespace, etc.)
	                if (node.getNodeType() == Node.ELEMENT_NODE) {
	                    System.out.println(node.getNodeName());
	                }
	            }
	            
	            String europe_id= "f0_119";
	            String asia_id="f0_123";
	            

	            // ii question
	            System.out.println(" European countries:");
	            
	        
	            String expression = String.format(
	                "//country[encompassed/@continent='%s']/name/text()", 
	                europe_id
	            );

	            NodeList european_countries = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
	            
	            for (int i = 0; i < european_countries.getLength(); i++) {
	                System.out.println(european_countries.item(i).getNodeValue().trim());
	            }
	            
	            // iii question
	             expression = String.format(
	                    "//country[encompassed/@continent='%s' and encompassed/@continent='%s']",
	                    europe_id, asia_id
                );

	             NodeList countries = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

                System.out.println("  Countries that belong to both Europe and Asia:");
                for (int i = 0; i < countries.getLength(); i++) {
                	
                    Element country = (Element) countries.item(i);
                    System.out.println("Country_"+i+ ": ");

      
                    NamedNodeMap attributes = country.getAttributes();
                    for (int j = 0; j < attributes.getLength(); j++) {
                        Node attr = attributes.item(j);
                        System.out.println("  " + attr.getNodeName() + " : " + attr.getNodeValue());
                    }
                    System.out.println();
                }
                
                // iv question
                
                List<Map<String, String>> country_list = new ArrayList<>();
                
                NamedNodeMap attrs=null;
                Element country = null;
                
                expression = String.format("//country[encompassed/@continent='%s']", europe_id);
                european_countries = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
                
                for (int i = 0; i < european_countries.getLength(); i++) {
                	               	
                	if (european_countries.item(i).getNodeType() == Node.ELEMENT_NODE) {
                		country = (Element) european_countries.item(i);
             
                		 attrs = country.getAttributes();

				Map<String, String> data = new HashMap<>();
                        
                        	for (int j = 0; j < attrs.getLength(); j++) {
                            		Node attr = attrs.item(j);
                            		data.put(attr.getNodeName(), attr.getNodeValue());
                        	}

                        	country_list.add(data);
                	}
                   
                }
                
                FileWriter output = new FileWriter("./european_countries.json");
                
                //format the JSON with indentation and line breaks, making it human-readable
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json_output = gson.toJson(country_list);
                
                output.write(json_output);
                output.close();
                
                System.out.println("JSON file created: european_countries.json");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}

}
