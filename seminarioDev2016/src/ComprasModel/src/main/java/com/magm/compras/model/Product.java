package com.magm.compras.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Access(value = AccessType.FIELD)
@Table(name = "products")
public class Product implements Serializable {

	private static final long serialVersionUID = -2237359968314973968L;

	private String description;

	@Id
	@GeneratedValue
	private int id;

	@Override
	public boolean equals(Object obj) {
		return ((Product) obj).getId() == getId();
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Product: [%d] %s", getId(), getDescription());
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "productTags", joinColumns = @JoinColumn(name = "idProduct") )
	@Column(name = "tag")
	private List<String> tags = new ArrayList<String>();

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void addTag(String tag) {
		tags.add(tag);
	}

}
