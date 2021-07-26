package ru.netology;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;


import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CallbackTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя Фамилия");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitRequestWithSpaceInName() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя Имя Фамилия");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitRequestWithDoubleName() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя-имя Фамилия");
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
        form.$("[data-test-id=name] input").setValue("Name Surname");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitRequestWithSymbolName() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя Фамилия%имя");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitRequestWithEmptyPhone() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя Фамилия");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitRequestWith12DigitsPhone() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя Фамилия");
        form.$("[data-test-id=phone] input").setValue("+799999999999");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitRequestWith10DigitsPhone() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя Фамилия");
        form.$("[data-test-id=phone] input").setValue("+7999999999");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitRequestWithoutPlusPhone() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя Фамилия");
        form.$("[data-test-id=phone] input").setValue("7999999999");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitRequestWith1DigitsPhone() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя Фамилия");
        form.$("[data-test-id=phone] input").setValue("+7");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitRequestWithLetterPhone() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя Фамилия");
        form.$("[data-test-id=phone] input").setValue("+7phone99999");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitRequestWithoutFlagVar3() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя Фамилия");
        form.$("[data-test-id=phone] input").setValue("+79999999999");
        form.$("[role=button]").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }

    @Test
    void shouldNotSubmitRequestWithOneWord() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=name] input").setValue("Имя");
        form.$("[data-test-id=phone] input").setValue("+79000000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[role=button]").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаны неверно. Проверьте, что введённые данные совпадают с паспортными."));
    }
}
