package adcowebsolutions.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "account_role")
@SequenceGenerator(name="account_role_seq",sequenceName="account_role_roleId_seq")
public class AccountRole implements Identifiable<Long>, Serializable {
    private static final long serialVersionUID = 1L;
 
    private Long roleId; // pk
    private Account account; // not null
    private String roleName;

    public AccountRole() {
    }

    public AccountRole(Long primaryKey) {
        setPrimaryKey(primaryKey);
    }

    @Transient
    @XmlTransient
    public Long getPrimaryKey() {
        return getRoleId();
    }

    public void setPrimaryKey(Long roleId) {
        setRoleId(roleId);
    }

    @Transient
    @XmlTransient
    public boolean isPrimaryKeySet() {
        return isRoleIdSet();
    }

    @Column(name = "role_id", nullable = false, unique = true, precision = 20)
    @GeneratedValue(generator="account_role_seq")
    @Id
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Transient
    public boolean isRoleIdSet() {
        return roleId != null;
    }

    /**
     * Helper method to set the roleId attribute via an int.
     * @see #setRoleId(Long)
     */
    public void setRoleId(int roleId) {
        this.roleId = Long.valueOf(roleId);
    }

    @ManyToOne
	@JoinColumn(name = "user_name", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Length(max = 32)
    @Column(name = "role_name", length = 32)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Set the default values.
     */
    public void initDefaultValues() {
    }

    @Override
    public boolean equals(Object accountRole) {
        if (this == accountRole) {
            return true;
        }

        if (!(accountRole instanceof AccountRole)) {
            return false;
        }

        AccountRole other = (AccountRole) accountRole;
        return getRoleId().equals(other.getRoleId());
    }

    @Override
    public int hashCode() {
        int userNameLength =  getAccount() != null ?  getAccount().getUserName().length() : 1;
        return 100/userNameLength;
    }


    /**
     * Construct a readable string representation for this AccountRole instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("accountRole.roleId=[").append(getRoleId()).append("]\n");
        result.append("accountRole.userName=[").append(getAccount().getUserName()).append("]\n");
        result.append("accountRole.roleName=[").append(getRoleName()).append("]\n");
        return result.toString();
    }
}