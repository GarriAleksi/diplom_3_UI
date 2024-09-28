import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.practicum.LoginPage;
import ru.yandex.practicum.RegisterPage;
import ru.yandex.practicum.generator.UserGenerator;
import ru.yandex.practicum.HomePage;

import static org.junit.Assert.assertEquals;

public class RegistrationFunctionalityTest {

    private static final String INCORRECT_PASSWORD_MESSAGE = "Некорректный пароль";
    private static final String SITE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String EXPECTED_URL_AFTER_REGISTRATION = "https://stellarburgers.nomoreparties.site/login";

    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private final UserGenerator userGenerator = new UserGenerator();

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(SITE_URL);

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Корректная регистрация нового пользователя")
    public void shouldRegisterValidUser() {
        navigateToRegisterPage();
        registerNewUser(userGenerator.getName(), userGenerator.getEmail(), userGenerator.getValidPassword());

        verifyUserIsRedirectedToLogin();
    }

    @Test
    @DisplayName("Регистрация с некорректным паролем (меньше 6 символов)")
    public void shouldNotRegisterUserWithInvalidPassword() {
        navigateToRegisterPage();
        registerNewUser(userGenerator.getName(), userGenerator.getEmail(), userGenerator.getInvalidPassword());

        verifyInvalidPasswordErrorDisplayed();
    }

    @Step("Переход на форму регистрации")
    private void navigateToRegisterPage() {
        homePage.clickLk();
        loginPage.clickRegister();
    }

    @Step("Регистрация нового пользователя с именем {0}, email {1}, паролем {2}")
    private void registerNewUser(String name, String email, String password) {
        registerPage.setName(name)
                .setEmail(email)
                .setPassword(password)
                .clickRegisterButton();
    }

    @Step("Проверка перенаправления на страницу входа после успешной регистрации")
    private void verifyUserIsRedirectedToLogin() {
        loginPage.waitLoadHeader();
        String actualUrl = driver.getCurrentUrl();
        assertEquals("URL после регистрации не совпадает", EXPECTED_URL_AFTER_REGISTRATION, actualUrl);
    }

    @Step("Проверка сообщения об ошибке для некорректного пароля")
    private void verifyInvalidPasswordErrorDisplayed() {
        String actualError = registerPage.getTextException();
        assertEquals("Сообщение об ошибке пароля неверно", INCORRECT_PASSWORD_MESSAGE, actualError);
    }
}
