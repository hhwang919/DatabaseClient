package DBClient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBController {
    @FXML
    private TextArea queryText;

    @FXML
    private TableView<Employee> tableView;


    public void runQuery()
    {
        String sqlCommand = queryText.getText().trim();
        System.out.println(sqlCommand);

        ObservableList<String> columnList = FXCollections.observableArrayList();
        // List<String> columnList = new ArrayList<>();

        ObservableList<Employee> values = Employee.getEmployees(sqlCommand, columnList);

        System.out.println("DBController : " + columnList.toString());

        tableView.getColumns().clear();

        int colCount = columnList.size();
        System.out.println("colCount : " + colCount);
        for(int i = 0; i < colCount; i++)
        {
            // Note : List index is 0-based.
            final int j = i;
            //System.out.println("a(n) " + columnList.get(i));
            //System.out.println("This is a(n) " + columnList.get(i).getClass().getSimpleName());

            //TableColumn<Employee, String> col = new TableColumn<Employee, String>(columnList.get(i));
            //col.setCellValueFactory(new PropertyValueFactory(columnList.get(i)));

            //Setting cell property value to correct variable from employee class.

            TableColumn col = new TableColumn(columnList.get(i));

            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Employee, String>, ObservableValue<String>>()
            {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Employee, String> param)
                {
                    //return new SimpleStringProperty(param.getValue().get(j).toString());
                    return new SimpleStringProperty(param.getValue().getColumnList().get(j).toString());
                }
            });

            //tableView.getColumns().addAll(col);
            tableView.getColumns().add(col);
            //System.out.println("Column [" + i + "] : " + columnList.get(i));
        }

        //System.out.println("This is a(n) " + values.getClass().getSimpleName());
        //System.out.println("Size : " + values.size());

        // Add to TableView
        tableView.setItems(values);
    }
}
