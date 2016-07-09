package models.business.publications;

import java.io.Serializable;
import java.util.List;

import businessmodels.Product;

public class Journal extends Product implements Serializable {

    private List<String> subjects;

    public Journal(String title, int borrowDuration, String issn, List<String> subjects) {
        super(issn, title, 0.0, 0.0, borrowDuration);
        this.productId = issn;
        this.subjects = subjects;
    }

    public String getIssn() {
        return this.productId ;
    }

    public List<String> getSubjects() {
        return subjects;
    }

}
