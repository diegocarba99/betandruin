package domain;

import java.util.List;

public class EventContainer {
	
	private Event event;
	private List<Result> results;
	
	public EventContainer() {
		this.event = null;
		this.results = null;
	}
	
	public EventContainer(Event event) {
		this.event = event;
		this.results = event.getPosibleResults();
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
	
	public String toString() {
		String rtrn = "";
		for(int i = 0; i < results.size(); i++) rtrn += results.get(i).toString();
		return event.toString() + " - " + rtrn;
	}

}
