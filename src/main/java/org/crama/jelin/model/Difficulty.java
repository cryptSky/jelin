package org.crama.jelin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Difficulty")
public class Difficulty implements Serializable {

	private static final long serialVersionUID = 512390103989175782L;

	@Id
	@GeneratedValue
	@Column(name = "DIFFICULTY_ID", nullable = false)
	private int id;
	
	@Column(name = "CODE", length = 10, nullable = false)
	private String code;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "LEVEL", nullable = false)
	private int level;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "Difficulty [id=" + id + ", code=" + code + ", name=" + name + ", level=" + level + "]";
	}
	
	
}
