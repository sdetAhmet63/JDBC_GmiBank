package gmibank.com.tests;

import gmibank.com.utilities.ConfigurationReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import gmibank.com.utilities.DatabaseConnector;



public class DataBase_OdevCozumu_Samet {


//    Given kullanici GMI Bank database'ine baglanir
//    Scenario: Kullanici user ve customer tablosundaki verileri dogrular
//    Given kullanici activated'i true olmayan kisilerin isim soyisim ve mobile numberlerini goruntuler

    String userQuery1 = "SELECT first_name, email FROM "
            + ConfigurationReader.getProperty("usersTableName")
            +" WHERE first_name = 'Ali' ";
    String userQuery2 = "SELECT mobile_phone_number FROM "
            + ConfigurationReader.getProperty("customerTableName")
            +" WHERE id>30000;";

    List<String> userEmail = new ArrayList<>();
    List<String> userPhoneNumber = new ArrayList<>();
    ResultSet resultSet;


    //    Scenario: Kullanici user tablosundaki verileri dogrular
    //    Given kullanici firstname'i Ali olanlarin email datalarini getirir
    //    Then Kullanici email'i bos olan kullanicinin olmadigini dogrular
    @Test
    public void test1 () throws SQLException {
        resultSet = DatabaseConnector.getResultSet(userQuery1);
        while (resultSet.next()){
            userEmail.add(resultSet.getString(2));
        }
        System.out.println(userEmail);
        Assert.assertFalse(userEmail.contains(""));
    }

    //    Scenario: Kullanici customer tablosundaki verileri dogrular
    //    Given  kullanici id'si 30000 den buyuk olanlarin  phone number'larini goruntuler
    //    Then  kullanici phone number'larinin 10 haneden buyuk olmadigini dogrular
    @Test
    public void test2 () throws SQLException {
        resultSet = DatabaseConnector.getResultSet(userQuery2);
        while (resultSet.next()){
            String data = resultSet.getString(1);
            data = data.replace("-","");
            data = data.replace("+","");
            userPhoneNumber.add(data);
        }
        System.out.println(userPhoneNumber);
        for(String w : userPhoneNumber){
            Assert.assertEquals(w.length(),11);
        }
    }

}
