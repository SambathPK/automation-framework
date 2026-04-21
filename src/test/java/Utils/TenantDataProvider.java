package Utils;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;


public class TenantDataProvider {

    @DataProvider(name = "SmokeTest")
    public static Object[][] smokeTenants() {

        return new Object[][]{

//                {"https://tagcorporation.akku.work/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://deepamfinvest.akku.work/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://vitrana.akku.work", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://classicmouldsapp.akku.work/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://anahat.akku.work/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://abilityengg.akku.work", "presales@cloudnowtech.com", "CloudNow@123"},
                {"https://peindiaapp.akku.work", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://cmdpplapp.akku.work", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://ultramarinepigments.akku.work", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://lakshmicarbons.akku.work/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://vinura.akku.work/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
//                {"https://presalesteam.akku.work/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://grtjewels.akku.work", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://envi.akku.work/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
//                {"https://tnqtech.akku.work/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
//                {"https://casagrand.akku.work", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
//                {"https://tilhealthcare.akku.work/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
//                {"https://onelogin.casagrand.co.in/", "presales@cloudnowtech.com", "?Akku@CybEr#2026"},
                {"https://dekielectronics.akku.work", "presales@cloudnowtech.com", "?Akku@CybEr#2026"}


        };
    }

    @DataProvider(name = "RegressionTest")
    public static Object[][] regressionTenants() {

        return new Object[][]{
                {"https://releasetest.akku.work/"}
        };
    }
}
