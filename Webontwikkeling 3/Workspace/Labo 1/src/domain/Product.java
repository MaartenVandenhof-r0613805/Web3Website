package domain;

public class Product {
	private int productId;
	private String name;
	private String description;
	private double price;
	private int rating;
	
	public Product() {
		
	}
	
	public Product(int productId, String name, String description, double d, int rating) {
		setProductId(productId);
		setName(name);
		setDescription(description);
		setPrice(d);
		setRating(rating);
	}
	public Product(String name, String description, double d) {
		setName(name);
		setDescription(description);
		setPrice(d);
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		if(productId == null || productId < 0) {
			throw new DomainException("Give a valid id");
		} else {
			this.productId = productId;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (name.isEmpty()) {
			throw new DomainException("No name given");
		}
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		if (description == null || description.isEmpty()) {
			throw new DomainException("No description given");
		}
		
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		if (price == null || price<0) {
			throw new DomainException("Give a valid price");
		}
		this.price = price;
	}
	public void setPrice(String price) {
		if (price == null || price.isEmpty()) {
			throw new DomainException("No price given");
		}
		setPrice(Double.valueOf(price));
	}
	public void setRating(int rating) {
		if (rating == 0) {
			this.rating = 0;
			
		} else if(rating < 0 || rating > 100){
			throw new DomainException();
	    }else {
			this.rating = rating;
		}
	}
	
	public int getRating() {
		return this.rating;
	}
	
	@Override
	public String toString(){
		return getName() + ": " + getDescription() + " - " + getPrice();
	}
	
}
