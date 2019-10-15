package seedu.address.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import seedu.address.MainApp;
import seedu.address.logic.Logic;

import java.io.IOException;

/**
 * Sets up the controller {@code UserViewController} to dynamically change user views.
 */
public class UserViewMain {
   private static final String FXML = "UserViewMain.fxml";
   private UserViewController userViewController;
   private UserViewNavigator userViewNavigator;
   private Logic logic;

   public UserViewMain(Logic logic) throws IOException {
      this.logic = logic;
      FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/" + FXML));
      fxmlLoader.load();

      userViewController = fxmlLoader.getController();
      assert userViewController != null;

      userViewNavigator = new UserViewNavigator();

      UserViewNavigator.setMainController(userViewController);
   }

   /**
    * Shows the viewer the dashboard.
    * @return the dashboard layout
    */
   public Pane loadDashboard() {
      userViewNavigator.loadDashboard(logic);
      return userViewController.getCurrentView();
   }

   /**
    * Shows the user the list of tasks.
    * @return the task list pane
    */
   public Pane loadTasks() {
      userViewNavigator.loadTaskListView(logic);
      return userViewController.getCurrentView();
   }
}
