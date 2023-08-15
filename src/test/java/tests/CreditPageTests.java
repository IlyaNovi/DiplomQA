package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPageTests {

    StartPage startPage = open("http://localhost:8080/", StartPage.class);

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        SQLHelper.clearDB();
    }

    @DisplayName("Успешная покупка в кредит по карте, со статусом APPROVED")
    @Test
    void creditPositiveAllFieldValidApproved() {
        var cardInfo = DataHelper.getApprovedCard();
        var creditPage = startPage.goToCreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.checkApprovedNotification();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @DisplayName("Отказ в покупке в кредит по карте, со статусом DECLINED")
    @Test
    void creditPositiveAllFieldValidDeclined() {
        var cardInfo = DataHelper.getDeclinedCard();
        var creditPage = startPage.goToCreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.checkDeclinedNotification();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }
}
