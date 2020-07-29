package jp487bluebook.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Classes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3941048526618689161L;
	@Id
	@Column(name="class_id")
	int id;
	@Column(name="className")
	String className;
	@Column(name="convenor")
	String convenor;
	@Column(name="description")
	@Lob
	String desc;

	@ManyToMany(mappedBy = "classes", fetch = FetchType.EAGER)
	List<BluebookUser> students = new ArrayList<BluebookUser>();

	@OneToMany(mappedBy = "bb_class_ann", cascade=CascadeType.ALL)
	List<Announcement> announcements = new ArrayList<Announcement>();
	
	@OneToMany(mappedBy = "bb_class", cascade=CascadeType.ALL)
	List<Quizzes> quizzes = new ArrayList<Quizzes>();

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "class_teacher")
	BluebookUser class_teacher = new BluebookUser();
	
	private String classCode;

	//a list of of a class that ontains the quiz ID and an average, copy classreult averge for updating (lsit of floats, etc)
	@OneToMany(mappedBy = "relatedClass", cascade = CascadeType.ALL)
	private List<ClassQuizAverage> averageGrades = new ArrayList<ClassQuizAverage>();
	
	public Classes(){
		
	}

	public String getConvenor() {
		return convenor;
	}


	public void setConvenor(String convenor) {
		this.convenor = convenor;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}

	
	public void delAnnouncement(int id) {
		for(int i = 0; i < announcements.size(); i++) {
			if(announcements.get(i).getId() == id) {
				announcements.remove(i);
			}
		}
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return className;
	}
	public void setName(String name) {
		this.className = name;
	}




	public List<Quizzes> getQuizzes() {
		return quizzes;
	}


	public void setQuizzes(List<Quizzes> quizzes) {
		this.quizzes = quizzes;
	}
	
	public void addQuizzes(Quizzes quiz) {
		quizzes.add(quiz);
	}

	public String getClassCode() {

		return classCode;
	}

	public void setClassCode(String classCode) {
		
		this.classCode = classCode;
	}

	public void genClassCode(){
		this.classCode = class_teacher.getId() + className.substring(0, Math.min(className.length(), 3)).toUpperCase() + id;
	}

	public BluebookUser getClass_teacher() {
		return class_teacher;
	}

	public void setClass_teacher(BluebookUser class_teacher) {
		this.class_teacher = class_teacher;
	}

	public List<BluebookUser> getStudents() {
		return students;
	}

	public void setStudents(List<BluebookUser> students) {
		this.students = students;
	}



	public List<ClassQuizAverage> getAverageGrades() {
		return averageGrades;
	}

	public void setAverageGrades(List<ClassQuizAverage> averageGrades) {
		this.averageGrades = averageGrades;
	}





}