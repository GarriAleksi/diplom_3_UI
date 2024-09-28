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
    private final ButtonNameForLogin nameButtonLogin;  // Сохранение имени кнопки

    public LoginFunctionalityTest(ButtonNameForLogin nameButtonLogin) {
        this.nameButtonLogin = nameButtonLogin;  // Инициализация кнопки
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
        clickButton(nameButtonLogin);  // Клик по кнопке авторизации

        loginPage.waitLoadHeader()  // Ожидание загрузки заголовка
                .setEmail(user.getEmail())  // Установка email
                .setPassword(user.getPassword())  // Установка пароля
                .clickLogin();  // Клик по кнопке "Войти"

        homePage.waitLoadHeader();  // Ожидание загрузки домашней страницы

        String actualUrl = driver.getCurrentUrl();  // Получение текущего URL

        assertEquals("URL не совпадает с ожидаемым", HOME_URL, actualUrl);  // Проверка на совпадение URL
    }
}
