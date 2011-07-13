package adcowebsolutions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user_role", uniqueConstraints = {@UniqueConstraint(columnNames= {"user_id", "role_name"})})
@SequenceGenerator(name="ur_seq", sequenceName="user_role_id_seq")
public class WebUserRole {
	
	@Id
	@GeneratedValue(generator="ur_seq")
	@Column(name="role_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name="user_id")
	private WebUser user;

	@Column(name="role_name")
	private String roleName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public WebUser getUser() {
		return user;
	}

	public void setUser(WebUser user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return user.getId()+" "+roleName;
	}
}
