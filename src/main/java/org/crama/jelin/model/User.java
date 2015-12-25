
package org.crama.jelin.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "User")
public class User implements Serializable {
	
	private static final long serialVersionUID = -3214924530355260911L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="USER_ID", nullable=false)
	private int id;
	
	@Column(name="USERNAME", nullable=false, unique=true)
	private String username;
	
	@Column(name="EMAIL", nullable=false, unique=true)
	private String email;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "NET_STATUS_ID")
	private NetStatus netStatus;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "PROCESS_STATUS_ID")
	private ProcessStatus processStatus;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "UserCharacter", 
    joinColumns = { 
           @JoinColumn(name = "USER_ID")
    }, 
    inverseJoinColumns = { 
           @JoinColumn(name = "CHARACTER_ID")
    })
	private Set<Character> characterSet = new HashSet<Character>();
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER) 
	@JoinColumn(name = "CHARACTER_ID", nullable = true)
	private Character choosenCharacter;
	
	/*@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<GameUser> gamePlayerSet = new HashSet<GameUser>();*/
	
	public User() {}
	
	public User(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}
	
	public User(int id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Character> getCharacterSet() {
		return characterSet;
	}

	public void setCharacterSet(Set<Character> characterSet) {
		this.characterSet = characterSet;
	}

	public Character getChoosenCharacter() {
		return choosenCharacter;
	}

	public void setChoosenCharacter(Character choosenCharacter) {
		this.choosenCharacter = choosenCharacter;
	}

	public NetStatus getNetStatus() {
		return netStatus;
	}

	public void setNetStatus(NetStatus netStatus) {
		this.netStatus = netStatus;
	}

	public ProcessStatus getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(ProcessStatus processStatus) {
		this.processStatus = processStatus;
	}

	/*public Set<GameUser> getGamePlayerSet() {
		return gamePlayerSet;
	}

	public void setGamePlayerSet(Set<GameUser> gamePlayerSet) {
		this.gamePlayerSet = gamePlayerSet;
	}*/
	
	
	
}
