package adcowebsolutions.authentication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import adcowebsolutions.domain.AccountRole;

public abstract class Authenticator {
	
	protected List<GrantedAuthority> createGrantedAuthoritiesList(List<AccountRole> authorities) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		Iterator<AccountRole> i = authorities.iterator();
		while (i.hasNext()) {
			AccountRole role = i.next();
			grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_" + role.getRoleName()));
		}
		return grantedAuthorities;
	}
}
