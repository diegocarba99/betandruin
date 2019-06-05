package domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Sport {

	@Id
	@XmlID
	private String sportID;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Category> categories;
	
	public Sport(String sportID, List<Category> categories) {
		super();
		this.sportID = sportID;
		this.categories = categories;
	}

	public String getIDcode() {
		return sportID;
	}
	
	public void setIDcode(String iDcode) {
		sportID = iDcode;
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public void addCategory( Category c) {
		if ( categories == null) categories = new LinkedList<Category>();
		categories.add(c);
	}

	public boolean hasCategory(int categoryId) {
		for (Category c : categories) {
			if( c.getIDcode() == categoryId) return true;
		}
		return false;
	}
	
	
}