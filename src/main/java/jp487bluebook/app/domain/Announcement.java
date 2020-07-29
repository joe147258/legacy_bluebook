package jp487bluebook.app.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.Date;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;



@Entity
public class Announcement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ann_id")
	@Id
	private int id;
	private String title;
	private String contents;
	private Date date;


	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "class_id")
	private Classes bb_class_ann;



	public Announcement(){
		
	}

	public Announcement(String title, String contents, int Id) {
		this.title = title;
		this.contents = contents;
		date = new Date();
		this.id = Id;

	}

	
	public String getStringDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(date);
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public Date getDate(){

		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

	public Classes getBb_class_ann() {
		return bb_class_ann;
	}

	public void setBb_class_ann(Classes bb_class_ann) {
		this.bb_class_ann = bb_class_ann;
	}




	
}
