package edu.rhs.school_planner_objects;

public class HomeworkAssignment {
	private String title;
	private String date;
	
	public HomeworkAssignment(String t){
		setTitle(t);
		setDate("");
	}
	public HomeworkAssignment(String t, String d) {
		setTitle(t);
		setDate(d);
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate() {
		return date;
	}
}
