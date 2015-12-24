package org.crama.jelin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ProcessStatus")
public class ProcessStatus {
	public static final String FREE = "free";
	public static final String CALLING = "calling";
	public static final String INVITING = "inviting";
	public static final String WAITING = "waiting";
	public static final String INGAME = "ingame";
	
	@Id
	@GeneratedValue
	@Column(name="ID", nullable=false)
	private int id;
	@Column(name="PROCESS_STATUS", nullable=false)
	private String status;
	
	public ProcessStatus() {
		
	}
	
	public ProcessStatus(String status) {
		super();
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		ProcessStatus other = (ProcessStatus) obj;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
}
