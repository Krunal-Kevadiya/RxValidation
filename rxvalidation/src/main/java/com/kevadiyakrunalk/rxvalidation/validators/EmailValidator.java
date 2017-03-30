package com.kevadiyakrunalk.rxvalidation.validators;

import java.util.regex.Pattern;

/**
 * The type Email validator.
 */
public class EmailValidator extends PatternMatchesValidator {

    private static final String DEFAULT_MESSAGE = "Invalid email";

    /**
     * Instantiates a new Email validator.
     */
    public EmailValidator() {
        super(DEFAULT_MESSAGE, android.util.Patterns.EMAIL_ADDRESS);
    }

    /**
     * Instantiates a new Email validator.
     *
     * @param invalidEmailMessage the invalid email message
     */
    public EmailValidator(String invalidEmailMessage) {
        super(invalidEmailMessage, android.util.Patterns.EMAIL_ADDRESS);
    }

    /**
     * Instantiates a new Email validator.
     *
     * @param invalidEmailMessage the invalid email message
     * @param pattern             the pattern
     */
    public EmailValidator(String invalidEmailMessage, Pattern pattern) {
        super(invalidEmailMessage, pattern);
    }
}
