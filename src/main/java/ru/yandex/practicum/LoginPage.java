package ru.yandex.practicum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.restassured.RestAssured.given;

public class LoginPage {
    private final WebDriver driver;
    private final By loginHeader = By.xpath(".//h2[text() = 'Вход']");
    private final By allFieldsLoginForm = By.xpath(".//*[@class='text input__textfield text_type_main-default']");
    private final By loginButton = By.xpath(".//button[text() = 'Войти']");
    private final By registerButton = By.linkText("Зарегистрироваться");
    private final By restorePassword = By.linkText("Восстановить пароль");

    private static final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/api/auth/login";

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void clickRestorePasswordButton() {
        driver.findElement(restorePassword).click();
    }

    public LoginPage waitLoadHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(loginHeader));
        return this;
    }

    public LoginPage setEmail(String newEmail) {
        getEmailField().sendKeys(newEmail);
        return this;
    }

    public LoginPage setPassword(String newPassword) {
        getPasswordField().sendKeys(newPassword);
        return this;
    }

    public String loginAndGetAccessToken(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLogin(); // Нажать кнопку "Войти"

        // Получение токена доступа после успешного входа
        String accessToken = given()
                .contentType("application/json")
                .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}")
                .when()
                .post(LOGIN_URL)
                .then()
                .statusCode(200) // Убедитесь, что код ответа 200
                .extract()
                .path("accessToken"); // Здесь мы предполагаем, что токен доступа возвращается в этом поле

        return accessToken;
    }

    private WebElement getEmailField() {
        return driver.findElements(allFieldsLoginForm).get(0);
    }

    private WebElement getPasswordField() {
        return driver.findElements(allFieldsLoginForm).get(1);
    }
}