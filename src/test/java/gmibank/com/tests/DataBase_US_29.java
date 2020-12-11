package gmibank.com.tests;


import gmibank.com.utilities.ConfigurationReader;
import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataBase_US_29 {

    ///istedigimiz tablodan sorgu yapmak icin
    String userDataQuery = "SELECT * FROM " + ConfigurationReader.getProperty("usersTableName"); //query'den sonra bosluk olmali!
    String countiesDataQuery="SELECT * FROM " +ConfigurationReader.getProperty("countriesTableName");
    String stateDataQuery= "SELECT * FROM " +ConfigurationReader.getProperty("statesTableName");

    //Alinan sorgu sonuclarini depolamak icin
    ResultSet resultSetUser;
    ResultSet resultSetCountry;
    ResultSet resultSetState;


    List<String> userFirstNameList = new ArrayList<>();
    List<String> countryList = new ArrayList<>();
    List<String> stateList=new ArrayList<>();

   // @Given("user  connecting GMI database")
    public void user_connecting_GMI_database() {
        //Alinan sorgu sonuclarini depolamak icin
        resultSetUser = DatabaseConnector.getResultSet(userDataQuery);
        resultSetCountry=DatabaseConnector.getResultSet((countiesDataQuery));
        resultSetState=DatabaseConnector.getResultSet(stateDataQuery);
    }

   // @Given("user read all user data from database")
    public void user_read_all_user_data_from_database() throws SQLException {
        ResultSetMetaData userMetaData = resultSetUser.getMetaData();
        int columnCount = userMetaData.getColumnCount();  //column sayisi
        System.out.println(columnCount);

        for (int i = 1; i <= columnCount; i++) {
            System.out.println(userMetaData.getColumnName(i)); //column isimleri
        }

        while (resultSetUser.next()) {
            String firstname = resultSetUser.getString("first_name");
            userFirstNameList.add(firstname);
        }
        System.out.println(userFirstNameList);

        ///DatabaseConnectector class'taki method araciligiyla yapilan ornek
        List<Map<String,String>> listMap = DatabaseConnector.getQueryAsAListOfMaps(userDataQuery);
        System.out.println("List Map :" +listMap.get(0).get("first_name"));
    }

   // @Then("user validate all user data")
    public void user_validate_all_user_data() throws SQLException {
        String userFirstNameQuery = "SELECT first_name FROM " + ConfigurationReader.getProperty("usersTableName");
        resultSetUser = DatabaseConnector.getResultSet(userFirstNameQuery);


        List<String> actualFirstNameList = new ArrayList<>();

        while (resultSetUser.next()) {
            String firstName = resultSetUser.getString(1);  //ilk index// tek colum geldigi icin
            actualFirstNameList.add(firstName);
        }
        System.out.println(actualFirstNameList);

        Assert.assertEquals(userFirstNameList, actualFirstNameList);
    }

  //  @Given("user read all countries infos from database")
    public void user_read_all_countries_infos_from_database() throws SQLException {
        ResultSetMetaData countryMetaData=resultSetCountry.getMetaData() ;
        int columnCount= countryMetaData.getColumnCount();
        System.out.println(columnCount);

        for (int i = 1; i <=columnCount ; i++) {
            System.out.println(countryMetaData.getColumnName(i));
        }
        while(resultSetCountry.next()){
            String country=resultSetCountry.getString("name");
            countryList.add(country);
        }
        System.out.println(countryList);

    }

  //  @Then("user validate all countries infos")
    public void user_validate_all_countries_infos() throws SQLException {
        String countryNameQuery="SELECT name FROM " +ConfigurationReader.getProperty("countriesTableName");
        resultSetCountry=DatabaseConnector.getResultSet(countryNameQuery);

        List<String> actualCountry=new ArrayList<>();

        while(resultSetCountry.next()){
            String countryName=resultSetCountry.getString("name");
            actualCountry.add(countryName);
        }
        Assert.assertEquals(countryList,actualCountry);

    }

  //  @Given("user read all states of related to USA from database")
    public void user_read_all_states_of_related_to_USA_from_database() throws SQLException {
        ResultSetMetaData stateMetaData=resultSetState.getMetaData();
        int stateColumnCount=stateMetaData.getColumnCount();
        System.out.println("stateColumnCount : " +stateColumnCount);

        for (int i = 1; i <=stateColumnCount ; i++) {
            System.out.println(stateMetaData.getColumnName(i));
        }
        while(resultSetState.next()){
            String states=resultSetState.getString("name");
            stateList.add(states);
        }
        System.out.println(stateList);


    }

    //@Then("user validate all states of related to USA")
    public void user_validate_all_states_of_related_to_USA() throws SQLException {
        String statesDataQuery= "SELECT * FROM tp_state WHERE name='Washington' ";
        resultSetState=DatabaseConnector.getResultSet(statesDataQuery);

        List<String> actualStateList=new ArrayList<>();

        while(resultSetState.next()){
            String states=resultSetState.getString("name");
            actualStateList.add(states);
        }

        Assert.assertTrue(stateList.containsAll(actualStateList));
    }






}