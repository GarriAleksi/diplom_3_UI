package ru.yandex.practicum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.practicum.constant.ButtonNameForConstructor;

import java.time.Duration;

public class UserPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By profileButton = By.xpath(".//*[text()='Профиль']");
    private final By allUserFields = By.xpath(".//*[@class='text input__textfield text_type_main-default input__textfield-disabled']");
    private final By constructorButton = By.linkText("Конструктор");
    private final By logoButton = By.xpath(".//*[@class='AppHeader_header__logo__2D0X2']");
    private final By exitButton = By.xpath(".//button[text()='Выход']");

    public UserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    /**
     * Ожидает загрузку страницы пользователя.
     *
     * @return текущий экземпляр UserPage
     */
    public UserPage waitLoadingPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileButton));
        return this;
    }

    /**
     * Получает имя пользователя.
     *
     * @return имя пользователя
     */
    public String getUserName() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allUserFields)).get(0).getAttribute("value");
    }

    /**
     * Получает логин пользователя.
     *
     * @return логин пользователя
     */
    public String getUserLogin() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allUserFields)).get(1).getAttribute("value");
    }

    /**
     * Нажимает на кнопку "Конструктор".
     */
    public void clickConstructorButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(constructorButton));
        button.click();
    }

    /**
     * Нажимает на логотип.
     */
    public void clickLogo() {
        WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(logoButton));
        logo.click();
    }

    /**
     * Нажимает на кнопку "Выход".
     */
    public void clickExit() {
        WebElement exit = wait.until(ExpectedConditions.elementToBeClickable(exitButton));
        exit.click();
    }

    /**
     * Нажимает на кнопку в зависимости от переданного значения.
     *
     * @param buttonName кнопка, на которую нужно нажать
     */
    public void changeButton(ButtonNameForConstructor buttonName) {
        switch (buttonName) {
            case CONSTRUCTOR:
                clickConstructorButton();
                break;
            case LOGO_STELLAR_BURGER:
                clickLogo();
                break;
            default:
                throw new IllegalArgumentException("Неизвестное значение ButtonNameForConstructor: " + buttonName);
        }
    }
}
