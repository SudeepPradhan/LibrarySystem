package models.business.publications;

import java.io.Serializable;
import java.util.List;

import businessmodels.ProductImpl;
import decorators.ProductDecorator;

public class Journal extends ProductDecorator implements Serializable {

    private List<String> subjects;
    private String productId;

    public Journal(String title, int borrowDuration, String issn, List<String> subjects) {
        super(new ProductImpl(issn, title, 0.0, 0.0, borrowDuration));
        this.productId = issn;
        this.subjects = subjects;
    }

    public String getIssn() {
        return this.productId;
    }

    public List<String> getSubjects() {
        return subjects;
    }

}
