package com.kevadiyakrunalk.rxvalidation.validators;

import android.text.TextUtils;
import android.widget.EditText;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.Validator;

import rx.Observable;

/**
 * The type Min length validator.
 */
public class MinLengthValidator implements Validator<EditText> {
    private static final String DEFAULT_MESSAGE = "Bad length";

    private String lengthMessage;
    private int properLength;

    /**
     * Instantiates a new Min length validator.
     *
     * @param properLength the proper length
     */
    public MinLengthValidator(int properLength) {
        this(properLength, DEFAULT_MESSAGE);
    }

    /**
     * Instantiates a new Min length validator.
     *
     * @param properLength  the proper length
     * @param lengthMessage the length message
     */
    public MinLengthValidator(int properLength, String lengthMessage) {
        this.lengthMessage = lengthMessage;
        this.properLength = properLength;
    }

    @Override
    public Observable<RxValidationResult<EditText>> validate(String text, EditText item) {
        if (TextUtils.isEmpty(text)) {
            return Observable.just(RxValidationResult.createImproper(item, lengthMessage));
        }

        if (text.trim().length() >= properLength) {
            return Observable.just(RxValidationResult.createSuccess(item));
        }

        return Observable.just(RxValidationResult.createImproper(item, lengthMessage));
    }
}
