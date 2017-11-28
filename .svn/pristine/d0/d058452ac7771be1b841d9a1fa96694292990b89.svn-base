package vtc.game.app.vcoin.vtcpay.model;

/**
 * Created by LuanPV on 3/9/2017.
 */

public class SecretQuestion {
    private int questionId;
    private String questionName;

    public SecretQuestion() {
    }

    public SecretQuestion(String questionName, int questionId) {
        this.questionName = questionName;
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return questionName
     */
    @Override
    public String toString() {
        return questionName;
    }
}


