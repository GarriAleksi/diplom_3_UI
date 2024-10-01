import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.practicum.PersonalAccountPage;

public class PersonalAccountAccessTest {
    private static final String SITE_URL = "https://stellarburgers.nomoreparties.site";
    private WebDriver driver;
    private PersonalAccountPage personalAccountPage;

    @Before
    public void setupBrowser() {
        // Автоматическое управление драйвером Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(SITE_URL);
        personalAccountPage = new PersonalAccountPage(driver); // Инициализация страницы
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void clickPersonalAccountTest() {
        // Использование метода из класса страницы
        personalAccountPage.clickPersonalAccountLink();

        // Добавьте дополнительные проверки после клика, если необходимо
    }
}