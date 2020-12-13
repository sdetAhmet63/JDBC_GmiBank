package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class US_08_Test {

    //user can update table (jhi_persistent_audit_event)/ AUTENTICATION_FAILURE to HATA, =>
    // event-id's are higher than 44500, principal is "group8user" event_type is 'AUTHENTICATION_FAILURE'
    // /jhi_persistent_audit_event tablosunda event_id'si 44500'den büyük, principal degeri "group8user"
    // ve event_type degeri "AUTHENTICATION_FAILURE" olan verilerin "AUTHENTICATION_FAILURE" degerlerini
    // "HATA" olarak güncelleyin.// not: sorgu sırasında toplam 6 record güncellenecek, güncelleme sonrası
    // tekrar eski haline güncellemeyi unutmayın.

    @Test
    public void TC_0801() throws SQLException {
        String afterUpdate="select event_type\n" +
                " from jhi_persistent_audit_event\n" +
                " where event_type='AUTHENTICATION_FAILURE' and event_id>44500 and principal='group8user' ";

        List<Map<String,String>>  eventList=DatabaseConnector.getQueryAsAListOfMaps(afterUpdate);
        System.out.println(eventList);

        String updateQuery="update jhi_persistent_audit_event  set event_type='AUTHENTICATION_FAILURE'\n" +
                " where event_type='HATA' and event_id>44500 and principal='group8user' ";

        DatabaseConnector.executeUpdateQuery(updateQuery);

        for (Map<String,String> w: eventList){
            Assert.assertEquals(w.get("event_type"),"HATA");

        }
        List<Map<String,String>>  eventListBefore=DatabaseConnector.getQueryAsAListOfMaps(afterUpdate);
        System.out.println(eventListBefore);





    }


    // kullanıcı jhi_persistent_audit_evt_data tablosundan bir value degeri "Bad credentials" olan bir ID belirler
    // ve o ID'yi tablodan siler.
    @Test
    public void TC_0802() {
        String deleteQuery="delete from jhi_persistent_audit_evt_data\n" +
                "where event_id=0 ";

        DatabaseConnector.executeUpdateQuery(deleteQuery);
    }


    //kullanıcı tp_account data tablosundan tüm hesap çeşitlerini listeler, bu hesaplardan kaçar adet olduğunu
    // sayar ve her hesapta ortalama  ne kadar para (balance) olduğunu kontrol eder
    @Test
    public void TC_0803() throws SQLException {
        String query3="select count(account_type) as totalAccount,account_type,avg (balance) as ortBalance\n" +
                "from tp_account\n" +
                "group by account_type ";

        List<Map<String,String>> listAccount=DatabaseConnector.getQueryAsAListOfMaps(query3);
        System.out.println(listAccount);
    }
}
