package TravezyInterface;

public class Ui {
    private int activityLevel = 1;

    public void greet() {
        String logo = " _________  ________  ________  ___      ___ _______   ________      ___    ___ \n" +
                "|\\___   ___\\\\   __  \\|\\   __  \\|\\  \\    /  /|\\  ___ \\ |\\_____  \\    |\\  \\  /  /|\n" +
                "\\|___ \\  \\_\\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\  /  / | \\   __/| \\|___/  /|   \\ \\  \\/  / /\n" +
                "     \\ \\  \\ \\ \\   _  _\\ \\   __  \\ \\  \\/  / / \\ \\  \\_|/__   /  / /    \\ \\    / / \n" +
                "      \\ \\  \\ \\ \\  \\\\  \\\\ \\  \\ \\  \\ \\    / /   \\ \\  \\_|\\ \\ /  /_/__    \\/  /  /  \n" +
                "       \\ \\__\\ \\ \\__\\\\ _\\\\ \\__\\ \\__\\ \\__/ /     \\ \\_______\\\\________\\__/  / /    \n" +
                "        \\|__|  \\|__|\\|__|\\|__|\\|__|\\|__|/       \\|_______|\\|_______|\\___/ /     \n" +
                "                                                                   \\|___|/      \n" +
                "                                                                                \n";

        String welcome = "Welcome to the itinerary planner, please use the helpItinerary command\n" +
                "to view the commands supported by Travezy! \uD83D\uDE0A\n";

        String greetings = printLine() + logo + welcome + printLine();
        System.out.println(greetings);
    }

    void exit() {
        String exit = "      Alrighty! Come back soon to add more exciting activities! \uD83C\uDF04\n";

        String exitMessage = printLine() + exit + printLine();
        System.out.println(exitMessage);
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    void setActivityLevel(int level) {
        activityLevel = level;
    }

    private String printLine() {
        return "\n★ ・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・★\n";
    }
}
