package org.acme;

public class Metadata {

	public Metadata() {
		super();
	}

	
	public Metadata(String version, String colour, String mode) {
		super();
		this.version = version;
		this.colour = colour;
		this.mode = mode;
	}

	String version;
	
	String colour;
	
	String mode;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
}
