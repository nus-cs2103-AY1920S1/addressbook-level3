package com.typee.model.report;

import java.nio.file.Path;

import com.typee.model.engagement.Engagement;
import com.typee.model.person.Person;

/**
 * Report class which contains the Engagement
 */
public class Report {
    private Engagement engagement;
    private Path filePath;
    private Person to;
    private Person from;

    public Report(Engagement engagement, Person to, Person from) {
        this.engagement = engagement;
        this.to = to;
        this.from = from;
    }

    public Engagement getEngagement() {
        return engagement;
    }

    public void setEngagement(Engagement engagement) {
        this.engagement = engagement;
    }

    public Person getTo() {
        return to;
    }

    public void setTo(Person to) {
        this.to = to;
    }

    public Person getFrom() {
        return from;
    }

    public void setFrom(Person from) {
        this.from = from;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }
}
