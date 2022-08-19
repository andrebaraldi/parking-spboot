package com.baraldi.parkingspboot.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// UserDetails : Spring enxegará esse modelo que será usado para autenticação
@Entity                    // Nossa classe é uma entidade no banco de dados
@Table(name = "TB_USER")   // Nome que a tabela assumirá na base de dados
public class UserModel implements UserDetails, Serializable {

	@Id  // Indica que é o Id da nossa entidade 
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "UUID") // Auto-incremento no banco de dados
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid", columnDefinition = "char(36)")
	@Type(type = "org.hibernate.type.UUIDCharType")		
	private UUID userID;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	// -------------------------------------------------------------
	// Tabela auxiliar criada da relação de usuário x roles
	// Um Usuário pode ter muitas Roles e vice-versa (ManytoMany)
	// Essa tabela vai relacioar os UUIDs de User x UUIDs de Roles
	@ManyToMany
	@JoinTable (name = "TB_USERS_ROLES"
	           ,joinColumns = @JoinColumn(name = "user_id")
	           ,inverseJoinColumns = @JoinColumn(name="role_id"))			   
	private List<RoleModel> roles;
	// -------------------------------------------------------------
	

	// -------------------------------------------------------------
	// Métodos provenientes da interface UserDetails
	// Usada para autenticação do Spring
	// -------------------------------------------------------------
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { return this.roles;}

	@Override
	public String getPassword() { return this.password; }

	@Override
	public String getUsername() { return this.username;}

	@Override
	public boolean isAccountNonExpired() { return true; }

	@Override
	public boolean isAccountNonLocked() { return true;}

	@Override
	public boolean isCredentialsNonExpired() { return true; }

	@Override
	public boolean isEnabled() { return false; }

	// Get/Set
	// ------------
	public UUID getUserID() { return userID;}
	public void setUserID(UUID userID) { this.userID = userID;}
	public void setUsername(String username) { this.username = username;}
	public void setPassword(String password) { this.password = password; }
		
}
