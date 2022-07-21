package ru.netology.order;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class OrderTest {
    @Test
    public void positive() {
        open("http://localhost:9999/");
        $(By.name("name")).setValue("Иванов Иван");
        $(By.name("phone")).setValue("+79991234567");
        $(By.className("checkbox__box")).click();
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.className("Success_successBlock__2L3Cw")).shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void positiveNameMinusSign() {
        open("http://localhost:9999/");
        $(By.name("name")).setValue("Иванов-Иван");
        $(By.name("phone")).setValue("+79991234567");
        $(By.className("checkbox__box")).click();
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.className("Success_successBlock__2L3Cw")).shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void wrongName() {
        open("http://localhost:9999/");
        $(By.name("name")).setValue("Qwerty");
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("span[data-test-id=\"name\"] span.input__sub")).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void emptyName() {
        open("http://localhost:9999/");
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("span[data-test-id=\"name\"] span.input__sub")).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void wrongPhone() {
        open("http://localhost:9999/");
        $(By.name("name")).setValue("Иванов Иван");
        $(By.name("phone")).setValue("wrong");
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("span[data-test-id=\"phone\"] span.input__sub")).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void missPlacedPlusSign() {
        open("http://localhost:9999/");
        $(By.name("name")).setValue("Иванов Иван");
        $(By.name("phone")).setValue("7+9991234567");
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("span[data-test-id=\"phone\"] span.input__sub")).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void emptyPhone() {
        open("http://localhost:9999/");
        $(By.name("name")).setValue("Иванов Иван");
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("span[data-test-id=\"phone\"] span.input__sub")).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void noAgreement() {
        open("http://localhost:9999/");
        $(By.name("name")).setValue("Иванов Иван");
        $(By.name("phone")).setValue("+79991234567");
        $(By.cssSelector("button[type=\"button\"]")).click();
        $(By.cssSelector("label[data-test-id=\"agreement\"]")).shouldHave(cssClass("input_invalid"));
    }
}