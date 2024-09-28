import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.practicum.constant.SectionName;
import ru.yandex.practicum.HomePage;

import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.constant.SectionName.*;

@Slf4j
@RunWith(Parameterized.class)
public class NavigationConstructorTest {
    private static final String SITE = "https://stellarburgers.nomoreparties.site";
    private WebDriver driver;
    private HomePage homePage;
    private SectionName sectionName;
    private final String attribute = "tab_tab_type_current";

    public NavigationConstructorTest(SectionName sectionName) {
        this.sectionName = sectionName;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {BUN},
                {SAUCE},
                {FILLING}
        };
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();  // Открытие браузера на полный экран
        driver.get(SITE);
        homePage = new HomePage(driver);
        log.info("Сайт открыт: {}", SITE);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Браузер закрыт");
        }
    }

    @Test
    @DisplayName("Переход по разделам Конструктора")
    public void clickSectionTest() {
        log.info("Тестирование перехода на раздел: {}", sectionName);

        // Проверка, активна ли вкладка
        String currentClass = homePage.getClassName(sectionName);
        log.info("Текущий класс раздела {}: {}", sectionName, currentClass);

        if (!currentClass.contains(attribute)) {
            log.info("Вкладка не активна, кликаем на раздел {}", sectionName);
            homePage.clickSection(sectionName);
        } else {
            log.info("Вкладка уже активна: {}", sectionName);
        }

        // Проверка класса после клика
        String actual = homePage.getClassName(sectionName);
        log.info("Проверяем класс после клика: {}", actual);
        assertTrue("Класс не содержит ожидаемый атрибут", actual.contains(attribute));
    }
}
