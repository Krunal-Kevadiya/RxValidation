package com.kevadiyakrunalk.rxvalidation.validators;

import android.text.TextUtils;
import android.widget.EditText;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.Validator;

import rx.Observable;

/**
 * The type Max length validator.
 */
public class MaxLengthValidator implements Validator<EditText> {
    private static final String DEFAULT_MESSAGE = "Bad length";

    private String lengthMessage;
    private int properLength;

    /**
     * Instantiates a new Max length validator.
     *
     * @param properLength the proper length
     */
    public MaxLengthValidator(int properLength) {
        this(properLength, DEFAULT_MESSAGE);
    }

    /**
     * Instantiates a new Max length validator.
     *
     * @param properLength  the proper length
     * @param lengthMessage the length message
     */
    public MaxLengthValidator(int properLength, String lengthMessage) {
        this.lengthMessage = lengthMessage;
        this.properLength = properLength;
    }

    @Override
    public Observable<RxValidationResult<EditText>> validate(String text, EditText item) {
        if (TextUtils.isEmpty(text)) {
            return Observable.just(RxValidationResult.createImproper(item, lengthMessage));
        }

        if (text.trim().length() <= properLength) {
            return Observable.just(RxValidationResult.createSuccess(item));
        }

        return Observable.just(RxValidationResult.createImproper(item, lengthMessage));
    }
}
