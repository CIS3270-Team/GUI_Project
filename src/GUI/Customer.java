package GUI;

public class Customer extends User{
	private String customer_ID;
	
	public Customer() {
		this.setUser(" ");
	}

	public String getCustomer_ID() {
		return customer_ID;
	}

	public void setCustomer_ID(String string) {
		this.customer_ID = string;
	}
}
