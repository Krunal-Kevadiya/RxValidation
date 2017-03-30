package com.kevadiyakrunalk.rxvalidation.validators;

import java.util.regex.Pattern;

/**
 * The type Ip 4 validator.
 */
public class Ip4Validator extends PatternMatchesValidator {

    private static final String DEFAULT_MESSAGE = "Invalid IP";

    /**
     * Instantiates a new Ip 4 validator.
     */
    public Ip4Validator() {
        super(DEFAULT_MESSAGE, android.util.Patterns.IP_ADDRESS);
    }

    /**
     * Instantiates a new Ip 4 validator.
     *
     * @param invalidIpMessage the invalid ip message
     */
    public Ip4Validator(String invalidIpMessage) {
        super(invalidIpMessage, android.util.Patterns.IP_ADDRESS);
    }

    /**
     * Instantiates a new Ip 4 validator.
     *
     * @param invalidIpMessage the invalid ip message
     * @param pattern          the pattern
     */
    public Ip4Validator(String invalidIpMessage, Pattern pattern) {
        super(invalidIpMessage, pattern);
    }
}
