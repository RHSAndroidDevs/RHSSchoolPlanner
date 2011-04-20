package objects;

public class Event {
	private String title;
	private String description;
	
	public Event(String t, String d) {
		setTitle(t);
		setDescription(d);
	}

	public Event() {
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
