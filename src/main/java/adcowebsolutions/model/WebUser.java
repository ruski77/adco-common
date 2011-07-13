package adcowebsolutions.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Holds user state to be persisted to a data store.
 * 
 * @author Russell Adcock
 */
@SuppressWarnings("serial")
@Entity
@Table(name="users")
public class WebUser implements Serializable {
	
	@Id
	@NotEmpty
	@Length(min=3, max=10, message="{userid_invalid_length}")
	@Column(name="user_id")
	private String id;
	
	@NotEmpty
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@NotEmpty
	@Column(name="password")
	private String password;
	
	@NotEmpty
	@Column(name="email", unique=true)
	@Email(message="{email_invalid}")
	private String email;

	@Column(name="status")
	private String status;
	
	@Column(name="last_login_date")
	private Timestamp lastLoginDate;
	
	@Column(name="registered_date")
	private Timestamp registeredDate;
	
	@Column(name="dob")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Column(name="invalid_login_count")
	private Integer invalidLoginCount;
	
	@Column(name="login_count")
	private Integer loginCount;
	
	@Column(name="comment_count")
	private Integer commentCount;
	
	@Column(name="news_count")
	private Integer newsCount;
	
	@Column(name="blog_count")
	private Integer blogCount;
	
	@Column(name="avatar")
	private byte[] avatar;
	
	@Column(name="avatar_mime_type")
	private String mimeType;
	
	@Column(name="update_password")
	private String updatePasswordFlag;
	
	@Column(name="gender")
	private String gender;

	@OneToMany(mappedBy="user", targetEntity=WebUserRole.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<WebUserRole> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getInvalidLoginCount() {
		return invalidLoginCount;
	}

	public void setInvalidLoginCount(Integer invalidLoginCount) {
		this.invalidLoginCount = invalidLoginCount;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(Integer newsCount) {
		this.newsCount = newsCount;
	}

	public Integer getBlogCount() {
		return blogCount;
	}

	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Timestamp getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Timestamp registeredDate) {
		this.registeredDate = registeredDate;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setUpdatePasswordFlag(String updatePasswordFlag) {
		this.updatePasswordFlag = updatePasswordFlag;
	}

	public String getUpdatePasswordFlag() {
		return updatePasswordFlag;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public List<WebUserRole> getRoles() { 
		return roles; 
	}
	
	public void setRoles(List<WebUserRole> roles) { 
		this.roles = roles; 
	}
	
	public boolean isPersisted() {
		return this.getRegisteredDate() != null;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}
	
	@Override
	public String toString() {
		return "WebUser(" + id + ")";
	}
}
