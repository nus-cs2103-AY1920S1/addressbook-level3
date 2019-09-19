package seedu.address.model.module;

public class AcadYear {
    private String acadYear;

    public AcadYear(String acadYear){
        this.acadYear = acadYear;
    }

    @Override
    public String toString(){
        return acadYear;
    }
}
