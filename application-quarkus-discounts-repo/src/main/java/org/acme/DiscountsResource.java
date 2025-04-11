package org.acme;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
// import org.eclipse.microprofile.metrics.annotation.Counted;

@Path("/discounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiscountsResource {
	
	@ConfigProperty(name = "application.version", defaultValue="none") 
	String version;
	
	@ConfigProperty(name = "application.colour", defaultValue="none") 
	String colour;
	
	@ConfigProperty(name = "application.mode", defaultValue="none") 
	String mode;

    private Set<Discount> discounts = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public DiscountsResource(){
        discounts.add(createDiscount());
    }

    @GET
    // @Counted(name = "discounts", description = "count of get discounts")
    public DiscountsResponse discounts() {
    	final DiscountsResponse discountResponse = new DiscountsResponse(discounts, version, colour, mode);
        return discountResponse;
    }

    Discount createDiscount(){
        final Discount apple = new Discount();
        apple.setName("BlackFriday");
        apple.setPrice("1350€");
        apple.setDiscount("10%");
        apple.setDescription("The best discount");
       return apple;
    }
}