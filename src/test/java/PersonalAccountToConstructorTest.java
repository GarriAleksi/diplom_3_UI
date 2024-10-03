import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.constant.ButtonNameForConstructor;

import static org.junit.Assert.assertEquals;
import static ru.yandex.practicum.constant.ButtonNameForConstructor.CONSTRUCTOR;
import static ru.yandex.practicum.constant.ButtonNameForConstructor.LOGO_STELLAR_BURGER;

@RunWith(Parameterized.class)
public class PersonalAccountToConstructorTest extends BaseTest {
    private final ButtonNameForConstructor buttonName;  // Изменено на final для безопасности

    public PersonalAccountToConstructorTest(ButtonNameForConstructor buttonName) {
        this.buttonName = buttonName;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {CONSTRUCTOR},
                {LOGO_STELLAR_BURGER}
        };
    }

    @Test
    @DisplayName("Переход из ЛК в Конструктор")
    public void transitionToConstructorFromLk() {
        transitionToLk();  // Переход в личный кабинет

        userPage.waitLoadingPage()
                .changeButton(buttonName);  // Изменение кнопки

        String expectedUrl = "https://stellarburgers.nomoreparties.site/";
        String actualUrl = driver.getCurrentUrl();  // Извлечение текущего URL

        assertEquals("Переход на неправильный URL", expectedUrl, actualUrl);  // Улучшенное сообщение об ошибке
    }

    @Step("Переход в ЛК")
    private void transitionToLk() {
        homePage.clickLk();  // Клик на ЛК
        loginPage.waitLoadHeader()
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .clickLogin();
        homePage.clickLk();  // Повторный клик на ЛК
    }
}