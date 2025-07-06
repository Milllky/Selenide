
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    public String dateGenerator(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void test() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String genDate = dateGenerator(7, "dd.MM.yyyy");
        $("[data-test-id='date'] input").setValue(genDate);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='agreement']").click();
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79098909090");

        $(byText("Забронировать")).click();
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + genDate),  Duration.ofSeconds(15)).should(Condition.visible);
        //        SelenideElement result = $(Selectors.withText("Успешно")).should(Condition.visible,Condition.text(""), Duration.ofSeconds(15));
    }
}
