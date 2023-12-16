package view;




import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Client;
import models.Message;


/**
 * The App Class.
 * This class is responsible for the graphical user interface.
 * * @author E.A NDAYIRAGIJE
 */
public class App{

	
	/** The header. */
	private VBox header;
	
	/** The body. */
	private VBox body;
	
	/** The sender text field. */
	private TextField senderTextField;
	
	/** The recpt text field. */
	private TextField recptTextField;
	
	/** The subject text field. */
	private TextField subjectTextField;
	
	/** The msg area. */
	private TextArea msgArea;
	
	/** The attach button. */
	private Button attachButton;
	
	/** The send button. */
	private Button sendButton;
	
	/** The host field. */
	private TextField hostField;
	
	/** The port field. */
	private TextField portField;
	
	/** The connect button. */
	private Button connectButton;
	
	/** The landing scene. */
	private Scene landingScene;
	
	/** The mailing scene. */
	private Scene mailingScene;
	
	/** The current scene. */
	private Scene currentScene;
	
	/** The client. */
	private Client client;
	
	/** The message. */
	private Message msg = new Message();
	
	
	
	
	/**
	 * Instantiates a new app and sets the current scene.
	 */
	public App()
	{
		this.landingScene = createLandingScene();
		this.currentScene = this.landingScene;
	}
	
	/**
	 * Creates the landing scene.
	 *
	 * @return the scene
	 */
	public Scene createLandingScene()
	{
		FlowPane layout = new FlowPane();
		VBox container = new VBox();
		
		HBox hostComponent = new HBox();
		Label hostLabel = new Label("Host-name: ");
		HBox.setMargin(hostLabel, new Insets(0, 19, 0, 0));
		this.hostField = new TextField();
		hostComponent.setAlignment(Pos.CENTER_LEFT);
		hostComponent.getChildren().addAll(hostLabel,this.hostField);
		
		HBox portComponent = new HBox();
		Label portLabel = new Label("Port Number: ");
		HBox.setMargin(portLabel, new Insets(0, 10, 0, 0));
		this.portField = new TextField();
		portComponent.setAlignment(Pos.CENTER_LEFT);
		portComponent.getChildren().addAll(portLabel,this.portField);
		
		HBox btnComponent = new HBox();
		this.connectButton = new Button("Connect");
		this.connectButton.setOnAction(e -> {
			if(this.hostField.getText().length() != 0 && this.hostField.getText().length() != 0)
			{
				//Instantiate the client by calling the parameterised constructor and passing a socket that is created based on the input recieved fro
				// the text fields.
				this.client = new Client(makeConnection());
			}
		}
		);
		btnComponent.getChildren().add(this.connectButton);
		btnComponent.setAlignment(Pos.CENTER);
		
		container.setSpacing(10);
		container.getChildren().addAll(hostComponent,portComponent,btnComponent);
		
		layout.getChildren().add(container);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout,560,280);
		
		return scene;
	}
	
	/**
	 * Make connection.
	 *
	 * @return the socket
	 */
	public Socket makeConnection()
	{
		String hostName = this.hostField.getText();
		int port = Integer.parseInt(this.portField.getText());
		try
		{
			Socket connection = new Socket(hostName, port);
			//Alerts the user that a connection has been established successfully.
			Alert connectSuccess = new Alert(AlertType.INFORMATION, "Connected successfully to" +
																	"\nHost: " + hostName +
																	"\nPort: " + port +
																	"\nPress OK to continue");
			connectSuccess.showAndWait();
			switchToMailingScene();
			return connection;
		} catch (UnknownHostException ex) {
			Alert unknownHostException = new Alert(AlertType.ERROR, ex.getMessage() + "\n Host is unknown. Please try again."
					+ "/nOr try host: mail.smtpbucket.com and port: 8025 and search for mail at https://www.smtpbucket.com/");
			unknownHostException.showAndWait();
			ex.printStackTrace();
			return null;
		} catch (IOException ex) {
			Alert generalException = new Alert(AlertType.ERROR, ex.getMessage() + "\nPlease check host name and port then try again."
					+ "\nOr try host: mail.smtpbucket.com and port: 8025 and search for mail at https://www.smtpbucket.com/");
			generalException.showAndWait();
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Creates the mailing scene.
	 *
	 * @return the scene
	 */
	public Scene createMailingScene()
	{ 
		FlowPane layout = new FlowPane();
		layout.setAlignment(Pos.CENTER);
		VBox container = new VBox(10);
		
		this.header = createHeader();
		this.body = createBody();
		
		container.getChildren().addAll(createHeader(),createBody());
		
		container.setAlignment(Pos.TOP_CENTER);
		layout.getChildren().add(container);
		
		Scene scene = new Scene(layout,540,350);
		this.mailingScene = scene;
		this.currentScene = this.mailingScene;
		
		return scene;
	}
	
	/**
	 * Switch to mailing scene.
	 */
	public void switchToMailingScene()
	{
		//Gets the current window(stage) and changes the scene via the setScene method.
		Window window = this.currentScene.getWindow();
		if (window instanceof Stage) {
            Stage s = (Stage) window;
            s.setScene(createMailingScene());
        }
		this.currentScene = this.mailingScene;
	}
	
	/**
	 * Switch to landing scene.
	 *
	 * @param from the previous scene
	 * @param to the new scene
	 */
	public void switchToLandingScene(Scene from , Scene to)
	{
		//Gets the current window(stage) and changes the scene via the setScene method.
		Window window = from.getWindow();
		if (window instanceof Stage) {
            Stage s = (Stage) window;
            s.setScene(to);
        }
		this.currentScene = to;
	}
	
	/**
	 * Creates the header.
	 *
	 * @return the vbox
	 */
	public VBox createHeader() {
		Label sender = new Label("Sender Name: ");
		this.senderTextField = new TextField();
		this.senderTextField.setPrefWidth(400);
		HBox senderComponent = new HBox(10);
		senderComponent.setAlignment(Pos.CENTER_LEFT);
		senderComponent.getChildren().addAll(sender,this.senderTextField);
		Insets margin = new Insets(0,15,0,0);
		HBox.setMargin(sender, margin);
		
		Label recpt = new Label("Recipient Name:");
		this.recptTextField = new TextField();
		this.recptTextField.setPrefWidth(400);
		Insets recptMargin = new Insets(0,7,0,0);
		HBox recptComponent = new HBox(10);
		recptComponent.setAlignment(Pos.CENTER_LEFT);
		recptComponent.getChildren().addAll(recpt,this.recptTextField);
		HBox.setMargin(recpt,recptMargin);
		
		Label subject = new Label("Subject: ");
		this.subjectTextField = new TextField();
		this.subjectTextField.setPrefWidth(400);
		HBox subjectComponent = new HBox();
		subjectComponent.setAlignment(Pos.CENTER_LEFT);
		subjectComponent.getChildren().addAll(subject,this.subjectTextField);
		Insets subjectMargin = new Insets(0,60,0,0);
		HBox.setMargin(subject, subjectMargin);
		
		VBox header = new VBox(5);
		header.getChildren().addAll(senderComponent,recptComponent,subjectComponent);
		return header;
	}
	
	/**
	 * Creates the body.
	 *
	 * @return the v box
	 */
	public VBox createBody() {
		Label message = new Label("Message: ");
		this.msgArea = new TextArea();
		this.msgArea.setPrefWidth(400);
		HBox msgComponent = new HBox();
		msgComponent.setAlignment(Pos.CENTER_LEFT);
		HBox.setMargin(message, new Insets(0,53,0,0));
		msgComponent.getChildren().addAll(message,this.msgArea);
		
		Label btn = new Label("");
		this.sendButton = new Button("Send");
		this.sendButton.setPrefWidth(100);
		this.attachButton = new Button("Attach");
		this.attachButton.setOnAction(e -> {
			attachFile();
		});
		this.attachButton.setPrefWidth(100);
		this.sendButton.setOnAction(e -> {
			sendMessage();
			client.SendMail();
			Alert finished = new Alert(AlertType.CONFIRMATION);
	        finished.setHeaderText("Mail Sent Successfully");
	        finished.setContentText("Press OK to send another message and CANCEL to terminate the program.");
	        finished.showAndWait();
	        if(finished.getResult() == ButtonType.OK)
	        {
	        	switchToLandingScene(this.mailingScene,this.landingScene);
	        }
	        if(finished.getResult() == ButtonType.CANCEL)
	        {
	        	System.exit(0);
	        }
		}
		);
		HBox sendBtnComponent = new HBox();
		sendBtnComponent.getChildren().addAll(btn,this.sendButton);
		sendBtnComponent.setAlignment(Pos.CENTER);
		Insets btnMargin = new Insets(0,0,0,0);
		HBox.setMargin(btn, btnMargin);
		
		HBox attachBtnComponent = new HBox();
		attachBtnComponent.getChildren().addAll(btn,this.attachButton);
		
		HBox btnComponent = new HBox(6);
		btnComponent.getChildren().addAll(attachBtnComponent,sendBtnComponent);
		btnComponent.setAlignment(Pos.CENTER);
		
		VBox body = new VBox(10);
		body.getChildren().addAll(msgComponent,btnComponent);
		return body;
	}
	
	/**
	 * Attach file.
	 */
	private void attachFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Attach a file");
		this.msg.setAttachment(fc.showOpenDialog(this.currentScene.getWindow()));
		this.msg.setHasAttachment(true);
	}

	/**
	 * Send message.
	 */
	public void sendMessage()
	{
		String from = this.senderTextField.getText().replace(" ", "") + "@csc2b.uj.ac.za";
		String to = this.recptTextField.getText().replace(" ", "") + "@csc2b.uj.ac.za";
		String content = this.msgArea.getText();
		String subject = this.subjectTextField.getText();
		
		this.msg.setSenderAddress(from);
		this.msg.setRecptAddress(to);
		this.msg.setMessageContent(content);
		this.msg.setMessageSubject(subject);
		client.setMsg(this.msg);
		
	}
	
	/**
	 * Gets the current scene.
	 *
	 * @return the current scene
	 */
	public Scene getCurrentScene()
	{
		return this.currentScene;
	}
}
