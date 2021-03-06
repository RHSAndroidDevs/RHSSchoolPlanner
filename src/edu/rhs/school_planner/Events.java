package edu.rhs.school_planner;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import edu.rhs.school_planner_adapters.EventsAdapter;
import edu.rhs.school_planner_objects.Event;

public class Events extends Activity {
	private ListView eventsListView;
	private ArrayList<Event> events = new ArrayList<Event>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.events);

		if (events.isEmpty()) {
			getEvents();
		}

		eventsListView = (ListView) findViewById(R.id.LVevents);
		eventsListView.setAdapter(new EventsAdapter(events, this));
	}

	public void getEvents(){
		// Fetches todays school events from the schools website
		try{
			// creates a url object containing the address we want to go to
			URL url = new URL("http://www.southsuburbanconference.org/g5-bin/client.cgi?G5genie=184&school_id=10&XMLCalendar=3");
			// connects to the URL allowing bidirectional data flow meaning we can send it things
			// as well as receive them, however we will just be reading things off this one
			URLConnection connection = url.openConnection();
			// connects us to the resource of the url... in this case the xml
			connection.connect();
			// sets up the input stream and buffered input stream that we will read the xml from
			InputStream is = connection.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			// This is where it gets a bit dicey, but as i understand it DBF allows us access to a parser
			// that produces DOM object trees (a way of reading xml) from xml documents or in this case
			// the stream
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(bis);
			doc.getDocumentElement().normalize();

			// NodeList keeps track of all the nodes or categories in the xml
			// once you have this you can get all the nodes you want by going down the tree
			NodeList activities = doc.getElementsByTagName("item");
			for (int x = 0; x < activities.getLength(); x++) {
				Event e = new Event();
				Node item = activities.item(x);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					Element itemElement = (Element)item;

					// gets the title node turns it into text and sets it to
					// the title in Event e
					//					NodeList titleList = itemElement.getElementsByTagName("title");
					//					Element titleElement = (Element)titleList.item(0);

					// gets the description of the event and sets it to the description
					// in Event e
					NodeList descriptionList = itemElement.getElementsByTagName("description");
					Element descriptionElement = (Element)descriptionList.item(0);

					NodeList textDescriptionList = descriptionElement.getChildNodes();
					e.setTitle(textDescriptionList.item(0).getNodeValue().trim());
				}
				// adds the event to the ArrayList of events
				events.add(e);
				Log.v("info", events.get(events.size()-1).getTitle());
			}
		}
		// in case we fail to make connection or read the data we have an event to fall back on
		catch(Exception e) {
			events.add(new Event("unavailable","check internet connection"));
		}
		Log.v("test",events.get(0).getTitle());
	}

}
