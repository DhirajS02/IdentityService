package com.example.identityservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "employee_code", nullable = false, unique = true)
    private String employeeCode;
	@Column(name = "employee_name")
    private String employeeName;
	@Column(name = "mobile_number")
    private String mobileNumber;
	@Column(name = "joining_date")
    private Instant joiningDate;
	@Column(name = "attendence_mode")
    private String attendenceMode;
	@Column(name = "employee_type")
    private String employeeType;
	@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "business_id", nullable = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "business_id"), name = "business_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
    private Business business;
	@Column(name = "is_activated")
	private Boolean isActivated;
	 @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
	
    public void addRole(Role role) {
        this.roles.add(role);
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();
		for(Role role:roles)
		{
			GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(role.getName().toString());
			grantedAuthorities.add(grantedAuthority);
		}
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return mobileNumber;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return isActivated;
	}
}
