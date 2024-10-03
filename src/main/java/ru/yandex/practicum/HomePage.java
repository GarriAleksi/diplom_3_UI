package ru.yandex.practicum;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.practicum.constant.SectionName;

import java.time.Duration;

public class HomePage {
    private static final String CLASS = "class";
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String activeClass = "tab_tab_type_current";  // Класс активной вкладки

    // Локаторы
    private final By buttonConstructor = By.xpath("//p[text()='Конструктор']");
    private final By buttonLk = By.xpath("//a[@class='AppHeader_header__link__3D_hX' and contains(., 'Личный Кабинет')]");
    private final By buttonLogin = By.xpath("//button[contains(text(),'Войти')]");
    private final By sectionBun = By.xpath(".//*[text()='Булки']//parent::div");
    private final By sectionSauce = By.xpath(".//*[text()='Соусы']//parent::div");
    private final By sectionFilling = By.xpath(".//*[text()='Начинки']//parent::div");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Метод для ожидания загрузки заголовка
    public void waitLoadHeader() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonConstructor));
    }

    // Общий метод для клика по разделу
    private void clickSection(By sectionLocator) {
        waitLoadHeader();
        driver.findElement(sectionLocator).click();
    }

    public void clickLk() {
        waitLoadHeader();
        driver.findElement(buttonLk).click();
    }

    public void clickLoginButton() {
        waitLoadHeader(); // Убедитесь, что заголовок загружен
        wait.until(ExpectedConditions.elementToBeClickable(buttonLogin)); // Явное ожидание
        if (driver.findElements(buttonLogin).size() > 0) {
            driver.findElement(buttonLogin).click();
        } else {
            System.out.println("Кнопка 'Войти' не найдена.");
        }
    }

    public void clickBun() {
        clickSection(sectionBun);
    }

    public void clickSauce() {
        clickSection(sectionSauce);
    }

    public void clickFilling() {
        clickSection(sectionFilling);
    }

    // Обновленный метод для клика по разделу с проверкой активности
    public void clickSection(SectionName sectionName) {
        // Проверяем, активна ли вкладка
        if (!isSectionActive(sectionName)) {
            switch (sectionName) {
                case BUN:
                    clickBun();
                    break;
                case SAUCE:
                    clickSauce();
                    break;
                case FILLING:
                    clickFilling();
                    break;
                default:
                    throw new IllegalArgumentException("Некорректное название элемента: " + sectionName);
            }
        } else {
            System.out.println("Вкладка уже активна: " + sectionName);
        }
    }

    public WebElement getSection(SectionName sectionName) {
        switch (sectionName) {
            case BUN:
                return driver.findElement(sectionBun);
            case SAUCE:
                return driver.findElement(sectionSauce);
            case FILLING:
                return driver.findElement(sectionFilling);
            default:
                throw new IllegalArgumentException("Некорректное название элемента: " + sectionName);
        }
    }

    // Метод для получения класса вкладки
    public String getClassName(SectionName sectionName) {
        return getSection(sectionName).getAttribute(CLASS);
    }

    // Новый метод для проверки, активна ли вкладка
    public boolean isSectionActive(SectionName sectionName) {
        String className = getClassName(sectionName);
        return className.contains(activeClass);  // Проверяем, содержит ли вкладка класс активности
    }
}
