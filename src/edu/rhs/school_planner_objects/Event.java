package edu.rhs.school_planner_objects;

public class Event {
	private String title;
	
	public Event(String t, String d) {
		setTitle(t);
	}

	public Event() {
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
