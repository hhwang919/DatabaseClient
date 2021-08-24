package DBClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;


public class Employee
{
    //private ObservableList employeeTableList;
    private ObservableList columnList;

    public Employee()
    {
        this(null);
    }

    public Employee(ObservableList list)
    {
        if (list == null)
        {
            this.columnList = FXCollections.observableArrayList();
        }
        else
        {
            this.columnList = list;
        }
    }


    public ObservableList getColumnList()
    {
        return this.columnList;
    }


    public static ObservableList<Employee> getEmployees(String sqlCommand, ObservableList<String> col)
    {
        ObservableList<Employee> ret_val = FXCollections.observableArrayList();

        try
        {
            Connection conn = DBConnection.getConnection();

            // Get a new statement for the current connection
            Statement statement = conn.createStatement();

            // Execute a SELECT SQL command
            ResultSet rs = statement.executeQuery(sqlCommand);

            //Retrieving the ResultSetMetadata object
            ResultSetMetaData rsMetaData = rs.getMetaData();

            int colCount = rsMetaData.getColumnCount();

            // Add table column name
            for(int i = 1; i <= colCount; i++)
            {
                // Note : The column indexes of rsMetaData are 1-based, rather than 0-based.
                col.add(rsMetaData.getColumnName(i));
                System.out.println("Column [" + i  + "] : " + rsMetaData.getColumnName(i) );
            }

            System.out.println("Employee : " + col);

            //this.columnList = col;

            // Add data to ObservableList
            while(rs != null && rs.next())
            {
                // Each row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= colCount; i++)
                {
                    // Each column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                ret_val.add(new Employee(row));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.toString());
            // e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("JDBC Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText(e.toString());

            alert.showAndWait();
        }

        return ret_val;
    }
}
