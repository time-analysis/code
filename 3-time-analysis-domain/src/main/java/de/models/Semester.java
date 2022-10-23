package de.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Semester {

	private String name;
	private ArrayList<Lecture> lectures;
	private LocalDate start;
	private LocalDate end;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(ArrayList<Lecture> lectures) {
		this.lectures = lectures;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

}
