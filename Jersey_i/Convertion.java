package Rest_api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;

@Path("convert")


public class Convertion {
	
	@GET
    @Path("/inch_to_cm")
    @Produces(MediaType.TEXT_PLAIN)
    public String inch_to_cm(@QueryParam("number") double inches) {
        double cm = inches * 2.54;
        return "cm: " + cm;
    }

    @GET
    @Path("/cm_to_inch")
    @Produces(MediaType.TEXT_PLAIN)
    public String cmToInch(@QueryParam("number") double cm) {
        double inches = cm / 2.54;
        return "inches: "+ inches;
    }

}
