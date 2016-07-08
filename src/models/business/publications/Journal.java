package models.business.publications;

import java.io.Serializable;
import java.util.List;
import models.business.Publication;

public class Journal extends Publication implements Serializable {

    private String issn;
    private List<String> subjects;

    public Journal(String title, int borrowDuration, String issn, List<String> subjects) {
        super(title, borrowDuration);
        this.issn = issn;
        this.subjects = subjects;
    }

    public String getIssn() {
        return issn;
    }

    @Override
    public String getUniqueIdentifier() {
        return issn;
    }

    public List<String> getSubjects() {
        return subjects;
    }

}
