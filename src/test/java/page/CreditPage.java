package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CreditPage {
    private final SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
    private final SelenideElement cardNumber = $(byText("Номер карты")).parent().$("input__control");
    private final SelenideElement month = $(byText("Месяц")).parent().$("input__control");
    private final SelenideElement year = $ (byText("Год")).parent().$("input__control");
    private final SelenideElement cardHolder = $(byText("Владелец")).parent().$("input__control");
    private final SelenideElement cvc =  $(byText("CVC/CVV")).parent().$("input__control");
    private final SelenideElement continueButton = $(byText("Продолжить")).parent().$("button__content");

       public CreditPage() {
        heading.shouldBe(visible);
        heading.shouldHave(text("Кредит по данным карты"));
    }

    public void insertCardData(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        cardHolder.setValue(cardInfo.getCardHolder());
        cvc.setValue(cardInfo.getCvc());
        continueButton.click();
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

