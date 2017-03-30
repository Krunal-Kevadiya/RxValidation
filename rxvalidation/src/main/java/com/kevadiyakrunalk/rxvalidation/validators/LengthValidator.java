package com.kevadiyakrunalk.rxvalidation.validators;

import android.text.TextUtils;
import android.widget.EditText;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.Validator;

import rx.Observable;

/**
 * The type Length validator.
 */
public class LengthValidator implements Validator<EditText> {
    private static final String DEFAULT_MESSAGE = "Bad length";

    private String lengthMessage;
    private int properLength;

    /**
     * Instantiates a new Length validator.
     *
     * @param properLength the proper length
     */
    public LengthValidator(int properLength) {
        this(properLength, DEFAULT_MESSAGE);
    }

    /**
     * Instantiates a new Length validator.
     *
     * @param properLength  the proper length
     * @param lengthMessage the length message
     */
    public LengthValidator(int properLength, String lengthMessage) {
        this.lengthMessage = lengthMessage;
        this.properLength = properLength;
    }

    @Override
    public Observable<RxValidationResult<EditText>> validate(String text, EditText item) {
        if (TextUtils.isEmpty(text)) {
            return Observable.just(RxValidationResult.createImproper(item, lengthMessage));
        }

        if (text.trim().length() == properLength) {
            return Observable.just(RxValidationResult.createSuccess(item));
        }

        return Observable.just(RxValidationResult.createImproper(item, lengthMessage));
    }
}
