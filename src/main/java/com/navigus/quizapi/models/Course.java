package com.navigus.quizapi.models;

import java.util.ArrayList;
import java.util.List;

public class Course {
	
	private int courseid;
	private String courseName;
	private int noc=0;
	private int pass=0;
	private int score=0;
	public int getNoc() {
		return noc;
	}
	public void setNoc(int noc) {
		this.noc = noc;
	}
	private List<Question> questions=new ArrayList<>();
	
	public Course() {
		super();
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public int getPass() {
		return pass;
	}
	public void setPass(int pass) {
		this.pass = pass;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
