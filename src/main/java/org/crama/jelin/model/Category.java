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
	
	@Column(name="DESCRIPTION", nullable=true)
	private String description;
	
	@Column(name="IS_CATEGORY", nullable=false)
	private boolean isCategory;
	
	@ManyToOne
    @JoinColumn(name="PARENT_ID")
	private Category parent;
		
	@ManyToOne
    @JoinColumn(name="GROUP_ID", nullable=false)
	private Group group;

	public Category()
	{
		
	}

	public Category(int id, String name, String description, boolean category, Category parent, Group group) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isCategory = category;
		this.parent = parent;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCategory() {
		return isCategory;
	}

	public void setIsCategory(boolean category) {
		this.isCategory = category;
	}

	public Category getParent() {
		return this.parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;		
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
