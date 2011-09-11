package adcowebsolutions.enums;

public enum Role {
	USER, CUSTOMER, ADMIN, GUEST;
	
	@Override
	public String toString() {
		return name();
	}

}
