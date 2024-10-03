import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.yandex.practicum.HomePage;
import ru.yandex.practicum.LoginPage;
import ru.yandex.practicum.RegisterPage;
import ru.yandex.practicum.generator.UserGenerator;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class RegistrationFunctionalityTest extends BaseTest {

    private static final String INCORRECT_PASSWORD_MESSAGE = "Некорректный пароль";
    private static final String SITE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String EXPECTED_URL_AFTER_REGISTRATION = "https://stellarburgers.nomoreparties.site/login";

    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private final UserGenerator userGenerator = new UserGenerator();

    @Override
    @Step("Инициализация страниц перед тестами")
    public void setUp() {
        super.setUp();
        driver.get(SITE_URL);

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test
    @DisplayName("Корректная регистрация нового пользователя")
    public void shouldRegisterValidUser() {
        String name = userGenerator.getName();
        String email = userGenerator.getEmail();
        String password = userGenerator.getValidPassword();

        navigateToRegisterPage();
        registerNewUser(name, email, password);

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

    @Step("Удаление пользователя с email {0}")
    private void deleteUser(String email, String password) {
        String accessToken = loginPage.loginAndGetAccessToken(email, password);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete("https://stellarburgers.nomoreparties.site/api/auth/user")
                .then()
                .statusCode(200);
    }
}
