package com.baraldi.parkingspboot.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import com.baraldi.parkingspboot.enums.RoleName;


@Entity                    // Nossa classe é uma entidade no banco de dados
@Table(name = "TB_ROLE")   // Nome que a tabela assumirá na base de dados
public class RoleModel implements GrantedAuthority, Serializable {

	// Versão do Serializable
	private static final long serialVersionUID = 1L;
			
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID") // Auto-incremento no banco de dados
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid", columnDefinition = "char(36)")
	@Type(type = "org.hibernate.type.UUIDCharType")
	private UUID roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true)
	private RoleName roleName;

	
	// --------------------------------
	// Método da GrantedAuthority
	// --------------------------------
	@Override
	public String getAuthority() { 
		return this.roleName.toString();
	}

	
	// Get/Set
	// ----------
	public UUID getRoleId() { return roleId; }
	public void setRoleId(UUID roleId) { this.roleId = roleId;}

	public RoleName getRoleName() { return roleName;}
	public void setRoleName(RoleName roleName) { this.roleName = roleName;}
	
}
