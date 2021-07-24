package ru.netology;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CallbackTest {
    @BeforeEach
    void setup(){
        open("http://localhost:9999");
    }
    @Test
    void shouldSubmitRequest() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
}
