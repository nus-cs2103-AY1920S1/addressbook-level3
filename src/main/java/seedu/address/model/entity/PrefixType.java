package seedu.address.model.entity;

public enum PrefixType {
    P, // Participant
    M, // Mentor
    T, // Team
    I; // Issue

    public static final String MESSAGE_CONSTRAINTS = "Prefix type should be a string of either one of the following values:\n" +
            "P: to indicate Entity is a Participant\n" +
            "M: to indicate Entity is a Mentor\n" +
            "I: to indicate Entity is an Issue\n" +
            "T: to indicate Entity is  a Team\n";

    public String toStorageValue(){
        return this.name();
    }

    public boolean isValidPrefix(String test){
    try{
        PrefixType result = PrefixType.valueOf(test);
        return true;

    } catch (IllegalArgumentException e){
        return false;
    }

    }



}
