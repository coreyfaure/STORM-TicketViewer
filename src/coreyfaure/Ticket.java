package coreyfaure;

public class Ticket {
	public String control;
	public String before;
	public String ticket;
	public String date;
	public String project;
	public String originTime;
	public String qaMonitor;
	public String truck;
	public String sub;
	public String sub2;
	public String owner;
	public String type;
	public String size;
	public String latitude;
	public String longitude;
	public String voided;
	Ticket (String control, String before, String ticket, String date, String project, 
			String originTime, String qaMonitor, String truck, String sub, String sub2,
			String owner, String type, String size, String latitude, String longitude, String voided){
		this.control = control;
		this.before = before;
		this.ticket = ticket;
		this.date = date;
		this.project = project;
		this.originTime = originTime;
		this.qaMonitor = qaMonitor;
		this.truck = truck;
		this.sub = sub;
		this.sub2 = sub2;
		this.owner = owner;
		this.type = type;
		this.size = size;
		this.latitude = latitude;
		this.longitude = longitude;
		this.voided = voided;
	}
	
	static Ticket getNullTicket() {
		return new Ticket("N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");
		
	}
	
}
