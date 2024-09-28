import io.restassured.http.ContentType;
import io.restassured.response.Response; // Импортируем Response
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.practicum.*;
import ru.yandex.practicum.constant.ButtonNameForLogin;
import ru.yandex.practicum.generator.UserGenerator;
import ru.yandex.practicum.user.User;

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
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(SITE);
        initializePages();
        createUser();
    }

    @After
    public void tearDown() {
        deleteUser();
        if (driver != null) {
            driver.quit();
        }
    }

    // Метод для инициализации страниц (Page Objects)
    private void initializePages() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        restorePasswordPage = new RestorePasswordPage(driver);
        userPage = new UserPage(driver);
    }

    // Создание тестового пользователя через API
    private void createUser() {
        user = userGenerator.getUser();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .post(SITE + "/api/auth/register");
    }

    // Удаление тестового пользователя через API
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

    // Выполнение действий по нажатию различных кнопок для входа
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
