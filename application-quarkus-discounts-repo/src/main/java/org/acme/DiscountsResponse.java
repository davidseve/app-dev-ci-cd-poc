package org.acme;

import java.util.Set;

public class DiscountsResponse{
	
	private Set<Discount> discounts; 
	private Metadata metadata;
	
	public DiscountsResponse() {
		super();
	}
	
	public DiscountsResponse(Set<Discount> discounts, Metadata metadata) {
		super();
		this.discounts = discounts;
		this.metadata = metadata;
	}
	public DiscountsResponse(Set<Discount> discounts, String version, String colour, String mode) {
		super();
		this.discounts = discounts;
		metadata = new Metadata(version, colour, mode);
	}
	

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public Set<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
	}
	
	
}
