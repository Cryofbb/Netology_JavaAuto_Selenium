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
        form.$("[data-test-id=name] input").setValue("Имя");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
    @Test
    void shouldSubmitRequestWithSpaceInName() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя имя");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
    @Test
    void shouldSubmitRequestWithDoubleName() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя-имя");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
    @Test
    void shouldNotSubmitRequestWithEmptyName() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldNotSubmitRequestWithEnglishName() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Name");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldNotSubmitRequestWithSymbolName() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя%имя");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
}
