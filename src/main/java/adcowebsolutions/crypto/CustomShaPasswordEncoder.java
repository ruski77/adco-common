package adcowebsolutions.crypto;

import java.io.Serializable;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class CustomShaPasswordEncoder extends ShaPasswordEncoder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public CustomShaPasswordEncoder() {
		super(256);
	}
}
