import java.sql.Connection;
import java.sql.DriverManager;

public class databaseConnection {
    

public Connection databaseLink;

public Connection getDBConnection(){

String databaseName = "test";
String databaseUser = "root";
String databasePassword = "";

String url = "jdbc:mysql://localhost/" + databaseName;

try{
    Class.forName("com.mysql.jdbc.Driver");
    databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
}catch(Exception e){
    e.printStackTrace();
}

return databaseLink;

}
}