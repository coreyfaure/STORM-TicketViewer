package coreyfaure;

import java.net.MalformedURLException;
import java.net.URL;

public class StaticMapURL {
	public static URL getURLSatellite(Ticket t, String apikey) throws MalformedURLException {
		String urlString = "https://maps.googleapis.com/maps/api/staticmap?center="
				+t.latitude+","+t.longitude
				+"&zoom=18&size=450x200&maptype=satellite&markers=color:red|label:T|"
				+t.latitude+","+t.longitude
				+"&key="+apikey;
		return new URL(urlString);
	}
	public static URL getURLRoadmap(Ticket t, String apikey) throws MalformedURLException {
		String urlString = "https://maps.googleapis.com/maps/api/staticmap?center="
				+t.latitude+","+t.longitude
				+"&zoom=18&size=450x200&maptype=roadmap&markers=color:red|label:T|"
				+t.latitude+","+t.longitude
				+"&key="+apikey;
		return new URL(urlString);
	}
}
