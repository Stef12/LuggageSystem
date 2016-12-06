/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesystem;

import java.sql.Statement;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;
import javafx.scene.Group;
import javafx.stage.StageStyle;

/**
 *
 * @author stefa
 */
public class LuggageSystem extends Application {

    Statement stmt;
    ResultSet resultSet;
    
    @Override
    public void start(Stage primaryStage) {

        
        primaryStage.setTitle("JavaFX Welcome");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        TextField usernameTextfield = new TextField();
        grid.add(usernameTextfield, 1, 1);

        Label password = new Label("Password:");
        grid.add(password, 0, 2);

        PasswordField passwordTextfield = new PasswordField();
        grid.add(passwordTextfield, 1, 2);

        Button loginButton = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Login button pressed");

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("Driver loaded");

                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
                    System.out.println("Database connected");
                    
                    initializeDB();

                    stmt = conn.createStatement();
                    
                    String queryString = "select * from luggagesystem.medewerker where username = " + "'" + usernameTextfield.getText() + "'" + "and password = " + "'" + passwordTextfield.getText() + "'";
                    
                    resultSet = stmt.executeQuery(queryString);
                    
                    if (resultSet.next()){
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        
                        System.out.println("username/password = " + username + " / " + password);
                    }
                    else {
                        System.out.println("Username/Password do not match");
                        
                        Stage wrongDialog = new Stage();
                        wrongDialog.initStyle(StageStyle.UTILITY);
                        Scene scene = new Scene(new Group(new Text(25,25,"Username/Password do not match")));
                        wrongDialog.setScene(scene);
                        wrongDialog.show();
                    }
                    /*
                    resultSet = stmt.executeQuery("select * from luggagesystem.medewerker where username = " + "'" + usernameTextfield.getText() + "'" + "and password = " + "'" + passwordTextfield.getText() + "'");
                    
                    while (resultSet.next()){
                        if (resultSet.getString("username") != null && resultSet.getString("password") != null) {
                            String username = resultSet.getString("username");
                            System.out.println("username = " + username);
                            String password = resultSet.getString("password");
                            System.out.println("password = " + password);
                        }
                        else {
                            System.out.println("Username / Password do not match");
                        }
                    }
                    */
                }
                catch (Exception ex){
                    System.err.println(ex.getMessage());
                    
                }
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);

        scene.getStylesheets().add(LuggageSystem.class.getResource("luggagesystem.css").toExternalForm());

        //grid.setGridLinesVisible(true);
        primaryStage.show();
    }

    public void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
            System.out.println("Database connected");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
