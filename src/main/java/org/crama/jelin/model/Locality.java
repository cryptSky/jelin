package org.crama.jelin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Locality")
public class Locality implements Serializable{
	
	private static final long serialVersionUID = -44040775077881L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@ManyToOne
    @JoinColumn(name="REGION_ID")
	private Region region;
	
	public Locality() {}
	
	public Locality(String name, Region region) {
		super();
		this.name = name;
		this.region = region;
	}
	
	public Locality(int id, String name, Region region) {
		super();
		this.id = id;
		this.name = name;
		this.region = region;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
}
