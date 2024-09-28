package ru.yandex.practicum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By registerHeader = By.xpath(".//h2[text() = 'Регистрация']");
    private final By allFieldsRegistrationForm = By.xpath(".//*[@class='text input__textfield text_type_main-default']");
    private final By registerButton = By.xpath(".//button[text() = 'Зарегистрироваться']");
    private final By exceptionPassword = By.xpath(".//*[@class='input__error text_type_main-default' and text()='Некорректный пароль']");
    private final By buttonLogin = By.linkText("Войти");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    /**
     * Ожидает загрузку страницы регистрации.
     *
     * @return текущий экземпляр RegisterPage
     */
    private RegisterPage waitDownloadPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerHeader));
        return this;
    }

    /**
     * Вводит имя в поле ввода.
     *
     * @param newName новое имя
     * @return текущий экземпляр RegisterPage
     */
    public RegisterPage setName(String newName) {
        waitDownloadPage();
        getNameField().sendKeys(newName);
        return this;
    }

    /**
     * Вводит email в поле ввода.
     *
     * @param newEmail новый email
     * @return текущий экземпляр RegisterPage
     */
    public RegisterPage setEmail(String newEmail) {
        getEmailField().sendKeys(newEmail);
        return this;
    }

    /**
     * Вводит пароль в поле ввода.
     *
     * @param newPassword новый пароль
     * @return текущий экземпляр RegisterPage
     */
    public RegisterPage setPassword(String newPassword) {
        getPasswordField().sendKeys(newPassword);
        return this;
    }

    /**
     * Нажимает на кнопку регистрации.
     */
    public void clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    /**
     * Получает текст исключения для некорректного пароля.
     *
     * @return текст исключения
     */
    public String getTextException() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(exceptionPassword)).getText();
    }

    /**
     * Нажимает на кнопку входа.
     */
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonLogin)).click();
    }

    /**
     * Получает поле ввода имени.
     *
     * @return поле ввода имени
     */
    private WebElement getNameField() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allFieldsRegistrationForm)).get(0);
    }

    /**
     * Получает поле ввода email.
     *
     * @return поле ввода email
     */
    private WebElement getEmailField() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allFieldsRegistrationForm)).get(1);
    }

    /**
     * Получает поле ввода пароля.
     *
     * @return поле ввода пароля
     */
    private WebElement getPasswordField() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allFieldsRegistrationForm)).get(2);
    }
}
