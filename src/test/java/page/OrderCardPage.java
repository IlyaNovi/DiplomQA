package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class OrderCardPage {
    private final SelenideElement cardNumber = $(byText("Номер карты")).parent().$(".input__top");
    private final SelenideElement month = $(byText("Месяц")).parent().$("input__top");
    private final SelenideElement year = $(byText("Год")).parent().$("input__top");
    private final SelenideElement cardHolder = $(byText("Владелец")).parent().$("input__top");
    private final SelenideElement cvc = $(byText("CVC/CVV")).parent().$("input__top");
    private final SelenideElement button = $(byText("Продолжить")).parent().$("button__text");
    private SelenideElement heading = $(byText("Оплата по карте")).parent().$("h3");


    public void insertCardData(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        cardHolder.setValue(cardInfo.getCardHolder());
        cvc.setValue(cardInfo.getCvc());
        button.click();
    }


    public void checkApprovedNotification() {
        SelenideElement successfulNotification = $(".notification_status_ok .notification__content").shouldHave(Condition.text("Операция одобрена Банком."), Duration.ofMillis(15000));
        successfulNotification.shouldBe(Condition.visible);
    }

    public void checkDeclinedNotification() {
        SelenideElement declineNotification = $(".notification_status_error .notification__content").shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."), Duration.ofMillis(15000));
        declineNotification.shouldBe(Condition.visible);
    }
}

