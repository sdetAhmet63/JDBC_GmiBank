package gmibank.com.tests;


import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

public class Database_OdevCozumu_Pinar {

    String userFirstnameQuery;
    String customerPhoneNoQuery;
    String userAndCustomerQuery;

    List<Map<String, String>> userlistMap;
    List<Map<String, String>> customerlistMap;
    List<Map<String, String>>  userAndCustomerListMap;

  //  @Given("kullanici GMI Bank database'ine baglanir")
    public void kullanici_GMI_Bank_database_ine_baglanir() {
        //baglanti "DatabaseConnector.getQueryAsAListOfMaps" methodu ile yapildi

    }

  //  @Given("kullanici firstname'i Ali olanlarin email datalarini getirir")
    public void kullanici_firstname_i_Ali_olanlarin_email_datalarini_getirir() throws SQLException {
        userFirstnameQuery = "SELECT email" +
                " FROM jhi_user" +
                " WHERE first_name='Ali' ";

        userlistMap = DatabaseConnector.getQueryAsAListOfMaps(userFirstnameQuery);

        System.out.println("listMap :" + userlistMap);


    }

   // @Then("Kullanici email'i bos olan kullanicinin olmadigini dogrular")
    public void kullanici_email_i_bos_olan_kullanicinin_olmadigini_dogrular() {

        for (Map<String, String> w : userlistMap) {
            Assert.assertFalse(w.get("email").equals(""));
        }
    }

   // @Given("kullanici id'si {int} den buyuk olanlarin  phone number'larini goruntuler")
    public void kullanici_id_si_den_buyuk_olanlarin_phone_number_larini_goruntuler(Integer idNumber) throws SQLException {
        customerPhoneNoQuery = "SELECT phone_number" +
                " FROM tp_customer" +
                " WHERE id> " + idNumber;

        customerlistMap = DatabaseConnector.getQueryAsAListOfMaps(customerPhoneNoQuery);
        System.out.println("customerlistMap : " + customerlistMap);

    }

   // @Then("kullanici phone number'larinin {int} haneden buyuk olmadigini dogrular")
    public void kullanici_phone_number_larinin_haneden_buyuk_olmadigini_dogrular(Integer phoneNumber) throws SQLException {
        customerPhoneNoQuery = "SELECT phone_number" +
                " FROM tp_customer" +
                " WHERE id > " + phoneNumber;

        customerlistMap = DatabaseConnector.getQueryAsAListOfMaps(customerPhoneNoQuery);
        System.out.println(customerlistMap);

        System.out.println("numaralar arasinda (-) olmadan 10 haneli: ");
        for (Map<String, String> w : customerlistMap) {

            if (w.get("phone_number").replace("-", "").length() <= 10) {
                System.out.println(w.get("phone_number").replace("-", ""));
            }
            Assert.assertFalse((w.get("phone_number").length() <= 10));
            ///number uzunlugu normalde 10 olmali ama (-) isareti de hane sayisi olarak sayilmis yani bu bir bug'dir
        }
    }

   // @Given("kullanici activated'i true olmayan kisilerin isim soyisim ve mobile numberlerini goruntuler")
    public void kullanici_activated_i_true_olmayan_kisilerin_isim_soyisim_ve_mobile_numberlerini_goruntuler() throws SQLException {
        userAndCustomerQuery = "select tp_customer.phone_number,jhi_user.first_name,jhi_user.last_name\n" +
                "from jhi_user\n" +
                "join tp_customer\n" +
                "on jhi_user.id=tp_customer.user_id\n" +
                "where not activated='true' ";

        userAndCustomerListMap=DatabaseConnector.getQueryAsAListOfMaps(userAndCustomerQuery);
        System.out.println(userAndCustomerListMap);


    }

}
