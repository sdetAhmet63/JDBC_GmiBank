package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class US_07_Test {

    @Test //TC0701 ==> Kullanici jhi user tablosundan activation key null olmayan
            // id,login,email ve aktivation_key'lerini id lerine gore artan sekilde listeler
    public void TC_0701() throws SQLException {
        String adminQuery = "select id,login,email,activation_key\n" +
                "from jhi_user\n" +
                "where activation_key is not null\n" +
                "order by id";
        List<Map<String,String>> query1 = DatabaseConnector.getQueryAsAListOfMaps(adminQuery);
        System.out.println(query1);
        for(Map<String,String>w : query1){
            Assert.assertFalse(w.get("activation_key").isEmpty());
        }
    }

    @Test //TC0702 ==> Kullanici jhi user authority tablosundan authorityname ROLE ADMIN olan
        //bilgileri user id azalan sekilde listeler
    public void TC_0702() throws SQLException {
        String adminQuery = "select *\n" +
                "from jhi_user_authority\n" +
                "where authority_name='ROLE_ADMIN'\n" +
                "order by user_id desc";
        List<Map<String,String>> query1 = DatabaseConnector.getQueryAsAListOfMaps(adminQuery);
        System.out.println(query1);
        for(Map<String,String>w : query1){
            Assert.assertEquals(w.get("authority_name"),"ROLE_ADMIN");
        }
    }


}
