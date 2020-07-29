package jp487bluebook.app.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;







@Entity
public class ClassResult implements Serializable {
    @Id
    @Column(name="cr_id")
    private int id;
    private static final long serialVersionUID = 7998602391270911974L;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "related_class")
    private Classes c;

    private float average;
    @Lob
    private ArrayList<Float> results = new ArrayList<Float>();
    @Lob
    private ArrayList<String> dates = new ArrayList<String>();
    @Lob
    private ArrayList<String> names = new ArrayList<String>();

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "average_owner")
    private BluebookUser averageOwner;

    
    


    public ClassResult(float average, int id, String name){
        this.average = average;
        results.add(average);
        this.id = id;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        dates.add(sdf.format(new Date()));
        names.add(name);
    }
    public ClassResult(){

    }
    //this method updates the average from every result in results;
    private void updateAverage() {
        average = 0;
        for (Float f : results){
            average += f.floatValue();
        }
        average = (average  / results.size());
    }

    public void addResult(Float f, Date d, String name){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        dates.add(sdf.format(d));
        names.add(name);
        results.add(f);
        updateAverage();
    }

    public void ammendLatestResult(Float f){
        results.set(results.size() - 1, f);
        updateAverage();
    }


    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }



    public ArrayList<Float> getResults() {
        return results;
    }

    public void setResults(ArrayList<Float> results) {
        this.results = results;
    }


    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BluebookUser getAverageOwner() {
        return averageOwner;
    }

    public void setAverageOwner(BluebookUser averageOwner) {
        this.averageOwner = averageOwner;
    }

    public Classes getC() {
        return c;
    }

    public void setC(Classes c) {
        this.c = c;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }




    

}