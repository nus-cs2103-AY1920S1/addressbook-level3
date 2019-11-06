package seedu.address.model;

/**
 * Represents all the Statistics of the System, encapsulated in an onject.
 */
public class Statistics {

    private int totalParticipants;
    private int totalTeams;
    private int totalMentors;

    //Represents number of Mentors who specialises in Environment Subject.
    //Subsequent variable names follow the same format.
    private long envMentors;
    private long socialMentors;
    private long healthMentors;
    private long eduMentors;

    //Represents number of Teams who specialises in Environment Subject.
    //Subsequent variable names follow the same format.
    private long envTeams;
    private long socialTeams;
    private long healthTeams;
    private long eduTeams;


    public Statistics() {
    }

    public long getTotalTeams() {
        return totalTeams;
    }

    public void setTotalTeams(int totalTeams) {
        this.totalTeams = totalTeams;
    }

    public long getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(int totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public long getTotalMentors() {
        return totalMentors;
    }

    public void setTotalMentors(int totalMentors) {
        this.totalMentors = totalMentors;
    }

    public long getEnvMentors() {
        return envMentors;
    }

    public void setEnvMentors(long envMentors) {
        this.envMentors = envMentors;
    }

    public long getSocialMentors() {
        return socialMentors;
    }

    public void setSocialMentors(long socialMentors) {
        this.socialMentors = socialMentors;
    }

    public long getHealthMentors() {
        return healthMentors;
    }

    public void setHealthMentors(long healthMentors) {
        this.healthMentors = healthMentors;
    }

    public long getEduMentors() {
        return eduMentors;
    }

    public void setEduMentors(long eduMentors) {
        this.eduMentors = eduMentors;
    }

    public long getEnvTeams() {
        return envTeams;
    }

    public void setEnvTeams(long envTeams) {
        this.envTeams = envTeams;
    }

    public long getSocialTeams() {
        return socialTeams;
    }

    public void setSocialTeams(long socialTeams) {
        this.socialTeams = socialTeams;
    }

    public long getHealthTeams() {
        return healthTeams;
    }

    public void setHealthTeams(long healthTeams) {
        this.healthTeams = healthTeams;
    }

    public long getEduTeams() {
        return eduTeams;
    }

    public void setEduTeams(long eduTeams) {
        this.eduTeams = eduTeams;
    }

}
