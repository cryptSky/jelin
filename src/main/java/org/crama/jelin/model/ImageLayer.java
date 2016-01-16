package org.crama.jelin.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "image_layer")
public class ImageLayer implements Serializable {

	private static final long serialVersionUID = 1204595197212859506L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LAYER_ID", nullable=false)
	private int id;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ENHANCER_ID", nullable = true, unique = true)
	private Enhancer enhancer;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CHARACTER_ID", nullable = false)
	private Character character;
	
	@Column(name = "LAYER_NUM", nullable = false)
	private int layerNum;
	
	@Column(name = "IMAGE", nullable = false)
	private String image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Enhancer getEnhancer() {
		return enhancer;
	}

	public void setEnhancer(Enhancer enhancer) {
		this.enhancer = enhancer;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public int getLayerNum() {
		return layerNum;
	}

	public void setLayerNum(int layerNum) {
		this.layerNum = layerNum;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ImageLayer [id=" + id + ", enhancer=" + enhancer + ", character=" + character + ", layerNum=" + layerNum
				+ ", image=" + image + "]";
	}
	
}
