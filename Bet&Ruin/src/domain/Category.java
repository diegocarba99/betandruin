package domain;

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
public class Category {

	@Id
	@XmlID
	private int categoryID;
	private String categoryName;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Event> events;
	private String sportID;
	
	public Category(String categoryName, int categoryID, List<Event> events) {
		super();
		this.categoryName = categoryName;
		this.categoryID = categoryID;
		this.events = events;
	}
	
	public String getName() {
		return categoryName;
	}
	
	public void setName(String name) {
		this.categoryName = name;
	}
	
	public int getIDcode() {
		return categoryID;
	}
	
	public void setIDcode(int iDcode) {
		categoryID = iDcode;
	}
	
	public List<Event> getEvents() {
		return events;
	}
	
	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public String getSportID() {
		return sportID;
	}

	public void setSportID(String sportID) {
		this.sportID = sportID;
	}
	
	public String toString() {
		return "[" + sportID + "|" + categoryName + "]" + ( (events == null)? "events null" : "events not null" );
	}
}
