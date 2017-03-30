package com.kevadiyakrunalk.rxvalidation.validators;

import android.text.TextUtils;
import android.widget.EditText;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.Validator;

import rx.Observable;

/**
 * The type Non empty validator.
 */
public class NonEmptyValidator implements Validator<EditText> {
    private static final String DEFAULT_MESSAGE = "Cannot be empty";
    private String cannotBeEmptyMessage;

    /**
     * Instantiates a new Non empty validator.
     */
    public NonEmptyValidator() {
        cannotBeEmptyMessage = DEFAULT_MESSAGE;
    }

    /**
     * Instantiates a new Non empty validator.
     *
     * @param cannotBeEmptyMessage the cannot be empty message
     */
    public NonEmptyValidator(String cannotBeEmptyMessage) {
        this.cannotBeEmptyMessage = cannotBeEmptyMessage;
    }

    @Override
    public Observable<RxValidationResult<EditText>> validate(String text, EditText item) {
        if (isEmpty(text)) {
            return Observable.just(RxValidationResult.createImproper(item, cannotBeEmptyMessage));
        }
        return Observable.just(RxValidationResult.createSuccess(item));
    }

    private boolean isEmpty(String value) {
        return TextUtils.isEmpty(value) || value.trim().length() == 0;
    }
}
