package com.kevadiyakrunalk.rxvalidation.validators;

import android.text.TextUtils;
import android.widget.EditText;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.Validator;

import rx.Observable;

/**
 * The type Digit validator.
 */
public class DigitValidator implements Validator<EditText> {
    private static final String DEFAULT_MESSAGE = "Digits only";
    private String digitOnlyMessage;

    /**
     * Instantiates a new Digit validator.
     */
    public DigitValidator() {
        digitOnlyMessage = DEFAULT_MESSAGE;
    }

    /**
     * Instantiates a new Digit validator.
     *
     * @param digitOnlyMessage the digit only message
     */
    public DigitValidator(String digitOnlyMessage) {
        this.digitOnlyMessage = digitOnlyMessage;
    }

    @Override
    public Observable<RxValidationResult<EditText>> validate(String text, EditText item) {
        if (TextUtils.isDigitsOnly(text)) {
            return Observable.just(RxValidationResult.createSuccess(item));
        }
        return Observable.just(RxValidationResult.createImproper(item, digitOnlyMessage));
    }
}
