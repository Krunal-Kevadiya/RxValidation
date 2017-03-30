package com.kevadiyakrunalk.rxvalidation.validators;

import android.text.TextUtils;
import android.widget.EditText;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.Validator;

import java.util.regex.Pattern;

import rx.Observable;

/**
 * The type Pattern matches validator.
 */
public class PatternMatchesValidator implements Validator<EditText> {

    private static final String DEFAULT_MESSAGE = "Invalid value";
    private String invalidValueMessage;
    private Pattern pattern;

    /**
     * Instantiates a new Pattern matches validator.
     */
    public PatternMatchesValidator() {
        this.invalidValueMessage = DEFAULT_MESSAGE;
        this.pattern = android.util.Patterns.EMAIL_ADDRESS;
    }

    /**
     * Instantiates a new Pattern matches validator.
     *
     * @param invalidValueMessage the invalid value message
     */
    public PatternMatchesValidator(String invalidValueMessage) {
        this.invalidValueMessage = invalidValueMessage;
        this.pattern = android.util.Patterns.EMAIL_ADDRESS;
    }

    /**
     * Instantiates a new Pattern matches validator.
     *
     * @param invalidValueMessage the invalid value message
     * @param pattern             the pattern
     */
    public PatternMatchesValidator(String invalidValueMessage, Pattern pattern) {
        this.invalidValueMessage = invalidValueMessage;
        this.pattern = pattern;
    }

    /**
     * Instantiates a new Pattern matches validator.
     *
     * @param invalidValueMessage the invalid value message
     * @param pattern             the pattern
     */
    public PatternMatchesValidator(String invalidValueMessage, String pattern) {
        this.invalidValueMessage = invalidValueMessage;
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public Observable<RxValidationResult<EditText>> validate(String text, EditText item) {
        return Observable.just(validatePattern(item, text));
    }

    private RxValidationResult<EditText> validatePattern(EditText view, String value) {
        if (nonEmptyAndMatchPattern(value)) {
            return RxValidationResult.createSuccess(view);
        }
        return RxValidationResult.createImproper(view, invalidValueMessage);
    }

    private boolean nonEmptyAndMatchPattern(String value) {
        return !TextUtils.isEmpty(value) && pattern.matcher(value).matches();
    }
}
