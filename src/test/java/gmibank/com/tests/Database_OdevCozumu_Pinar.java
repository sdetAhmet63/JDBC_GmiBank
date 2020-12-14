package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DataBase_OdevCozumu_Pinar {

    String userFirstnameQuery;
    String customerPhoneNoQuery;
    String userAndCustomerQuery;

    List<Map<String, String>> userlistMap;
    List<Map<String, String>> customerlistMap;
    List<Map<String, String>> userAndCustomerListMap;


    //    Given kullanici firstname'i Ali olanlarin email datalarini getirir
    //    Then Kullanici email'i bos olan kullanicinin olmadigini dogrular
    @Test
    public void query1() throws SQLException {
        userFirstnameQuery = "SELECT email" +
                " FROM jhi_user" +
                " WHERE first_name='Ali' ";

        userlistMap = DatabaseConnector.getQueryAsAListOfMaps(userFirstnameQuery);

        System.out.println("listMap :" + userlistMap);

        for (Map<String, String> w : userlistMap) {
            Assert.assertFalse(w.get("email").equals(""));
        }
    }


    // Given  kullanici id'si 30000 den buyuk olanlarin  phone number'larini goruntuler
    //  kullanici phone number'larinin 10 haneden buyuk olmadigini dogrular
    @Test
    public void query2() throws SQLException {
        customerPhoneNoQuery = "SELECT phone_number" +
                " FROM tp_customer" +
                " WHERE id> " + 30000;

        customerlistMap = DatabaseConnector.getQueryAsAListOfMaps(customerPhoneNoQuery);
        System.out.println("customerlistMap : " + customerlistMap);
        ///******************
        System.out.println("numaralar arasinda (-) olmadan 10 haneli: ");
        for (Map<String, String> w : customerlistMap) {

            if (w.get("phone_number").replace("-", "").length() <= 10) {
                System.out.println(w.get("phone_number").replace("-", ""));
            }
            Assert.assertFalse((w.get("phone_number").length() <= 10));
            ///number uzunlugu normalde 10 olmali ama (-) isareti de hane sayisi olarak sayilmis yani bu bir bug'dir
        }
    }

    // kullanici activated'i true olmayan kisilerin isim soyisim ve mobile numberlerini goruntuler
    //kullanici activated'i true olmayan kisilerin sayisinin 46 oldugunu dogrular
    @Test
    public void query3() throws SQLException {
        userAndCustomerQuery = "select tp_customer.phone_number,jhi_user.first_name,jhi_user.last_name\n" +
                "from jhi_user\n" +
                "join tp_customer\n" +
                "on jhi_user.id=tp_customer.user_id\n" +
                "where not activated='true' ";

        userAndCustomerListMap=DatabaseConnector.getQueryAsAListOfMaps(userAndCustomerQuery);
        System.out.println(userAndCustomerListMap.size());
        Assert.assertEquals(userAndCustomerListMap.size(),39);
    }
    //datalarin sayisi degisebilir yeni degisiklige uygun guncelleyiniz...
}

