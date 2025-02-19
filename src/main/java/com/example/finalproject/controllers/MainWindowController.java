package com.example.finalproject.controllers;

import com.example.finalproject.Main;
import com.example.finalproject.models.Banker;
import com.example.finalproject.utils.MySQLConnector;
import com.example.finalproject.utils.Storing;
import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import com.opencsv.CSVReader;
import javafx.stage.Stage;


public class MainWindowController implements Initializable {
    @FXML
    private Label info_dtb;
    @FXML
    private TextField warehouse_input,truck_input,employee_input,amount_input;

    @FXML
    private TextField wh_reqinput,truck_reqinput,emp_reqinput,amount_reqinput,P_reqinput;
    @FXML
    private GridPane allocation_arr,max_arr,need_arr,work_arr,titlepane,safeSeq_pane;
    @FXML
    private Label warningtext;
    @FXML
    private Label warehouse_showing,truck_showing,employee_showing,amount_showing,RSStext,Processtext;

    @FXML
    private Button setupBtn;
    @FXML
    private Button logout;



    @FXML
    private MenuItem exportItem;

    @FXML
    private MenuItem importItem;
    private int P;
    private int R=4;
    int allocation[][];
    int max[][];
    int need_array[][];
    int[][] work_array;
    int available[];
    int safe_lst[];
    int importID[];


    @FXML
    private void p_title(int[] data){
        titlepane.getChildren().clear();
        try{
            for(int row=1;row<=data.length;++row){
                Label label =new Label();
                label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
//                label.setPadding(new Insets(10));
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.setStyle("-fx-background-color: lightblue; -fx-alignment: center;");
                label.setText("Order "+OrderIDImport()[row-1]);
                titlepane.add(label,0,row);
                GridPane.setHgrow(label, Priority.ALWAYS); // Allow the button to grow horizontally
                GridPane.setVgrow(label, Priority.ALWAYS); // Allow the button to grow vertically
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);

            }

        }
        catch(NumberFormatException e){
            System.out.println(e);

        }
    }
    @FXML
    private void sort_lst(int[]arr ,boolean check){
        safeSeq_pane.getChildren().clear();
        try{
            for(int row=1;row<= arr.length;row++){
                Label label =new Label();
                label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
//                label.setPadding(new Insets(10));
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.setStyle("-fx-background-color: lightblue; -fx-alignment: center;");
                if(check){
                label.setText("Order "+arr[row-1]);}
                else{label.setText("X");}
                safeSeq_pane.add(label,0,row);
                GridPane.setHgrow(label, Priority.ALWAYS); // Allow the button to grow horizontally
                GridPane.setVgrow(label, Priority.ALWAYS); // Allow the button to grow vertically
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);

            }

        }
        catch(NumberFormatException e){
            System.out.println(e);

        }

    }

    @FXML
    private void array_order(GridPane grid,int rows,int cols,int [][] array,int[] order,String checkme,boolean check) {
        grid.getChildren().clear(); // Clear existing elements if any

        try {
            String title[]={"Order","Warehouse","Truck","Employee","Amount"};
            for(int col=0;col<cols+1;col++){
                Label label =new Label();
                label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
//                label.setPadding(new Insets(10));
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.setStyle("-fx-background-color: lightblue; -fx-alignment: center;");

                label.setText(title[col]);
                grid.add(label,col,0);
            }
            for (int i = 1; i <= rows; i++) {
                    Label label = new Label();
                    label.setTextFill(Color.DARKBLUE); // Set text color
                    label.setFont(Font.font("Arial", FontWeight.BOLD, 12)); // Set font
                    label.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,
                            CornerRadii.EMPTY, Insets.EMPTY))); // Set background color
                    label.setStyle("-fx-alignment: center;");
                    if (checkme.equals("False")){
                    label.setText(Integer.toString(order[i-1]));}
                    else{
                        if(check){
                            label.setText(Integer.toString(order[i-1]));}

                        else{label.setText("X");}
                    }
                    grid.add(label, 0, i);
                    GridPane.setHgrow(label, Priority.ALWAYS); // Allow the button to grow horizontally
                    GridPane.setVgrow(label, Priority.ALWAYS); // Allow the button to grow vertically
                    GridPane.setHalignment(label, HPos.CENTER);
                    GridPane.setValignment(label, VPos.CENTER);
                }
            for (int i = 1; i <= rows; i++) {
                for (int j = 1; j <= cols; j++) {
                    Label label = new Label();
                    label.setTextFill(Color.DARKBLUE); // Set text color
                    label.setFont(Font.font("Arial", FontWeight.BOLD, 12)); // Set font
                    label.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,
                            CornerRadii.EMPTY, Insets.EMPTY))); // Set background color
                    label.setStyle("-fx-alignment: center;");
                    label.setText(Integer.toString(array[i-1][j-1]));
                    grid.add(label, j, i);
                    GridPane.setHgrow(label, Priority.ALWAYS); // Allow the button to grow horizontally
                    GridPane.setVgrow(label, Priority.ALWAYS); // Allow the button to grow vertically
                    GridPane.setHalignment(label, HPos.CENTER);
                    GridPane.setValignment(label, VPos.CENTER);
                }
            }
        } catch (NumberFormatException e) {
            // Handle invalid input
            System.out.println("Invalid input. Please enter valid numbers.");
        }
    }

    @FXML
    private void format_array(GridPane grid,int rows,int cols,int [][] array) {
        grid.getChildren().clear(); // Clear existing elements if any

        try {
            String title[]={"Warehouse","Truck","Employee","Amount"};
            for(int col=0;col<cols;col++){
                Label label =new Label();
                label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
//                label.setPadding(new Insets(10));
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.setStyle("-fx-background-color: lightblue; -fx-alignment: center;");

                label.setText(title[col]);
                grid.add(label,col,0);
            }
            for (int i = 1; i <= rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Label label = new Label();
                    label.setTextFill(Color.DARKBLUE); // Set text color
                    label.setFont(Font.font("Arial", FontWeight.BOLD, 12)); // Set font
                    label.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,
                            CornerRadii.EMPTY, Insets.EMPTY))); // Set background color
                    label.setStyle("-fx-alignment: center;");
                    label.setText(Integer.toString(array[i-1][j]));
                    grid.add(label, j, i);
                    GridPane.setHgrow(label, Priority.ALWAYS); // Allow the button to grow horizontally
                    GridPane.setVgrow(label, Priority.ALWAYS); // Allow the button to grow vertically
                    GridPane.setHalignment(label, HPos.CENTER);
                    GridPane.setValignment(label, VPos.CENTER);
                }
            }
        } catch (NumberFormatException e) {
            // Handle invalid input
            System.out.println("Invalid input. Please enter valid numbers.");
        }
    }
    //FUNCTION ĐỂ KHỞI TẠO GRID

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        logout.setOnAction(event -> {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-window.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 1000, 700);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Login!");
            stage.setScene(scene);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            //xoa username + password
            Storing.putValueToPreferences("username", "");
            Storing.putValueToPreferences("password", "");
        });

        //Câu lêệnh tạm thời kết nối sql
        MySQLConnector mySQLConnector=MySQLConnector.getInstance();
        mySQLConnector.Connect("root", "123456hn");

        //Nhap file vao he thong
        importItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            // Đặt tiêu đề cho cửa sổ chọn tệp (tùy chọn)
            fileChooser.setTitle("Choose a File");

            // Hiển thị cửa sổ chọn tệp và lấy tệp được chọn
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                info_dtb.setText(filePath);
                importToMySQL(filePath, "logistic");
            }
        });
        exportItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            // Đặt tiêu đề cho cửa sổ chọn tệp (tùy chọn)
            fileChooser.setTitle("Save File");

            // Hiển thị cửa sổ lưu tệp và lấy tệp được chọn
            File savedFile = fileChooser.showSaveDialog(null);

            if (savedFile != null) {
                String filePath = savedFile.getAbsolutePath();
                String absolutePath = savedFile.getAbsolutePath();
                String parentDirectory = savedFile.getParent(); // Đây là thư mục chứa tệp
                System.out.println(parentDirectory + absolutePath + absolutePath + filePath);
                exportFromMySQL(filePath, "exportFile");
            }
        });


        //available
        //Nhập tt database, nhấn nút, sau đó lấy thông tin
        setupBtn.setOnAction(event -> {
            int warehouse_available = Integer.parseInt(warehouse_input.getText());
            int truck_available = Integer.parseInt(truck_input.getText());
            int employee_available=Integer.parseInt(employee_input.getText());
            int amount_available=Integer.parseInt(amount_input.getText());

            int warehouse_request = Integer.parseInt(wh_reqinput.getText());
            int truck_request = Integer.parseInt(truck_reqinput.getText());
            int employee_request=Integer.parseInt(emp_reqinput.getText());
            int amount_request=Integer.parseInt(amount_reqinput.getText());

            int P_request=Integer.parseInt(P_reqinput.getText());

            String in4_dtb="logistic";
            //Có được giá trị P,R
            P =Processes(in4_dtb);
            R=4;
            Processtext.setText(Integer.toString(P));
            RSStext.setText(Integer.toString(R));
            //In ra danh sách đơn hàng
            importID=new int[P];
            importID=OrderIDImport();
            for(int i=0;i<importID.length;i++){
                System.out.println(importID[i]);

            }
//            p_title(importID);

            //Khởi tạo allocation và hiển thị giá trị allocation
            allocation=new int[P][R];
            allocation=Allocation(in4_dtb);
            array_order(allocation_arr,P,R,allocation,importID,"False",false);

            //Khởi tạo Max
            max=new int[P][R];
            max=Max(in4_dtb);
            format_array(max_arr,P,R,max);

            //Khởi tạo Available
            int available[]={warehouse_available,truck_available,employee_available,amount_available};

            //Khởi tạo request
            int request[]={warehouse_request,truck_request,employee_request,amount_request};

            Banker deadlock2=new Banker(P,P_request,work_array,safe_lst);
            boolean check=deadlock2.isSafe(available,max,allocation,request);
            if(check){
                warningtext.setText("System is in safe state");

            }
            else {
                warningtext.setText("System is not in safe state");

            }

            //in kq available ra
            warehouse_showing.setText(warehouse_input.getText());
            truck_showing.setText(truck_input.getText());
            employee_showing.setText(employee_input.getText());
            amount_showing.setText(amount_input.getText());

            //IN kq work ra
            work_array=deadlock2.getWork_array();
            format_array(work_arr,P,R,work_array);
            printArr(work_array,P,R);

            // khoi tao in kq need ra
            System.out.println("------");
            need_array = new int[P][R];
            need_array=deadlock2.calculateNeed(need_array,max,allocation);
            format_array(need_arr,P,R,need_array);
//            printArr(need_array,P,R);

            //In ra thứ tự đơn hàng
            safe_lst=new int[P];
            safe_lst=deadlock2.getSafeSeq();
//            sort_lst(safe_lst,check);
            array_order(work_arr,P,R,work_array,safe_lst,"True",check);

            inputData_exportFile(safe_lst);
        });
    }
        public static void importToMySQL(String filePath, String tableName) {
            try (CSVReader reader = new CSVReader(new FileReader(filePath));
                 Connection connection = MySQLConnector.getConnection())
            {

                // Đọc dòng đầu tiên chứa tên cột
                String[] headers = reader.readNext();
                if (headers == null) {
                    throw new IllegalArgumentException("File CSV trống!");
                }

                // Tạo câu truy vấn SQL dựa trên tên cột
                StringJoiner columnNames = new StringJoiner(", ", "(", ")");
                StringJoiner placeholders = new StringJoiner(", ", "(", ")");
                for (String header : headers) {
                    columnNames.add(header);
                    placeholders.add("?");
                }
                String clear_data="DELETE FROM "+ tableName+" WHERE Warehouse REGEXP '^-?[0-9]+$' ";
                System.out.println(clear_data);
                PreparedStatement a = connection.prepareStatement(clear_data);
                a.executeUpdate();




                String insertQuery = "INSERT INTO " + tableName + columnNames + " VALUES " + placeholders;
                System.out.println(insertQuery);
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                // Đọc và chèn dữ liệu từ các dòng tiếp theo
                String[] line;
                while ((line = reader.readNext()) != null) {
                    for (int i = 0; i < line.length; i++) {
                        preparedStatement.setString(i + 1, line[i]);
                    }
                    try {

                        preparedStatement.executeUpdate();
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static void exportFromMySQL(String filePath,String data) {
            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }
            try (CSVWriter writer = new CSVWriter(new FileWriter(filePath));
                 Connection connection = MySQLConnector.getConnection()) {

                String[] header={"Order ID","Warehouse","Truck","Employee","Amount","Max_Warehouse","Max_Trucks","Max_Employee","Max_Order"};
                writer.writeNext(header);
                String selectQuery = "SELECT * FROM "+data;
                System.out.println(selectQuery);
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String[] line = {resultSet.getString(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9)};
                    writer.writeNext(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    int Processes(String datanameinput){
        MySQLConnector mySQLConnector = MySQLConnector.getInstance();
        try{
            String sql="SELECT COUNT(*) FROM "+ datanameinput;
            System.out.println(sql);
            ResultSet resultSet = mySQLConnector.queryResults(sql);
            if(resultSet.next()){
                return resultSet.getInt(1);
            }

        }catch (Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
    int[][] Allocation(String datanameinput){
        MySQLConnector mySQLConnector = MySQLConnector.getInstance();
        try{
            String sql="SELECT Warehouse,Truck,Employee,Amount FROM "+ datanameinput;
            System.out.println(sql);
            ResultSet resultSet = mySQLConnector.queryResults(sql);
            int hang=0;
            while(resultSet.next()){
                int warehouses=resultSet.getInt(1);
                int trucks = resultSet.getInt(2);
                int employees=resultSet.getInt(3);
                int amount=resultSet.getInt(4);
                allocation[hang][0]=warehouses;
                allocation[hang][1]=trucks;
                allocation[hang][2]=employees;
                allocation[hang][3]=amount;


                hang++;

            }

        }catch (Exception ex){
            System.out.println(ex);
        }
        return allocation;
    }
    void printArr(int [][] arr,int row,int col){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                System.out.print(arr[i][j]);
            }System.out.println("");
        }
    }

    int[][] Max(String datanameinput){
        MySQLConnector mySQLConnector = MySQLConnector.getInstance();
        try{
            String sql="SELECT Max_Warehouse,Max_Trucks,Max_Employees,Max_Orders FROM "+ datanameinput;
            System.out.println(sql);
            ResultSet resultSet = mySQLConnector.queryResults(sql);
            int hang=0;
            while(resultSet.next()){
                int warehouses=resultSet.getInt(1);
                int trucks = resultSet.getInt(2);
                int employees=resultSet.getInt(3);
                int amount=resultSet.getInt(4);
                max[hang][0]=warehouses;
                max[hang][1]=trucks;
                max[hang][2]=employees;
                max[hang][3]=amount;

                hang++;

            }

        }catch (Exception ex){
            System.out.println(ex);
        }
        return max;
    }

    int [] OrderIDImport(){
        MySQLConnector mySQLConnector = MySQLConnector.getInstance();
        try{
            String sql="SELECT OrderID FROM logistic";
            System.out.println(sql);
            ResultSet resultSet = mySQLConnector.queryResults(sql);
            int hang=0;
            while(resultSet.next()){
                int ID= resultSet.getInt(1);
                System.out.println(hang+""+ID);
                importID[hang]=ID;
                hang++;
            }        return importID;


        }catch (Exception ex){
            System.out.println(ex);
        }
        return importID;
    }

    void deleteData_exportFile(){
        MySQLConnector mySQLConnector = MySQLConnector.getInstance();
        try{
            String sql =  "DELETE FROM exportfile";
            System.out.println(sql);
            mySQLConnector.queryUpdate(sql);
        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    void inputID_exportFile(int[] dataID){
        try {
            MySQLConnector connector = MySQLConnector.getInstance();
            for (int i = 0; i < dataID.length; i++) {
                String sql= """
                        INSERT INTO exportfile(OrderID)
                        VALUES (%s)
                        """.formatted(dataID[i]);
                connector.queryUpdate(sql);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    void inputData_exportFile(int[] IDlst){
        MySQLConnector mySQLConnector = MySQLConnector.getInstance();
        deleteData_exportFile();
        inputID_exportFile(IDlst);

        try{
            String sql= """
                    SELECT l.Warehouse, l.Truck, l.Employee, l.Amount, l.Max_Warehouse, l.Max_Trucks, l.Max_Employees, l.Max_Orders
                    FROM exportfile e
                    JOIN logistic l ON e.OrderID=l.OrderID
                    """;
            System.out.println(sql);
            ResultSet resultSet = mySQLConnector.queryResults(sql);
            int hang=0;
            while(resultSet.next()){
                int ID=IDlst[hang];
                int Warehouse= resultSet.getInt(1);
                int Truck= resultSet.getInt(2);
                int Employee= resultSet.getInt(3);
                int Amount= resultSet.getInt(4);
                int Max_Warehouse= resultSet.getInt(5);
                int Max_Truck= resultSet.getInt(6);
                int Max_Employee= resultSet.getInt(7);
                int Max_Amount= resultSet.getInt(8);
//                String sql1= """
//                        INSERT INTO exportfile(Warehouse, Truck, Employee, Amount, Max_Warehouse, Max_Trucks, Max_Employees, Max_Orders)
//                        VALUES (%s,%s,%s,%s,%s,%s,%s,%s)
//                        """.formatted(Warehouse,Truck,Employee,Amount,Max_Warehouse,Max_Truck,Max_Employee,Max_Amount);
                String sql1= """
                        UPDATE exportfile\s
                        SET Warehouse = %s,
                            Truck = %s,
                            Employee = %s,
                            Amount = %s,
                            Max_Warehouse=%s,
                            Max_Trucks=%s,
                            Max_Employees=%s,
                            Max_Orders=%s
      
                        WHERE OrderID =%s;
                        """.formatted(Warehouse,Truck,Employee,Amount,Max_Warehouse,Max_Truck,Max_Employee,Max_Amount,ID);
                System.out.println(sql1);
                mySQLConnector.queryUpdate(sql1);
                ++hang;



            }

        }catch (Exception ex){
            System.out.println(ex);
        }


    }

}

