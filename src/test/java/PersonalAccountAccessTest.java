import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import ru.yandex.practicum.PersonalAccountPage;

public class PersonalAccountAccessTest {
    private static final String SITE_URL = "https://stellarburgers.nomoreparties.site";
    private WebDriver driver;
    private PersonalAccountPage personalAccountPage;

    @Before
    @Step("Настройка браузера Chrome")
    public void setupBrowser() {
        // Использование только ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(SITE_URL);
        personalAccountPage = new PersonalAccountPage(driver); // Инициализация страницы
    }

    @After
    @Step("Закрытие браузера")
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    @Step("Тест: Переход на страницу личного кабинета")
    public void clickPersonalAccountTest() {
        // Использование метода из класса страницы
        personalAccountPage.clickPersonalAccountLink();
    }
}
