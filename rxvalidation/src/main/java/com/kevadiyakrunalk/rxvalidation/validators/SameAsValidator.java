package com.kevadiyakrunalk.rxvalidation.validators;

import android.widget.EditText;
import android.widget.TextView;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.Validator;

import rx.Observable;

/**
 * The type Same as validator.
 */
public class SameAsValidator implements Validator<EditText> {
    /**
     * The constant DEFAULT_MESSAGE.
     */
    public static final String DEFAULT_MESSAGE = "Must be the same";
    private String sameAsMessage;
    private TextView anotherTextView;

    /**
     * Instantiates a new Same as validator.
     */
    public SameAsValidator() {
        sameAsMessage = DEFAULT_MESSAGE;
    }

    /**
     * Instantiates a new Same as validator.
     *
     * @param anotherTextView the another text view
     * @param message         the message
     */
    public SameAsValidator(TextView anotherTextView, String message) {
        this.anotherTextView = anotherTextView;
        this.sameAsMessage = message;
    }

    @Override
    public Observable<RxValidationResult<EditText>> validate(String text, EditText item) {
        if (anotherTextView.getText().toString().equals(text)) {
            return Observable.just(RxValidationResult.createSuccess(item));
        }

        return Observable.just(RxValidationResult.createImproper(item, sameAsMessage));
    }
}
