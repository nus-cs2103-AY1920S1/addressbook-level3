package seedu.address.ui;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.*;


public class InformationDisplay extends UiPart<Region> {

	private static final String FXML = "InformationDisplay.fxml";

	public final Person person;

	@FXML
	private AnchorPane displayPane;
	@FXML
	private Label name;
	@FXML
	private Label phone;
	@FXML
	private Label address;
	@FXML
	private Label email;
	@FXML
	private ImageView photo;
	@FXML
	private FlowPane tags;
	@FXML
	private Label attendanceRate;
	@FXML
	private Label performance;

	public static final Person ALICE = new Person(new Name("Alice Pauline"), new Phone("94351253"),
												  new Email("alice@example.com"),
												  new Address("123, Jurong West Ave 6, #08-111"),
												  getTagSet("friends"));

	public InformationDisplay() {
		super(FXML);
		this.person = ALICE;
		name.setText(this.person.getName().fullName);
		phone.setText(this.person.getPhone().value);
		address.setText(this.person.getAddress().value);
		email.setText(this.person.getEmail().value);
		//setText of attendance and performance. Should attendance and performance be tag to a person?
		attendanceRate.setText("0%");
		performance.setText("superb");
	}
}
