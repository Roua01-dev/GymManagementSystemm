package com.example.gymmanagementsystemm;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public AnchorPane main_form;
    public AnchorPane signup_form;
    public PasswordField su_password;
    public TextField su_email;
    public Button su_signupBtn;
    public AnchorPane sub_form;
    public TextField su_username;
    public Button sub_signupBtn;
    public Button sub_LoginBtn;
    public AnchorPane login_form;
    public TextField si_username;
    public Label edit_label;
    public PasswordField si_password;
    public Button btn_close;
    public Button si_loginBtn;
    public FontAwesomeIcon close_icon;

    //DataBase Tools
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;
    private double xOffset = 0;
    private double yOffset = 0;

    public void login(){
    String sql="select * from admin where username=? and password=?" ;
    conn=database.connectionDb();

try {
    st=conn.prepareStatement(sql);
    st.setString(1,si_username.getText());
    st.setString(2,si_password.getText());
    rs=st.executeQuery();

    Alert alert;
    if(si_username.getText().isEmpty() || si_password.getText().isEmpty()){
    alert =new Alert(Alert.AlertType.ERROR) ;
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText("Please fill all blank fields");
    alert.showAndWait();
    }else{
        if(rs.next()){
            data.username=si_username.getText();
        alert =new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully login!");
        alert.showAndWait();
        si_loginBtn.getScene().getWindow().hide();
        Parent root= FXMLLoader.load(HelloApplication.class.getResource("dashboard.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();



    }else{
            alert =new Alert(Alert.AlertType.ERROR) ;
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Wrong Username/Password");
            alert.showAndWait();
        }
    }






} catch (Exception e) {
    throw new RuntimeException(e);
}


    }
    public void signup(){
        String sql="insert into admin (email,username,password)values(?,?,?)" ;
        conn=database.connectionDb();

        try {
            Alert alert;
            if(su_email.getText().isEmpty()||su_password.getText().isEmpty()||su_username.getText().isEmpty())  {
            alert=new Alert(Alert.AlertType.ERROR)    ;
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("please fill all blank fields");
            alert.showAndWait();
            }   else{
                if(su_password.getText().length()<8)   {
                    alert=new Alert(Alert.AlertType.ERROR)    ;
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);

                    alert.setContentText("Invalid password :3");
                    alert.showAndWait();
                }
                else{
                    st=conn.prepareStatement(sql);
                    st.setString(1,su_email.getText());
                    st.setString(2,su_username.getText());
                    st.setString(3,su_password.getText());
                    alert=new Alert(Alert.AlertType.INFORMATION)    ;
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);

                    alert.setContentText("Successfully create new account ");
                    alert.showAndWait();
                    st.executeUpdate();
                    su_password.setText("");
                    su_email.setText("");
                    su_username.setText("");



                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    public void close(){
      javafx.application.Platform.exit();
    }

    public void signupSlider(){
        TranslateTransition slider1=new TranslateTransition();
        slider1.setNode(sub_form);
        slider1.setToX(300);
        slider1.setDuration(Duration.seconds(.5));
        slider1.play();
        slider1.setOnFinished((ActionEvent e)->{
        edit_label.setText("Login Account");
        sub_signupBtn.setVisible(false);
        sub_LoginBtn.setVisible(true);
        signup_form.setVisible(true);
        close_icon.setFill(Color.valueOf("#FFF"));


        });

    }


    public void loginSlider(){
        TranslateTransition slider1=new TranslateTransition();
        slider1.setNode(sub_form);
        slider1.setToX(0);
        slider1.setDuration(Duration.seconds(.5));
        slider1.play();

        slider1.setOnFinished((ActionEvent e)->{
            edit_label.setText("Create Account");
            sub_signupBtn.setVisible(true);
            sub_LoginBtn.setVisible(false);
            close_icon.setFill(Color.valueOf("#000"));


        });

    }







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}