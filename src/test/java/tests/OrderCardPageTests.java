package tests;


import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderCardPageTests {
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

    @DisplayName("Покупка по карте, со статусом APPROVED")
    @Test
    void orderPositiveAllFieldValidApproved() {
        var cardInfo = DataHelper.getApprovedCard();
        var orderPage = startPage.goToOrderCardPage();
        orderPage.insertCardData(cardInfo);
        orderPage.checkApprovedNotification();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());

    }

    @DisplayName("Отказ в покупке по карте, со статусом DECLINED")
    @Test
    void orderPositiveAllFieldValidDeclined() {
        var cardInfo = DataHelper.getDeclinedCard();
        var orderPage = startPage.goToOrderCardPage();
        orderPage.insertCardData(cardInfo);
        orderPage.checkDeclinedNotification();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }
}