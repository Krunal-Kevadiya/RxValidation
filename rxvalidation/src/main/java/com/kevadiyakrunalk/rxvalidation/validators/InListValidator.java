package com.kevadiyakrunalk.rxvalidation.validators;

import android.widget.EditText;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.Validator;

import java.util.List;

import rx.Observable;

/**
 * The type In list validator.
 */
public class InListValidator implements Validator<EditText> {
    private final String errorMessage;
    private final List<String> properValues;

    /**
     * Instantiates a new In list validator.
     *
     * @param errorMessage the error message
     * @param properValues the proper values
     */
    public InListValidator(String errorMessage, List<String> properValues) {
        this.errorMessage = errorMessage;
        this.properValues = properValues;
    }

    @Override
    public Observable<RxValidationResult<EditText>> validate(String text, EditText item) {
        if (properValues != null && properValues.contains(text)) {
            return Observable.just(RxValidationResult.createSuccess(item));
        }

        return Observable.just(RxValidationResult.createImproper(item, errorMessage));
    }
}
