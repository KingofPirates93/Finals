/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogMeOn;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Main extends Application {

    Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle( "LogON!" );

        GridPane gridPane = new GridPane();
        gridPane.setAlignment( Pos.CENTER );
        gridPane.setPadding( new Insets( 10, 10, 10, 10 ) );
        gridPane.setVgap( 8 );
        gridPane.setHgap( 10 );

        //login welcome
        Text welcome = new Text( "Please Sign in Below." );
        gridPane.setConstraints( welcome, 0, 0, 2, 1 );

        //Username label
        Label userName = new Label( "Username: " );
        gridPane.setConstraints( userName, 0, 1 );

        //username textfield

        TextField userNameInput = new TextField();
        gridPane.setConstraints( userNameInput, 1, 1 );

        //password label
        Label pass = new Label( "Password: " );
        gridPane.setConstraints( pass, 1, 2 );

        //pass input
        PasswordField passinput = new PasswordField();
        gridPane.setConstraints( passinput, 1, 2 );

        //login BUTTON
        Button loginButton = new Button( "Login" );
        gridPane.setConstraints( loginButton, 1, 4 );


        //NIST AC-8 WARNING
        Text warning = new Text( "Warning:\n" +
                "This system is intended for authorized users. \n" +
                "By using this system you agree to the monitoring and\n" +
                "recording of information entered. \n" +
                "Unauthorized access attempts are strictly monitored\n" +
                "Behavior identified in the Acceptable Use Policy is enforced" );
        gridPane.setConstraints( warning, 0, 5, 3, 2 );

        final Text alert = new Text();
        gridPane.setConstraints( alert, 1, 9 );

        gridPane.getChildren().addAll( welcome, userName, userNameInput, pass, passinput, loginButton, warning );

        //button click action
        loginButton.setOnAction( event -> {

            //authenticate user

            boolean isValid = authenticate( userNameInput.getText(), passinput.getText() );

            //NIST AC-7 logon attempts

            int loginCounter = counter();
            int maxLogin = 3;

            //if statements
            if (loginCounter == maxLogin) {
                gridPane.setVisible( false );
                GridPane gridPane2 = new GridPane();
                gridPane2.setAlignment( Pos.CENTER );
                gridPane2.setPadding( new Insets( 10, 10, 10, 10 ) );
                gridPane2.setVgap( 8 );
                gridPane2.setHgap( 10 );
                Text errorLogin = new Text( "Failed Attempt Logged\n"
                        + "Incorrect Username and Password has been entered 3 times\n"
                        + "Contact Administrator for password reset" );

                errorLogin.setFill( Color.RED );
                gridPane2.setConstraints( errorLogin, 0, 0, 2, 2 );

                gridPane2.getChildren().addAll( errorLogin );
                Scene scene = new Scene( gridPane2, 500, 350 );
                window.setScene( scene );
                window.show();

            } else if (isValid != true && loginCounter < maxLogin) {

                final Text tryAgain = new Text();
                gridPane.setConstraints( tryAgain, 1, 8 );
                tryAgain.setFill( Color.RED );
                tryAgain.setText( "Login Unsuccessful, Please Try Again." );
                maxLogin++;
            } else {

                try {
                    JavaMail.generateAndSendEmail();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                gridPane.setVisible( false );
                GridPane gridPane3 = new GridPane();
                gridPane3.setAlignment( Pos.CENTER );
                gridPane3.setPadding( new Insets( 10, 10, 10, 10 ) );
                gridPane3.setVgap( 8 );
                gridPane3.setHgap( 10 );
                Text enterMFAtxt = new Text( "Thank You " + userNameInput.getText()
                        + "\nEnter your security code from the address provided." );
                gridPane3.setConstraints( enterMFAtxt, 0, 0, 2, 1 );
                Label mfaCode = new Label( "Authentication Code: " );
                gridPane3.setConstraints( mfaCode, 0, 1 );
                TextField mfaINPUT = new TextField();
                gridPane3.setConstraints( mfaINPUT, 0, 2 );
                Button mfaButton = new Button( "Proceed" );
                gridPane3.setConstraints( mfaButton, 0, 4, 2, 1 );

                gridPane3.getChildren().addAll( enterMFAtxt, mfaCode, mfaINPUT, mfaButton );
                Scene scene = new Scene( gridPane3, 500, 350 );
                window.setScene( scene );
                window.show();

                mfaButton.setOnAction( event1 -> {

                    boolean access = MFACode( userNameInput.getText(), mfaINPUT.getText() );
                    if (access == false) {
                        gridPane.setVisible( false );
                        GridPane gridPane4 = new GridPane();
                        gridPane4.setAlignment( Pos.CENTER );
                        gridPane4.setHgap( 10 );
                        gridPane4.setVgap( 10 );
                        Text wrongMFA = new Text( "A Problem Was Encountered!\n" +
                                "The code you entered was incorrect or not entered in time. \n" );
                        wrongMFA.setFill( Color.RED );
                        gridPane4.setConstraints( wrongMFA, 0, 0, 2, 2 );
                        gridPane4.getChildren().addAll( wrongMFA );

                        Scene scenel = new Scene( gridPane4, 500, 400 );
                        window.setScene( scene );
                        window.show();

                    } else {
                        gridPane.setVisible( false );
                        GridPane gridPane5 = new GridPane();
                        gridPane5.setAlignment( Pos.CENTER );
                        // Set gap between the components
                        // Larger numbers mean bigger spaces
                        gridPane5.setHgap( 10 );
                        gridPane5.setVgap( 10 );
                        Text hello = new Text( "Welcome " + userNameInput.getText() + "!\n" );
                        hello.setFill( Color.BLACK );
                        gridPane5.setConstraints( hello, 0, 0, 2, 1 );
                        gridPane5.getChildren().addAll( hello );

                        Scene scene3 = new Scene( gridPane5, 500, 400 );
                        window.setScene( scene3 );
                        window.show();

                    }
                } );
            }
        } );



        Scene scene = new Scene( gridPane, 500, 350 );
        window.setScene( scene );
        window.show();


    }
    //LOGGER
    File filename;
    {
        filename = new File( "src/log.txt" );
    }

    final boolean append = true;
    BufferedWriter writer = null;

    //tracker variables
    Date date = new Date();
    private int track = 0;
    public int attempts;

    public boolean MFACode(String user, String twofnum) {
        boolean accessCode = false;
        if (twofnum.equalsIgnoreCase( JavaMail.getSEND() )) {
            accessCode = true;
        }
        return accessCode;
    }

    private int counter() {
        int plus = track++;
        attempts = plus + 1;
        return plus;
    }

    public static void main(String[] args) {
        launch(args);
    }


    public boolean authenticate(String user, String pword) {
        boolean isValid = false;
        Account acc = new Account();
        if (user.equalsIgnoreCase( acc.getUsername() )
                && pword.equals( acc.getUpass() )) {

            isValid = true;


            try {
                writer = new BufferedWriter( new FileWriter( filename, append ) );
                writer.newLine();
                writer.append( " Successful login Attempt: " + date + " for " + user );
            }
            // print error message if there is one
            catch (IOException io) {
                System.out.println( "File IO Exception" + io.getMessage() );
            }
        } else {
            try {
                writer = new BufferedWriter( new FileWriter( filename, append ) );
                writer.newLine();
                writer.append( "Alert! Invalid login attempt on " + date + " for: " + user + " Attempt Number: " + (attempts + 1) );
            }
            // print error message if there is one
            catch (IOException io) {
                System.out.println( "File IO Exception" + io.getMessage() );
            }
        }
        try {
            if (writer != null) {
                writer.close();
            }
        }
        //print error message if there is one
        catch (IOException io) {
            System.out.println("Issue closing the File." + io.getMessage());
        }
        return isValid;
        }


    }





