package ru.yandex.practicum.generator;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.practicum.user.User;

public class UserGenerator {

    private static final int NAME_LENGTH = 10;
    private static final int EMAIL_PREFIX_LENGTH = 5;
    private static final int EMAIL_DOMAIN_LENGTH = 4;
    private static final int VALID_PASSWORD_LENGTH = 8;
    private static final int INVALID_PASSWORD_LENGTH = 5;

    public User getUser() {
        return new User(getName(), getEmail(), getValidPassword());
    }

    public String getName() {
        return RandomStringUtils.randomAlphabetic(NAME_LENGTH);
    }

    public String getEmail() {
        return String.format("%s@%s.ru",
                RandomStringUtils.randomAlphabetic(EMAIL_PREFIX_LENGTH),
                RandomStringUtils.randomAlphabetic(EMAIL_DOMAIN_LENGTH));
    }

    public String getValidPassword() {
        return RandomStringUtils.randomAlphanumeric(VALID_PASSWORD_LENGTH);
    }

    public String getInvalidPassword() {
        return RandomStringUtils.randomAlphanumeric(INVALID_PASSWORD_LENGTH);
    }
}