package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DiscountsResourceTest {

    @Test
    public void testList() {
    	DiscountsResponse response = 
    		given()
                .when().get("/discounts")
                .then()
                .statusCode(200)
                .extract()
                .as(DiscountsResponse.class);
//                .body("$.discounts.size()", is(1),
//                        "discounts.name", containsInAnyOrder("BlackFriday"),
//                        "discounts.price", containsInAnyOrder("1350€"),
//                        "discounts.discount", containsInAnyOrder("10%"));
    	
    	
    	assertEquals(response.getDiscounts().size(),1);
    	assertEquals(response.getDiscounts().iterator().next().getName(),"BlackFriday");
    	assertEquals(response.getDiscounts().iterator().next().getPrice(),"1350€");
    	assertEquals(response.getDiscounts().iterator().next().getDiscount(),"10%");
    }

}