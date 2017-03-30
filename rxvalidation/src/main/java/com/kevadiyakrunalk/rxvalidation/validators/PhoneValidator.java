package com.kevadiyakrunalk.rxvalidation.validators;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.Validator;
import com.kevadiyakrunalk.rxvalidation.phoneformat.PhoneFormat;

import java.util.regex.Pattern;

import rx.Observable;

/**
 * The type Phone validator.
 */
public class PhoneValidator implements Validator<EditText> {

    private static final String DEFAULT_MESSAGE = "Invalid phone";
    private String invalidValueMessage;
    private Pattern pattern;
    private PhoneFormat phoneFormat;

    /**
     * Instantiates a new Phone validator.
     */
    public PhoneValidator() {
        invalidValueMessage = DEFAULT_MESSAGE;
        pattern = Patterns.PHONE;
    }

    /**
     * Instantiates a new Phone validator.
     *
     * @param invalidPhoneMessage the invalid phone message
     * @param phoneFormat         the phone format
     */
    public PhoneValidator(String invalidPhoneMessage, PhoneFormat phoneFormat) {
        invalidValueMessage = invalidPhoneMessage;
        this.phoneFormat = phoneFormat;
    }

    /**
     * Instantiates a new Phone validator.
     *
     * @param invalidPhoneMessage the invalid phone message
     */
    public PhoneValidator(String invalidPhoneMessage) {
        invalidValueMessage = invalidPhoneMessage;
        pattern = Patterns.PHONE;
    }

    /**
     * Instantiates a new Phone validator.
     *
     * @param invalidPhoneMessage the invalid phone message
     * @param pattern             the pattern
     */
    public PhoneValidator(String invalidPhoneMessage, Pattern pattern) {
        invalidValueMessage = invalidPhoneMessage;
        this.pattern = pattern;
    }

    /**
     * Instantiates a new Phone validator.
     *
     * @param invalidPhoneMessage the invalid phone message
     * @param pattern             the pattern
     */
    public PhoneValidator(String invalidPhoneMessage, String pattern) {
        this.invalidValueMessage = invalidPhoneMessage;
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
        return !TextUtils.isEmpty(value) &&
                (pattern != null ? pattern.matcher(value).matches() : phoneFormat.isPhoneNumberValid(value));
    }
}
