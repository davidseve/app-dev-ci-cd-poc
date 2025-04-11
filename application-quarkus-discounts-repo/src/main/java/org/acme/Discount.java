package org.acme;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;

public class Discount {

    @NotEmpty
    private String name;

    @NotEmpty
    private String price;

    @NotEmpty
    private String discount;
    
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, discount, name, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Discount other = (Discount) obj;
		return Objects.equals(description, other.description) && Objects.equals(discount, other.discount)
				&& Objects.equals(name, other.name) && Objects.equals(price, other.price);
	}

	@Override
	public String toString() {
		return "Discount [name=" + name + ", price=" + price + ", discount=" + discount + ", description=" + description
				+ "]";
	}

    
}
