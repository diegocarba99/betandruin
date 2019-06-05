package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Event implements Serializable {
	
	@XmlID
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int eventID;
	private String description; 
	private Date eventDate;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Result> posibleResults;
	private boolean canDraw;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Question> questions;// = new List<Question>();
	private String sportID;
	private int categoryID;

	public Integer getEventID() {
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}

	public String getSportID() {
		return sportID;
	}

	public void setSportID(String sportID) {
		this.sportID = sportID;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public Event() {
		super();
	}
	
	public Event(Integer eventNumber, String description,Date eventDate, List<Result> posibleResults, boolean canDraw, String sportID, int categoryID) {
		super();
		this.eventID = eventNumber;
		this.description = description;
		this.eventDate = eventDate;
		this.posibleResults = posibleResults;
		this.canDraw = canDraw;
		this.sportID = sportID;
		this.categoryID = categoryID;
	}

	public Event(Integer eventNumber, String description,Date eventDate) {
		super();
		this.eventID = eventNumber;
		this.description = description;
		this.eventDate=eventDate;
	}
	
	public Event( String description,Date eventDate) {
		super();
		this.description = description;
		this.eventDate=eventDate;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Integer getEventNumber() {
		return eventID;
	}

	public void setEventNumber(Integer eventNumber) {
		this.eventID = eventNumber;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public List<Result> getPosibleResults() {
		return posibleResults;
	}

	public void setPosibleResults(List<Result> posibleResults) {
		this.posibleResults = posibleResults;
	}

	public boolean isCanDraw() {
		return canDraw;
	}

	public void setCanDraw(boolean canDraw) {
		this.canDraw = canDraw;
	}

	public String toString(){
		return "#"+eventID+" - "+description;
	}
	
	public String toString(int i) {
		return "#" + eventID + " - " + description + " [" + posibleResults.get(i).toString() + "]";
	}
	
	public String toAdminString() {
		return this.getEventID() + "  Desc:" + this.getDescription() + " Category:" + this.getCategoryID() + "  Sport:" + this.getSportID();
	}
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Question addQuestion(String question, float betMinimum)  {
        Question q=new Question(question,betMinimum, this);
        questions.add(q);
        return q;
	}
	
	public boolean deleteQuestion(Question question) {
		for(int i=0; i<questions.size(); i++) 
			if(questions.get(i).getQuestion().equals(question.getQuestion())) {
				questions.remove(i);
				return true;
			}
		return false;
	}

	
	/**
	 * This method checks if the question already exists for that event
	 * 
	 * @param question that needs to be checked if there exists
	 * @return true if the question exists and false in other case
	 */
	public boolean doesQuestionExists(String question)  {	
		for (Question q:this.getQuestions()){
			if (q.getQuestion().compareTo(question)==0)
				return true;
		}
		return false;
	}
			
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventID;
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
		Event other = (Event) obj;
		if (eventID != other.eventID)
			return false;
		return true;
	}

	public void setCategoryID(int catId) {
		this.categoryID=catId;	
	}
	
	
	
	
	
	

}
