import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class TaxiSearchController implements Initializable {

    @FXML
    private TableColumn<TaxiSearchModel, String> DateColumn;
    
    @FXML
    private Button DeleteTaxiButton;

    @FXML
    private TextField KeywordTextField;

    @FXML
    private TableColumn<TaxiSearchModel, Integer> NumberColumn;

    @FXML
    private TableColumn<TaxiSearchModel, Integer> OffServiceColumn;

    @FXML
    private AnchorPane SearchTaxi;

    @FXML
    private TableColumn<TaxiSearchModel, String> ServiceNumberColumn;

    @FXML
    private TableView<TaxiSearchModel> TaxiTableView;

    ObservableList<TaxiSearchModel> taxiSearchModelObservableList = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;

    Connection connection = null;

    String query = null;

    
    @FXML
    void DeleteTaxi(ActionEvent event) throws SQLException {
        TaxiSearchModel Selected = TaxiTableView.getSelectionModel().getSelectedItem();

        query = "DELETE FROM taxi WHERE Number  ="+Selected.getNumber();
        databaseConnection connect = new databaseConnection();
        connection = connect.getDBConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
        refreshTable();

    }

    @FXML
    private void refreshTable() throws SQLException {
        taxiSearchModelObservableList.clear();
        query = "SELECT Number, ServiceNumber, Date, OffService FROM taxi;";
        try{

            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);

            while(queryOutput.next()){

                Integer queryNumber = queryOutput.getInt("Number");
                String queryServiceNumber = queryOutput.getString("ServiceNumber");
                String queryDate = queryOutput.getString("Date");
                Integer queryOffService = queryOutput.getInt("OffService");

                taxiSearchModelObservableList.add(new TaxiSearchModel(queryNumber, queryServiceNumber, queryDate, queryOffService));

            }

            NumberColumn.setCellValueFactory(new PropertyValueFactory<>("Number"));
            ServiceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceNumber"));
            DateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
            OffServiceColumn.setCellValueFactory(new PropertyValueFactory<>("OffService"));


            TaxiTableView.setItems(taxiSearchModelObservableList);

            FilteredList<TaxiSearchModel> filteredData = new FilteredList<>(taxiSearchModelObservableList, b -> true);

            KeywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(TaxiSearchModel -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String SearchKeyword = newValue.toLowerCase();

                    if (TaxiSearchModel.getNumber().toString().toLowerCase().indexOf(SearchKeyword) > -1) {
                        return true;//L9itha
                    }else{
                        return false;//mal9ithach
                    }
                });
            });
            
            SortedList<TaxiSearchModel> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(TaxiTableView.comparatorProperty());

            TaxiTableView.setItems(sortedData);

        }catch(SQLException e){
            Logger.getLogger(TaxiSearchController.class.getName()).log(Level.SEVERE, null ,e);

        }
    }
    

    


    @Override
    public void initialize(URL url, ResourceBundle resource) {
    
        databaseConnection connectNow = new databaseConnection();
        
        Connection connectDB = connectNow.getDBConnection();

        String TaxiViewQuery = "SELECT Number, ServiceNumber, Date, OffService FROM taxi;";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(TaxiViewQuery);

            while(queryOutput.next()){

                Integer queryNumber = queryOutput.getInt("Number");
                String queryServiceNumber = queryOutput.getString("ServiceNumber");
                String queryDate = queryOutput.getString("Date");
                Integer queryOffService = queryOutput.getInt("OffService");

                taxiSearchModelObservableList.add(new TaxiSearchModel(queryNumber, queryServiceNumber, queryDate, queryOffService));

            }

            NumberColumn.setCellValueFactory(new PropertyValueFactory<>("Number"));
            ServiceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceNumber"));
            DateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
            OffServiceColumn.setCellValueFactory(new PropertyValueFactory<>("OffService"));


            TaxiTableView.setItems(taxiSearchModelObservableList);

            FilteredList<TaxiSearchModel> filteredData = new FilteredList<>(taxiSearchModelObservableList, b -> true);

            KeywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(TaxiSearchModel -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String SearchKeyword = newValue.toLowerCase();

                    if (TaxiSearchModel.getNumber().toString().toLowerCase().indexOf(SearchKeyword) > -1) {
                        return true;//L9itha
                    }else{
                        return false;//mal9ithach
                    }
                });
            });
            
            SortedList<TaxiSearchModel> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(TaxiTableView.comparatorProperty());

            TaxiTableView.setItems(sortedData);

        }catch(SQLException e){
            Logger.getLogger(TaxiSearchController.class.getName()).log(Level.SEVERE, null ,e);

        }
        
    }

}
