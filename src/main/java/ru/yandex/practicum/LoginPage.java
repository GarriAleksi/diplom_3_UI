package ru.yandex.practicum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final By loginHeader = By.xpath(".//h2[text() = 'Вход']");
    private final By allFieldsLoginForm = By.xpath(".//*[@class='text input__textfield text_type_main-default']");
    private final By loginButton = By.xpath(".//button[text() = 'Войти']");
    private final By registerButton = By.linkText("Зарегистрироваться");
    private final By restorePassword = By.linkText("Восстановить пароль");

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
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver -> (driver.findElement(loginHeader).getText() != null
                && !driver.findElement(loginHeader).getText().isEmpty()
        ));
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

    private WebElement getEmailField() {
        return driver.findElements(allFieldsLoginForm).get(0);
    }

    private WebElement getPasswordField() {
        return driver.findElements(allFieldsLoginForm).get(1);
    }
}