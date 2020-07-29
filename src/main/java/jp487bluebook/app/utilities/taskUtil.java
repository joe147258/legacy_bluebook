package jp487bluebook.app.utilities;


public class taskUtil {

    private Boolean hidden;
    private Integer quizId;

    public taskUtil(Boolean hidden, Integer quizId){

        this.hidden = hidden;
        this.quizId = quizId;

    }


    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public int getQuizId() {
        return quizId.intValue();
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }
}