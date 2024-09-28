import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountAccessTest {
    private static final String SITE_URL = "https://stellarburgers.nomoreparties.site";
    private static final String PERSONAL_ACCOUNT_LINK_XPATH = "//a[@class='AppHeader_header__link__3D_hX' and contains(., 'Личный Кабинет')]";
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setupBrowser() {
        // Автоматическое управление драйвером Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(SITE_URL);
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void clickPersonalAccountTest() {
        // Ожидание исчезновения модального окна, если оно открыто
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("Modal_modal_overlay__x2ZCr")));

        // Находим элемент "Личный Кабинет"
        WebElement personalAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PERSONAL_ACCOUNT_LINK_XPATH)));

        // Прокрутка к элементу, если он не виден
        scrollToElement(personalAccountLink);

        // Кликаем по элементу
        personalAccountLink.click();

        // Добавьте дополнительные проверки после клика, если необходимо
    }

    private void scrollToElement(WebElement element) {
        // Прокрутка к элементу
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
