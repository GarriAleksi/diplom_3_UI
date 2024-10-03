import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.practicum.*;
import ru.yandex.practicum.constant.ButtonNameForLogin;
import ru.yandex.practicum.generator.UserGenerator;
import ru.yandex.practicum.user.User;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class BaseTest {
    private static final String SITE = "https://stellarburgers.nomoreparties.site";
    protected WebDriver driver;
    private final UserGenerator userGenerator = new UserGenerator();
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected RegisterPage registerPage;
    protected RestorePasswordPage restorePasswordPage;
    protected UserPage userPage;
    protected User user;

    @Before
    @Step("Настройка тестового окружения и создание тестового пользователя")
    public void setUp() {
        // Получаем выбор браузера из системной проперти или переменной окружения
        String browser = System.getProperty("browser", System.getenv("BROWSER"));

        if (browser == null || browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(SITE);
        initializePages();
        createUser();
    }

    @After
    @Step("Удаление тестового пользователя и завершение теста")
    public void tearDown() {
        deleteUser();
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Инициализация Page Object-ов")
    private void initializePages() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        restorePasswordPage = new RestorePasswordPage(driver);
        userPage = new UserPage(driver);
    }

    @Step("Создание тестового пользователя через API")
    private void createUser() {
        user = userGenerator.getUser();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .post(SITE + "/api/auth/register");
    }

    @Step("Удаление тестового пользователя через API")
    private void deleteUser() {
        // Логин и получение accessToken
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .post(SITE + "/api/auth/login");

        String accessToken = response
                .then()
                .extract()
                .path("accessToken");

        // Удаление пользователя
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .delete(SITE + "/api/auth/user");
    }

    @Step("Нажатие на кнопку для авторизации: {buttonName}")
    public void clickButton(ButtonNameForLogin buttonName) {
        switch (buttonName) {
            case LOGIN_ON_HOME_PAGE:
                homePage.clickLoginButton();
                break;
            case LOGIN_ON_LK:
                homePage.clickLk();
                break;
            case LOGIN_ON_REGISTER_PAGE:
                homePage.clickLk();
                loginPage.clickRegister();
                registerPage.clickLogin();
                break;
            case LOGIN_ON_RECOVERY_PASSWORD:
                homePage.clickLk();
                loginPage.clickRestorePasswordButton();
                restorePasswordPage.clickLoginButton();
                break;
            default:
                throw new IllegalArgumentException("Некорректное значение buttonName: " + buttonName);
        }
    }
}
