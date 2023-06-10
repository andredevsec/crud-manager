package model;

import java.util.Date;

public class ServiceOrder {
	private int id;
	private String description;
	private Double valueos;
	private Date start;
	private Date end;
	private Company company;
	
	public ServiceOrder () {
		
	}
	
	public ServiceOrder (int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getValueos() {
		return valueos;
	}

	public void setValue(Double valueos) {
		this.valueos = valueos;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
}
