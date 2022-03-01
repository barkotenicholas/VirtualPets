package models;

import database.DB;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DatabaseRule implements BeforeTestExecutionCallback, AfterTestExecutionCallback {


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        try(Connection con = DB.sql2o.open()) {
            String deletePersonsQuery = "DELETE FROM persons *;";
            con.createQuery(deletePersonsQuery).executeUpdate();
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/virtual_pets_test", "moringa", "access");

    }
}
