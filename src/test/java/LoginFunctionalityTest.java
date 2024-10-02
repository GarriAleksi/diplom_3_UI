import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.constant.ButtonNameForLogin;

import static org.junit.Assert.assertEquals;
import static ru.yandex.practicum.constant.ButtonNameForLogin.*;

@RunWith(Parameterized.class)
public class LoginFunctionalityTest extends BaseTest {
    public static final String HOME_URL = "https://stellarburgers.nomoreparties.site/login";
    private final ButtonNameForLogin nameButtonLogin;

    public LoginFunctionalityTest(ButtonNameForLogin nameButtonLogin) {
        this.nameButtonLogin = nameButtonLogin;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {LOGIN_ON_HOME_PAGE},
                {LOGIN_ON_LK},
                {LOGIN_ON_REGISTER_PAGE},
                {LOGIN_ON_RECOVERY_PASSWORD}
        };
    }

    @Test
    @DisplayName("Авторизация в ЛК")
    public void loginTest() {
        clickLoginButton(nameButtonLogin);
        loginWithCredentials(user.getEmail(), user.getPassword());
        verifyUrl(HOME_URL);
    }

    @Step("Клик по кнопке {nameButtonLogin}")
    private void clickLoginButton(ButtonNameForLogin nameButtonLogin) {
        clickButton(nameButtonLogin);  // Клик по кнопке авторизации
    }

    @Step("Ввод email {email} и пароля, и клик по кнопке 'Войти'")
    private void loginWithCredentials(String email, String password) {
        loginPage.waitLoadHeader()  // Ожидание загрузки заголовка
                .setEmail(email)  // Установка email
                .setPassword(password)  // Установка пароля
                .clickLogin();  // Клик по кнопке "Войти"
        homePage.waitLoadHeader();  // Ожидание загрузки домашней страницы
    }

    @Step("Проверка, что текущий URL совпадает с ожидаемым {expectedUrl}")
    private void verifyUrl(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();  // Получение текущего URL
        assertEquals("URL не совпадает с ожидаемым", expectedUrl, actualUrl);  // Проверка на совпадение URL
    }
}
