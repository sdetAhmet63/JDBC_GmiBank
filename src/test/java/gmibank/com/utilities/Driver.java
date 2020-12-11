package gmibank.com.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;


import java.util.concurrent.TimeUnit;

//Test base i artik sadece Before After methodlari icin vs kullanacaz onun disinda driver classsi
public class Driver {

    //Driver class driver instance'i baslatmak icin kullanilir(Sinngleton Driver)
    //ihtiyacmiz oldugunda driver'i kurmak ve baslatmak icin kullanilir
    //Projede kullanilan Web Driver i tekrar tekrar olusturmaktan kurtulmak icin kullaniyorz vu classi
    //Driver null oldugunda create edip baslatacaz(if driver=null)
    //Webdriver driver ; ==> Driver in null olmasi demek
    //Driver class'i farkli browserlar(tarayici) ile de kolayca baslatmak icin de kullanilir

    private Driver(){  //class ismiyle cons olusturdk/disardan kullanilmasin object olusturulmasin diye
//baska obje olusturulmasini istemedigmz icin bos bir cons. olusturyorz
    }

    //driver instance olusturalim
    static WebDriver driver;//diger classlarda kullanmak icin static yaptk
    //driver i baslatmak icn static method  olusturdk
    public static WebDriver getDriver() {  //diger classlarda kullanacaz diye public static yaptk
        if (driver == null) {
            switch (ConfigurationReader.getProperty("browser")){  //browseer chrome kullanilmazssa config classtan degistir
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "internetExplorer":
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case "safari":
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driver = new SafariDriver();
                    break;
                case "headless-chrome":  ///sayfayi acmadan run ediyor
                    WebDriverManager.chromedriver().setup();
                    driver=new ChromeDriver(new ChromeOptions().setHeadless(true));
                    break;
                ///bu  eklemeler artirilabilir
                //hangi tarayiciyi kulllanacaksak config readerda browser ismini degistirmeliyiz
            }

        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;

//getdriver once create eder sonra baslatir

    }
    public static void closeDriver(){
        if(driver!=null){ //driver bi seye atanmissa mesala chrome ise  bu sayfayi kapat diyoruz
            //eger driver chromu isaret ediyorsa kapat
            driver.quit();
            driver=null;  //driver null oldugundan emin olmak icin tekrar null olarak atayalim
            //boylelikle driver'i tekrar baslatabilirim//sonrasinda istedigimiz browsere gecmek icin
            //yapyoruz bunu//yoksa hep google da kalir baska browserlar calismaz
            //multi browser test yaparken(chrome,firefox,ie...)yaparken bu onemli olacak
        }
    }
}
