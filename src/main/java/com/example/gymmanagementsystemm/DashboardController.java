package com.example.gymmanagementsystemm;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public Button chat_btn;
    public AnchorPane chat_form;
    public TextField chat_txt_field;
    public Button chat_btnSend;
    public ScrollPane chat_Sc_pane;
    public VBox chat_vbox_msg;
    @FXML
    private TextField coaches_name;

    @FXML
    private ComboBox<String> members_schedule;

    @FXML
    private DatePicker members_startDate;

    @FXML
    private AnchorPane payment_form;

    @FXML
    private TableView<memberData> payment_tableView;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TableColumn<coachData, String> coaches_col_adress;

    @FXML
    private Label payment_total;
    @FXML
    private Label username;
    @FXML
    private Button logout;

    @FXML
    private Label dashboard_NM;

    @FXML
    private Button payment_btn;

    @FXML
    private TableColumn<coachData, String> coaches_col_gender;

    @FXML
    private Label dashboard_NC;

    @FXML
    private Button members_clearBtn;

    @FXML
    private Button coach_btn;

    @FXML
    private TableColumn<coachData,String> coaches_col_status;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AreaChart<?, ?> dashboard_IncomeChart;

    @FXML
    private TableColumn<coachData, String> coaches_col_name;

    @FXML
    private TextField payment_Amount;

    @FXML
    private Button coaches_AddBtn;

    @FXML
    private TextField members_name;

    @FXML
    private TextField members_ID;

    @FXML
    private TextArea coaches_adress;

    @FXML
    private TableColumn<memberData,String> payment_col_name;

    @FXML
    private Button members_btn;

    @FXML
    private TableColumn<memberData,String> members_col_Schedule;

    @FXML
    private TextField coaches_coachID;

    @FXML
    private DatePicker members_EndDate;

    @FXML
    private Label payment_change;

    @FXML
    private TableColumn<memberData,String> members_col_Phone;

    @FXML
    private AnchorPane coaches_form;

    @FXML
    private Button coaches_ResetBtn;

    @FXML
    private TableColumn<memberData,String> payment_col_customerID;

    @FXML
    private ComboBox<String> members_gender;

    @FXML
    private ComboBox<String> payment_CustmoerID;

    @FXML
    private TableColumn<memberData,String> members_col_customerID;

    @FXML
    private TextArea members_adress;

    @FXML
    private TableView<memberData> members_tableview;

    @FXML
    private TableColumn<memberData,String> members_col_Gender;

    @FXML
    private Button members_UpdateBtn;

    @FXML
    private TableColumn<memberData,String> members_col_StartDate;

    @FXML
    private TextField members_phone;

    @FXML
    private ComboBox<String> coaches_gender;

    @FXML
    private TableColumn<memberData,String> members_col_Name;

    @FXML
    private Label dashboard_TI;

    @FXML
    private ComboBox<String> coaches_status;

    @FXML
    private TableColumn<memberData,String> members_col_EndDate;

    @FXML
    private Button close;

    @FXML
    private TableColumn<coachData, String> coaches_col_phone;

    @FXML
    private Button minimize;

    @FXML
    private Button payment_payBtn;

    @FXML
    private TableColumn<memberData,String> members_col_Status;

    @FXML
    private TableColumn<memberData,String> payment_col_status;

    @FXML
    private Button members_DeleteBtn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button coaches_updateBtn;

    @FXML
    private Button coaches_DeleteBtn;

    @FXML
    private AnchorPane members_form;

    @FXML
    private ComboBox<String> members_status;

    @FXML
    private TableColumn<memberData,String> payment_col_endDate;

    @FXML
    private TableColumn<memberData,String> payment_col_startDate;

    @FXML
    private Button members_AddBtn;

    @FXML
    private TableColumn<coachData,String> coaches_col_coachID;

    @FXML
    private ComboBox<String> payment_name;

    @FXML
    private TableView<coachData> coaches_tableView;

    @FXML
    private TextField coaches_phone;

    @FXML
    private TableColumn<memberData,String> members_col_Adress;
    public void emptyFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all blank fields");
        alert.showAndWait();
    }
    private Connection connect;
private PreparedStatement prepare;
private ResultSet result;
private Statement statement;
    private String gender[] = {"Male", "Female", "Others"};
    private String coachStatus[] = {"Active", "Inactive"};

/*  la méthode renvoie la liste d'objets "coachData" remplie
avec toutes les données récupérées depuis la base de données.*/
    public ObservableList<coachData> coachesDataList() {

        ObservableList<coachData> listData = FXCollections.observableArrayList();

        String sql = "select * from coach";
        connect=database.connectionDb();

        try{
            prepare=connect.prepareStatement(sql);
            result=prepare.executeQuery();
            coachData cd;
            while (result.next()){
                    cd=new coachData(result.getInt("id")
                            ,result.getString("coachid"),
                            result.getString("name")
                            ,result.getString("adress"),
                            result.getString("gender")
                             ,result.getInt("phoneNum"),
                            result.getString("status"));
                    listData.add(cd);
            }

        } catch (Exception e) {throw new RuntimeException(e);}

        return listData;
    }

    //Remplir la table avec les données récupérées depuis la base de données.
    private ObservableList<coachData> coachesListData;
    public void coachesShowData() {

        coachesListData = coachesDataList();

        coaches_col_coachID.setCellValueFactory(new PropertyValueFactory<>("coachId"));
        coaches_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        coaches_col_adress.setCellValueFactory(new PropertyValueFactory<>("address"));
        coaches_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        coaches_col_phone.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        coaches_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        coaches_tableView.setItems(coachesListData);



    }
    public void coachesSelect() {
        coachData cd = coaches_tableView.getSelectionModel().getSelectedItem();
        int num = coaches_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }




        coaches_coachID.setText(cd.getCoachId());

        coaches_name.setText(cd.getName());
        coaches_adress.setText(cd.getAddress());

       coaches_gender.setValue(cd.getGender());

        coaches_status.setValue(cd.getStatus());

        coaches_phone.setText(String.valueOf(cd.getPhoneNum()));
    }
    public void coachesAddBtn() {

        String sql = "INSERT INTO coach (coachid,name,adress,gender,phoneNum,status) "
                + "VALUES(?,?,?,?,?,?)";

        connect = database.connectionDb();

        try {

            Alert alert;

            if (coaches_coachID.getText().isEmpty() || coaches_name.getText().isEmpty()
                    || coaches_adress.getText().isEmpty()
                    || coaches_gender.getSelectionModel().getSelectedItem() == null
                    || coaches_phone.getText().isEmpty()
                    || coaches_status.getSelectionModel().getSelectedItem() == null) {
               emptyFields();
            } else {

                String checkData = "select coachid FROM coach WHERE coachid = '"
                        + coaches_coachID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Coach ID: " + coaches_coachID.getText() + " was already taken!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, coaches_coachID.getText());
                    prepare.setString(2, coaches_name.getText());
                    prepare.setString(3, coaches_adress.getText());
                    prepare.setString(4, (String) coaches_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, coaches_phone.getText());
                    prepare.setString(6, (String) coaches_status.getSelectionModel().getSelectedItem());

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO INSERT all DATA
                    prepare.executeUpdate();
                    // TO UPDATE TABLEVIEW
                    coachesShowData();
                    // TO CLEAR ALL FIELDS
                    coachesClearBtn();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void coachesUpdateBtn(){
        Alert alert;
            String sql="Update coach set name='"
                +coaches_name.getText()+"',adress='"
                +coaches_adress.getText()+"',gender='"
                +coaches_gender.getSelectionModel().getSelectedItem()+"',phoneNum='"
                +coaches_phone.getText()+"',status='"
                +coaches_status.getSelectionModel().getSelectedItem()
                +"'where coachid='"+coaches_coachID.getText()+"'";
        connect=database.connectionDb();

        try{
            if(coaches_coachID.getText().isEmpty()||coaches_name.getText().isEmpty()
            ||coaches_adress.getText().isEmpty()||coaches_gender.getSelectionModel().getSelectedItem()==null
            ||coaches_phone.getText().isEmpty()||coaches_status.getSelectionModel().getSelectedItem()==null){
                emptyFields();
            }else{
                prepare=connect.prepareStatement(sql);
                prepare.executeUpdate();
                alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update Coach ID:"+coaches_coachID.getText()+"?");
                Optional<ButtonType>option=alert.showAndWait();

                if(option.get().equals(ButtonType.OK))
                {
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    coachesShowData();
                    coachesClearBtn();
                }
                else{
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled Update!");
                    alert.showAndWait();


                }




            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    public void coachesClearBtn() {
        coaches_coachID.setText("");
        coaches_name.setText("");
        coaches_adress.setText("");
        coaches_gender.getSelectionModel().clearSelection();
        coaches_phone.setText("");
        coaches_status.getSelectionModel().clearSelection();
    }
    public  void coachesDeleteBtn(){
        String sql = "DELETE FROM coach WHERE coachid = '"
                + coaches_coachID.getText() + "'";

        connect = database.connectionDb();

        try {
            Alert alert;
            if (coaches_coachID.getText().isEmpty() || coaches_name.getText().isEmpty()
                    || coaches_adress.getText().isEmpty()
                    || coaches_gender.getSelectionModel().getSelectedItem() == null
                    || coaches_phone.getText().isEmpty()
                    || coaches_status.getSelectionModel().getSelectedItem() == null) {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Coach ID: " + coaches_coachID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);

                    // TO EXECUTE THE QUERY YOU TYPED
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    // TO UPDATE TABLEVIEW
                    coachesShowData();
                    // TO CLEAR ALL FIELDS
                    coachesClearBtn();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled Update!");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public void coachGenderList() {
        List<String> genderList = new ArrayList<>();

        for (String data : gender) {
            genderList.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(genderList);
        coaches_gender.setItems(listData);
    }
    public void coachStatusList() {
        List<String> coachList = new ArrayList<>();

        for (String data : coachStatus) {
            coachList.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(coachList);
        coaches_status.setItems(listData);
    }

    /******************************************/

    private String[] scheduleList = {"9AM - 11AM", "11AM - 1PM", "1PM - 3PM", "3PM - 5PM", "5PM - 7PM"};

    public ObservableList<memberData> membersDataList() {

        ObservableList<memberData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM member";

        connect = database.connectionDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            memberData md;

            while (result.next()) {
                md = new memberData(result.getInt("id"),
                        result.getString("memberId"),
                        result.getString("name"),
                        result.getString("address"),
                        result.getInt("phoneNum"),
                        result.getString("gender"),
                        result.getString("schedule"),
                        result.getDate("startDate"),
                        result.getDate("endDate"),
                        result.getDouble("price"),
                        result.getString("status"));

                listData.add(md);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<memberData> membersListData;
    public void membersShowData() {
        membersListData = membersDataList();

        members_col_customerID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        members_col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        members_col_Adress.setCellValueFactory(new PropertyValueFactory<>("address"));
        members_col_Phone.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        members_col_Gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        members_col_Schedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        members_col_StartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        members_col_EndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        members_col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));

        members_tableview.setItems(membersListData);
    }
    public void membersGender() {
        List<String> genderList = new ArrayList<>();

        for (String data : gender) {
            genderList.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(genderList);
        members_gender.setItems(listData);

    }
    public void membersSchedule() {

        List<String> sl = new ArrayList<>();

        for (String data : scheduleList) {
            sl.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(sl);
        members_schedule.setItems(listData);

    }
    private String memberStatus[] = {"Paid", "Unpaid"};
    public void membersStatus() {

        List<String> ms = new ArrayList<>();

        for (String data : memberStatus) {
            ms.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(memberStatus);
        members_status.setItems(listData);

    }
    public void membersClear() {
        members_ID.setText("");
        members_name.setText("");
        members_adress.setText("");
        members_phone.setText("");
        members_gender.getSelectionModel().clearSelection();
        members_schedule.getSelectionModel().clearSelection();
        members_startDate.setValue(null);
        members_EndDate.setValue(null);
        members_status.getSelectionModel().clearSelection();
    }
    private int totalDay;
    private double price;

    public void membersAddBtn() {

        String sql = "INSERT INTO member (memberId, name, address, phoneNum, gender, schedule, startDate, endDate, price, status) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";

        connect = database.connectionDb();

        try {
            Alert alert;

            if (members_ID.getText().isEmpty() || members_name.getText().isEmpty()
                    || members_phone.getText().isEmpty() || members_adress.getText().isEmpty()
                    || members_gender.getSelectionModel().getSelectedItem() == null
                    || members_schedule.getSelectionModel().getSelectedItem() == null
                    || members_startDate.getValue() == null
                    || members_EndDate.getValue() == null) {
                emptyFields();
            } else {

                String checkData = "SELECT memberId FROM member WHERE memberId = '"
                        + members_ID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Member ID: " + members_ID.getText() + " was already taken!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, members_ID.getText());
                    prepare.setString(2, members_name.getText());
                    prepare.setString(4, members_phone.getText());
                    prepare.setString(3, members_adress.getText());
                    prepare.setString(5, (String) members_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(6, (String) members_schedule.getSelectionModel().getSelectedItem());
                    prepare.setString(7, String.valueOf(members_startDate.getValue()));
                    prepare.setString(8, String.valueOf(members_EndDate.getValue()));

                    totalDay = (int) ChronoUnit.DAYS.between(members_startDate.getValue(), members_EndDate.getValue());

                    price = (totalDay * 10);

                    prepare.setString(9, String.valueOf(price));
                    prepare.setString(10, (String) members_status.getSelectionModel().getSelectedItem());

                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfuly Added!");
                    alert.showAndWait();

                    membersShowData();
                    membersClear();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void membersSelect() {

        memberData md = members_tableview.getSelectionModel().getSelectedItem();
        int num = members_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        members_ID.setText(md.getMemberId());
        members_name.setText(md.getName());
        members_adress.setText(md.getAddress());
        members_phone.setText(String.valueOf(md.getPhoneNum()));
        members_startDate.setValue(LocalDate.parse(String.valueOf(md.getStartDate())));
        members_EndDate.setValue(LocalDate.parse(String.valueOf(md.getEndDate())));
        members_gender.setValue(md.getGender());
        members_schedule.setValue(md.getSchedule());
        members_status.setValue(md.getStatus());

    }

    public void membersUpdate() {

        totalDay = (int) ChronoUnit.DAYS.between(members_startDate.getValue(), members_EndDate.getValue());
        price = (totalDay * 50);

        String sql = "UPDATE member SET name = '"
                + members_name.getText() + "', address = '"
                + members_adress.getText() + "', phoneNum = '"
                + members_phone.getText() + "', gender = '"
                + members_gender.getSelectionModel().getSelectedItem() + "', schedule = '"
                + members_schedule.getSelectionModel().getSelectedItem() + "', startDate = '"
                + members_startDate.getValue() + "', endDate = '"
                + members_EndDate.getValue() + "', price = '"
                + String.valueOf(price) + "', status = '"
                + members_status.getSelectionModel().getSelectedItem() + "' WHERE memberId = '"
                + members_ID.getText() + "'";

        connect = database.connectionDb();

        try {
            Alert alert;

            if (members_ID.getText().isEmpty() || members_name.getText().isEmpty()
                    || members_phone.getText().isEmpty() || members_adress.getText().isEmpty()
                    || members_gender.getSelectionModel().getSelectedItem() == null
                    || members_schedule.getSelectionModel().getSelectedItem() == null
                    || members_startDate.getValue() == null
                    || members_EndDate.getValue() == null) {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Member ID: " + members_ID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfuly Updated!");
                    alert.showAndWait();

                    membersShowData();
                    membersClear();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled Update!!");
                    alert.showAndWait();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void membersDelete() {

        String sql = "DELETE FROM member WHERE memberId = '"
                + members_ID.getText() + "'";

        connect = database.connectionDb();

        try {
            Alert alert;

            if (members_ID.getText().isEmpty() || members_name.getText().isEmpty()
                    || members_phone.getText().isEmpty() || members_adress.getText().isEmpty()
                    || members_gender.getSelectionModel().getSelectedItem() == null
                    || members_schedule.getSelectionModel().getSelectedItem() == null
                    || members_startDate.getValue() == null
                    || members_EndDate.getValue() == null) {
                emptyFields();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Member ID: " + members_ID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfuly Deleted!");
                    alert.showAndWait();

                    membersShowData();
                    membersClear();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled Delete!!");
                    alert.showAndWait();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /******************************Payment*************************************/

    public ObservableList<memberData> paymentDataList() {

        ObservableList<memberData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM member";

        connect = database.connectionDb();

        try {
            memberData md;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                md = new memberData(result.getInt("id"),
                        result.getString("memberId"),
                        result.getString("name"),
                        result.getString("address"),
                        result.getInt("phoneNum"),
                        result.getString("gender"),
                        result.getString("schedule"),
                        result.getDate("startDate"),
                        result.getDate("endDate"),
                        result.getDouble("price"),
                        result.getString("status"));

                listData.add(md);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<memberData> paymentListData;
    public void paymentShowData() {

        paymentListData = paymentDataList();

        payment_col_customerID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        payment_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        payment_col_startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        payment_col_endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        payment_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        payment_tableView.setItems(paymentListData);

    }

    public void paymentMemberId() {


        String sql = "select memberId FROM member WHERE status = 'Unpaid'";

        connect = database.connectionDb();

        try {
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("memberId"));
            }
            payment_CustmoerID.setItems(listData);

                paymentName();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void paymentName() {

        String sql = "SELECT name FROM member WHERE memberId = '"
                + payment_CustmoerID.getSelectionModel().getSelectedItem() + "'";

        connect = database.connectionDb();

        try {
            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                listData.add(result.getString("name"));
            }
            payment_name.setItems(listData);

            paymentDisplayTotal();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double totalP;

    public void displayTotal() {

        String sql = "SELECT price FROM member WHERE name = '"
                + payment_name.getSelectionModel().getSelectedItem() + "' and memberId = '"
                + payment_CustmoerID.getSelectionModel().getSelectedItem() + "'";

        connect = database.connectionDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("price");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void paymentDisplayTotal() {
        displayTotal();
        payment_total.setText("$" + String.valueOf(totalP));
    }
    private double amount;
    private double change;
    public void paymentAmount() {
        displayTotal();

        Alert alert;

        if (payment_Amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();

            payment_Amount.setText("");
        } else {
            amount = Double.parseDouble(payment_Amount.getText());

            if (amount >= totalP) {
                change = (amount - totalP);
                payment_change.setText("$" + String.valueOf(change));

            } else {
                payment_Amount.setText("");
                change = 0;
                amount = 0;
            }
        }
    }

    public void paymentPayBtn() {

        String sql = "UPDATE member SET status = 'Paid' WHERE memberId = '"
                + payment_CustmoerID.getSelectionModel().getSelectedItem() + "'";

        connect = database.connectionDb();

        try {
            Alert alert;
            if (totalP == 0 || change == 0 || payment_Amount.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong :3");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();

                    paymentShowData();
                    paymentClear();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void paymentClear() {
        payment_CustmoerID.getSelectionModel().clearSelection();
        payment_name.getSelectionModel().clearSelection();
        payment_total.setText("$0.0");
        payment_Amount.setText("");
        payment_change.setText("$0.0");

        amount = 0;
        change = 0;
        totalP = 0;
    }

/**********************Dashboard*********************************/
// récupère le nombre total de membres ayant un statut "Paid" dans une base de données
    public void dashboardNM() {

    String sql = "SELECT COUNT(id) FROM member WHERE status = 'Paid'";

    connect = database.connectionDb();

    int nm = 0;

    try {
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        if (result.next()) {
            nm = result.getInt("COUNT(id)");
        }

        dashboard_NM.setText(String.valueOf(nm));

    } catch (Exception e) {
        e.printStackTrace();
    }

}

//récupère le nombre total de coaches ayant un statut "Active"
    public void dashboardTC() {

        String sql = "SELECT COUNT(id) FROM coach WHERE status = 'Active'";

        connect = database.connectionDb();

        int tc = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                tc = result.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(tc));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //calcule la somme totale des prix payés par les membres dont le statut est 'Paid
    public void dashboardTI() {

        String sql = "SELECT SUM(price) FROM member WHERE status = 'Paid'";

        connect = database.connectionDb();

        double ti = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getDouble("SUM(price)");
            }

            dashboard_TI.setText("$" + String.valueOf(ti));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardChart() {

        dashboard_IncomeChart.getData().clear();

        String sql = "SELECT startDate, SUM(price) FROM member WHERE status = 'Paid' GROUP BY startDate ORDER BY TIMESTAMP(startDate) ASC LIMIT 10";

        connect = database.connectionDb();

        XYChart.Series chart = new XYChart.Series();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getDouble(2)));
            }

            dashboard_IncomeChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /************************CHAtServer*******************************/
   private   Server server;

    
    
    /*******************************************************/
    public void displayUsername() {

        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        username.setText(user);

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {

            dashboard_form.setVisible(true);
            chat_form.setVisible(false);

            coaches_form.setVisible(false);
            members_form.setVisible(false);
            payment_form.setVisible(false);

            dashboardNM();
            dashboardTC();
            dashboardTI();
            dashboardChart();

        } else if (event.getSource() == coach_btn) {

            dashboard_form.setVisible(false);
            coaches_form.setVisible(true);
            chat_form.setVisible(false);

            members_form.setVisible(false);
            payment_form.setVisible(false);

            // TO UPDATE WHEN YOU CLICK THE MENU BUTTON LIKE COACHES BUTTON
            coachGenderList();
            coachStatusList();
            coachesShowData();

        } else if (event.getSource() == members_btn) {

            dashboard_form.setVisible(false);
            coaches_form.setVisible(false);
            chat_form.setVisible(false);
            members_form.setVisible(true);
            payment_form.setVisible(false);

            membersShowData();
            membersGender();
            membersSchedule();
            membersStatus();

        } else if (event.getSource() == payment_btn) {

            dashboard_form.setVisible(false);
            coaches_form.setVisible(false);
            members_form.setVisible(false);
            chat_form.setVisible(false);
            payment_form.setVisible(true);

            paymentShowData();
            paymentMemberId();
            paymentName();

        } else if (event.getSource() == chat_btn) {

            dashboard_form.setVisible(false);
            coaches_form.setVisible(false);
            members_form.setVisible(false);
            payment_form.setVisible(false);
            chat_form.setVisible(true);

            /*****************Chat************/
            System.out.println("Lancement Serveur!");

            try{
                server=new Server(new ServerSocket(1234));
            } catch(IOException e){
                e.printStackTrace();
            }

            chat_vbox_msg.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    chat_Sc_pane.setVvalue((Double)t1);

                }
            });

            server.receiveMessageFromClient(chat_vbox_msg);
            chat_btnSend.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String messageTosend= chat_txt_field.getText();
                    if(!messageTosend.isEmpty()){
                        HBox hBox=new HBox();
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                        hBox.setPadding(new Insets(5,5,5,10));
                        Text text=new Text(messageTosend);
                        TextFlow textFlow=new TextFlow(text);
                        textFlow.setStyle("-fx-color:rgb(239,242,255);"+
                                "-fx-background-color:rgb(15,125,242);" +
                                "-fx-background-radius:25px;");
                        textFlow.setPadding(new Insets(5,10,5,10));
                        text.setFill(Color.color(0.934,0.945,0.996));

                        hBox.getChildren().add(textFlow);
                        chat_vbox_msg.getChildren().add(hBox);
                        server.sendMessageToClient(messageTosend);
                        chat_txt_field.clear();

                    }


                }
            });
        }


    }
    private double xOffset=0;
    private double yOffset=0;
    public void logout(){
        try{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType>option=alert.showAndWait();
            if(option.get().equals(ButtonType.OK)){
                Parent root= FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage =new Stage();
                Scene scene=new Scene(root);
                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
                logout.getScene().getWindow().hide();


                root.setOnMousePressed((MouseEvent event) -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                });



            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
   public  void minimize(){
       Stage stage=(Stage) main_form.getScene().getWindow();
       stage.setIconified(true);

   }
   public void close(){
    javafx.application.Platform.exit();
}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        dashboardNM();
        dashboardTC();
        dashboardTI();
        dashboardChart();


        coachGenderList();
        coachStatusList();
        coachesShowData();


        membersShowData();
        membersGender();
        membersSchedule();
        membersStatus();

        paymentShowData();
        paymentMemberId();
        paymentName();






    }
    public static void addLabel(String messageFromClient,VBox vbox){
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));
        Text text=new Text(messageFromClient);
        TextFlow textFlow=new TextFlow(text);
        textFlow.setStyle("-fx-color:rgb(233,233,235)"+
                "-fx-background-radius:20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);

            }
        });
    }
}
