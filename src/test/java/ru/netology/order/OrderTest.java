package ru.netology.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class OrderTest {
    @BeforeEach
    public void openBrowser() {
        open("http://localhost:9999/");
    }

    @Test
    public void positive() {
        $(By.name("name")).setValue("Иванов Иван");
        $(By.name("phone")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector(".paragraph[data-test-id=\"order-success\"]")).shouldHave(
                text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.")
        );
    }

    @Test
    public void positiveNameMinusSign() {
        $(By.name("name")).setValue("Иванов-Иван");
        $(By.name("phone")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector(".paragraph[data-test-id=\"order-success\"]")).shouldHave(
                text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.")
        );
    }

    @Test
    public void wrongName() {
        $(By.name("name")).setValue("Qwerty");
        $(By.name("phone")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("[data-test-id=\"name\"] .input__sub")).shouldHave(
                text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")
        );
    }

    @Test
    public void emptyName() {
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.name("phone")).setValue("+79991234567");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("[data-test-id=\"name\"] .input__sub")).shouldHave(
                text("Поле обязательно для заполнения")
        );
    }

    @Test
    public void wrongPhone() {
        $(By.name("name")).setValue("Иванов Иван");
        $(By.name("phone")).setValue("wrong");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("[data-test-id=\"phone\"] .input__sub")).shouldHave(
                text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")
        );
    }

    @Test
    public void missPlacedPlusSign() {
        $(By.name("name")).setValue("Иванов Иван");
        $(By.name("phone")).setValue("7+9991234567");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("[data-test-id=\"phone\"] .input__sub")).shouldHave(
                text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")
        );
    }

    @Test
    public void emptyPhone() {
        $(By.name("name")).setValue("Иванов Иван");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("[data-test-id=\"phone\"] .input__sub")).shouldHave(
                text("Поле обязательно для заполнения")
        );
    }

    @Test
    public void noAgreement() {
        $(By.name("name")).setValue("Иванов Иван");
        $(By.name("phone")).setValue("+79991234567");
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("[data-test-id=\"agreement\"]")).shouldHave(
                cssClass("input_invalid")
        );
    }
}