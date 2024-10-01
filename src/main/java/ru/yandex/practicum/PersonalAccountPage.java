package ru.yandex.practicum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private static final String PERSONAL_ACCOUNT_LINK_XPATH = "//a[@class='AppHeader_header__link__3D_hX' and contains(., 'Личный Кабинет')]";
    private static final String MODAL_WINDOW_CLASS = "Modal_modal_overlay__x2ZCr";

    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickPersonalAccountLink() {
        // Ожидание исчезновения модального окна, если оно открыто
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(MODAL_WINDOW_CLASS)));

        // Находим элемент "Личный Кабинет"
        WebElement personalAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PERSONAL_ACCOUNT_LINK_XPATH)));

        // Кликаем по элементу
        personalAccountLink.click();
    }
}