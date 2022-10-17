package de.dnb.timeAnalysis.domain;

import java.util.ArrayList;

public class Lecture {

	private String name;
	private Semester semster;
	private ArrayList<Entry> entries;
	private int presence;
	private int study;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Semester getSemster() {
		return semster;
	}

	public void setSemster(Semester semster) {
		this.semster = semster;
	}

	public ArrayList<Entry> getEntries() {
		return entries;
	}

	public void setEntries(ArrayList<Entry> entries) {
		this.entries = entries;
	}

	public void addEntry(Entry entry) {
		this.entries.add(entry);
	}

	public void removeEntry(Entry entry) {
		this.entries.remove(entry);
	}

	public int getPresence() {
		return presence;
	}

	public void setPresence(int presence) {
		this.presence = presence;
	}

	public int getStudy() {
		return study;
	}

	public void setStudy(int study) {
		this.study = study;
	}
}
