package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {
    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String cardHolder;
        String cvc;
    }

    public static String getShiftedMonth(int monthCount) {
        return LocalDate.now().plusMonths(monthCount).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getShiftedYear(int yearCount) {
        return LocalDate.now().plusYears(yearCount).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static CardInfo getApprovedCard() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth(5);
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getDeclinedCard() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth(3);
        String year = getShiftedYear(2);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444442", month, year, holder, cvv);
    }
}


