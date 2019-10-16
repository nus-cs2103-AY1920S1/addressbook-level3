package seedu.address.autocomplete;

public class test {
    public static void main(String[] args) {
        String testString = "a  b";
        String[] parts = testString.split(" ");
        for (String part : parts) {
            System.out.println(part);
        }
        System.out.println(parts.length);
    }
}
