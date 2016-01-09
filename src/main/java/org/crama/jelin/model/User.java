
package org.crama.jelin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.crama.jelin.model.Constants.UserType;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "User")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
	@Column(name = "TYPE", nullable = false)
	private UserType type;
	
	@JsonIgnore
	@Column(name = "NET_STATUS")
	private NetStatus netStatus;
		
	@JsonIgnore
	@Column(name = "PROCESS_STATUS")
	private ProcessStatus processStatus;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "BOT_ID")
	private GameBot bot;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
	
	@JsonIgnore
	@Column(name = "LAST_GAME_TIME", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastGameTime;
	
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

	public GameBot getBot() {
		return bot;
	}

	public void setBot(GameBot bot) {
		this.bot = bot;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public Date getLastGameTime() {
		return lastGameTime;
	}

	public void setLastGameTime(Date lastGameTime) {
		this.lastGameTime = lastGameTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/*@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", netStatus=" + netStatus
				+ ", processStatus=" + processStatus + ", characterSet=" + characterSet + ", choosenCharacter="
				+ choosenCharacter + ", lastGameTime=" + lastGameTime + "]";
	}*/

	
	
}
