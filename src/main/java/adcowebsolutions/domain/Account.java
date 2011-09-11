package adcowebsolutions.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "account")
public class Account implements Identifiable<String>, Serializable {
    private static final long serialVersionUID = 1L;

    // Raw attributes
    private String userName; // pk
    private String password; // not null
    private String email; // unique (not null)
    private Boolean enabled;
    private String firstName; // not null
    private String lastName;
    private LocalDateTime lastLoginDate; // not null
    private LocalDateTime registeredDate; // not null
    private LocalDate dateOfBirth;
    private Integer invalidLoginCount;
    private Integer loginCount;
    private Integer commentCount;
    private Integer newsCount;
    private Integer blogCount;
    private String updatePassword;
    private String gender;
    private String avatarMimeType;
    private byte[] avatar;
	private List<AccountRole> roles;

    public Account() {
    }

    public Account(String primaryKey) {
        setPrimaryKey(primaryKey);
    }

    @Transient
    @XmlTransient
    public String getPrimaryKey() {
        return getUserName();
    }

    public void setPrimaryKey(String userName) {
        setUserName(userName);
    }

    @Transient
    @XmlTransient
    public boolean isPrimaryKeySet() {
        return isUserNameSet();
    }

    @NotEmpty
    @Column(name = "user_name", nullable = false, unique = true, length = 10)
    @Length(min=3, max=10, message="{userid_imessagesnvalid_length}")
    @Id
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Transient
    public boolean isUserNameSet() {
        return userName != null && !userName.isEmpty();
    }

    @NotEmpty
    @Length(max = 256)
    @Column(nullable = false, length = 256)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty
    @Length(max = 256)
    @Email(message="{email_invalid}")
    @Column(nullable = false, unique = true, length = 256)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(length = 0)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @NotEmpty
    @Length(max = 32)
    @Column(name = "first_name", nullable = false, length = 32)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Length(max = 32)
    @Column(name = "last_name", length = 32)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "last_login_date", nullable = true, length = 19)
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDateTime")
    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @NotNull
    @Column(name = "registered_date", nullable = false, length = 19)
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDateTime")
    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    @Column(name = "date_of_birth", length = 10)
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "invalid_login_count", precision = 10)
    public Integer getInvalidLoginCount() {
        return invalidLoginCount;
    }

    public void setInvalidLoginCount(Integer invalidLoginCount) {
        this.invalidLoginCount = invalidLoginCount;
    }

    @Column(name = "login_count", precision = 10)
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Column(name = "comment_count", precision = 10)
    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    @Column(name = "news_count", precision = 10)
    public Integer getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Integer newsCount) {
        this.newsCount = newsCount;
    }

    @Column(name = "blog_count", precision = 10)
    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    @Column(name = "update_password", length = 1)
    public String getUpdatePassword() {
        return updatePassword;
    }

    public void setUpdatePassword(String updatePassword) {
        this.updatePassword = updatePassword;
    }

    @NotEmpty(message="{gender_requried}")
    @Column(name = "gender", length = 1)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Length(max = 64)
    @Column(name = "avatar_mime_type", length = 64)
    public String getAvatarMimeType() {
        return avatarMimeType;
    }

    public void setAvatarMimeType(String avatarMimeType) {
        this.avatarMimeType = avatarMimeType;
    }

    @Basic(fetch = FetchType.LAZY)
    @Lob
    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
    
    public void setRoles(List<AccountRole> roles) {
		this.roles = roles;
	}

	@OneToMany(mappedBy="account", targetEntity=AccountRole.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public List<AccountRole> getRoles() {
		return roles;
	}

    /**
     * Set the default values.
     */
    public void initDefaultValues() {
    }

    @Override
    public boolean equals(Object account) {
        if (this == account) {
            return true;
        }

        if (!(account instanceof Account)) {
            return false;
        }

        Account other = (Account) account;

        if (getEmail() == null) {
            if (other.getEmail() != null) {
                return false;
            }
        } else if (!getEmail().equals(other.getEmail())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int emailLength =  getEmail() != null ? getEmail().length() : 1;
        int userNameLength = getUserName() != null ? getUserName().length() : 1;
        return emailLength/userNameLength;
    }

    /**
     * Construct a readable string representation for this Account instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("account.userName=[").append(getUserName()).append("]\n");
        result.append("account.password=[").append(getPassword()).append("]\n");
        result.append("account.email=[").append(getEmail()).append("]\n");
        result.append("account.enabled=[").append(getEnabled()).append("]\n");
        result.append("account.firstName=[").append(getFirstName()).append("]\n");
        result.append("account.lastName=[").append(getLastName()).append("]\n");
        result.append("account.lastLoginDate=[").append(getLastLoginDate()).append("]\n");
        result.append("account.registeredDate=[").append(getRegisteredDate()).append("]\n");
        result.append("account.dateOfBirth=[").append(getDateOfBirth()).append("]\n");
        result.append("account.invalidLoginCount=[").append(getInvalidLoginCount()).append("]\n");
        result.append("account.loginCount=[").append(getLoginCount()).append("]\n");
        result.append("account.commentCount=[").append(getCommentCount()).append("]\n");
        result.append("account.newsCount=[").append(getNewsCount()).append("]\n");
        result.append("account.blogCount=[").append(getBlogCount()).append("]\n");
        result.append("account.updatePassword=[").append(getUpdatePassword()).append("]\n");
        result.append("account.gender=[").append(getGender()).append("]\n");
        result.append("account.avatarMimeType=[").append(getAvatarMimeType()).append("]\n");
        result.append("account.avatar=[").append(getAvatar()).append("]\n");
        result.append("account.roles=[").append(getRoles()).append("]\n");
        return result.toString();
    }
    
    @Transient
    public boolean isPersisted() {
    	return this.getRegisteredDate() != null;
    }
}