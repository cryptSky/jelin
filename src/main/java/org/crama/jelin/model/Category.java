package org.crama.jelin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Category")
public class Category implements Serializable {
	
	private static final long serialVersionUID = -78965874432221433L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="IS_CATEGORY", nullable=false)
	private boolean isCategory;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="PARENT_ID")
	private Category parentObject;
	
	@Transient
	private Integer parent;
	
	@ManyToOne
    @JoinColumn(name="GROUP_ID", nullable=false)
	private Group group;

	public Category()
	{
		
	}

	public Category(int id, String name, boolean category, Category parent, Group group) {
		super();
		this.id = id;
		this.name = name;
		this.isCategory = category;
		if (this.parentObject == null) {
			this.parent = null;
		} else {
			this.parent = parentObject.getId();
		}
		this.group = group;
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

	public boolean isCategory() {
		return isCategory;
	}

	public void setIsCategory(boolean category) {
		this.isCategory = category;
	}

	public Integer getParent() {
		if (this.parentObject == null) {
			return null;
		} else {
			return parentObject.getId();
		}
	}

	public void setParent(Category parent) {
		if (this.parentObject == null) {
			this.parent = null;
		} else {
			this.parentObject = parent;
			this.parent = parent.getId();
		}
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", isCategory=" + isCategory + ", parent=" + parent
				+ ", parentId=" + parent + ", group=" + group + "]";
	}
	
	

}
