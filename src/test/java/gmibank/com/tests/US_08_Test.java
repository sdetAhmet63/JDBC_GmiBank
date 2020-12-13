package gmibank.com.tests;


import gmibank.com.utilities.DatabaseConnectorSamet;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class US_08_Test {

    String queryTC0801Select = "select * " +
            "from jhi_persistent_audit_event " +
            "where event_id>44500 and principal = 'group8user' and event_type = 'AUTHENTICATION_FAILURE'";

    String queryTC0801Update = "update jhi_persistent_audit_event " +
            "SET event_type = ? " +
            "WHERE event_id>44500 and principal = 'group8user' and event_type = 'AUTHENTICATION_FAILURE';";

    String queryTC0801SelectAfterUpdate = "select * " +
            "from jhi_persistent_audit_event " +
            "where event_id>44500 and principal = 'group8user' and event_type = 'HATA'";

    String queryTC0801Fix = "update jhi_persistent_audit_event " +
            "set event_type = ? " +
            "where event_id>44500 and principal = 'group8user' and event_type = 'HATA'";

    String quertTC0802Delete = "delete from jhi_persistent_audit_evt_data " +
            "where event_id = ? ";

    String getQueryTC0803 = "select count(account_type) as total_account, account_type, avg(balance) as ortalama " +
            "from tp_account " +
            "group by account_type";

    ResultSet resultset;

    List<String> eventType = new ArrayList<>();
    List<Integer> dataInt = new ArrayList<>();
    List<String> dataString = new ArrayList<>();

    @AfterMethod
    public void after(){
        DatabaseConnectorSamet.closeConnection();
    }

    @Test
    public void tc0801() throws SQLException {
        resultset = DatabaseConnectorSamet.getResultSet(queryTC0801Select);
        while (resultset.next()){
            String queryData = resultset.getString("event_type");
            Assert.assertEquals(queryData,"AUTHENTICATION_FAILURE");
            eventType.add(queryData);
            }
        Assert.assertEquals(eventType.size(),6);
            eventType.clear();
        DatabaseConnectorSamet.executeUpdateQuery(queryTC0801Update);
        resultset = DatabaseConnectorSamet.getResultSet(queryTC0801SelectAfterUpdate);
        while (resultset.next()){
            String queryData = resultset.getString("event_type");
            Assert.assertEquals(queryData,"HATA");
            eventType.add(queryData);
        }
        Assert.assertEquals(eventType.size(),6);
        }
    @Test
    public void tc0801Fix(){
        //bu method tc0801 ile değiştirilen verileri eski haline çevirmek için kullanılacaktır.
        DatabaseConnectorSamet.executeUpdateQueryFix(queryTC0801Fix);
        }
    @Test
    public void tc0802(){
        DatabaseConnectorSamet.executeDelete(quertTC0802Delete);
    }
    @Test
    public void tc0803() throws SQLException {
        resultset = DatabaseConnectorSamet.getResultSet(getQueryTC0803);
        while (resultset.next()){
            int resultTotalAccount = resultset.getInt(1);
            dataInt.add(resultTotalAccount);
            String resultAccountType = resultset.getString(2);
            dataString.add(resultAccountType);
            int resultOrtalama = resultset.getInt(3);
            dataInt.add(resultTotalAccount);
        }
        System.out.println(dataInt);
        System.out.println(dataString);
    }

    }



