package jp487bluebook.app.domain;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


//TODO: Have a list of classQuizAverage, I must now update it when a student starts
//AND when a studnet submits their test. Same as ClassResult.
@Entity
public class ClassQuizAverage {
    public ClassQuizAverage(){

    }

    public ClassQuizAverage(int id, Float result, int quizId){
        this.id = id;
        results.add(result);
        this.quizId = quizId;
        average = 0;
    }
    
    @Id
    @Column(name = "cqid")
    private int id;

    private int quizId;

    private ArrayList<Float> results = new ArrayList<Float>();

    private float average;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_class")
    private Classes relatedClass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public List<Float> getResults() {
        return results;
    }

    public void setResults(ArrayList<Float> result) {
        this.results = result;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }

    private void updateAverage() {
        System.out.println("here"); // try to stop tje print aaa print to be null
        //update average never being called or not working?
        average = 0.0f;
        for (Float f : results){
            average += f.floatValue();
        }
        average = (average  / results.size());
    }
    public void addResult(Float f){
        results.add(f);
        updateAverage();
    }

    public Classes getRelatedClass() {
        return relatedClass;
    }

    public void setRelatedClass(Classes c) {
        this.relatedClass = c;
    }

    public void ammendLatestResult(Float f){
        System.out.println("AMEDING!");
        results.set(results.size() - 1, f);
        updateAverage();
    }

}