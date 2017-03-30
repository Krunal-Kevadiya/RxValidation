package com.kevadiyakrunalk.rxvalidation;

import android.widget.TextView;

import rx.Observable;

/**
 * The interface Validator.
 *
 * @param <T> the type parameter
 */
public interface Validator<T extends TextView> {
    /**
     * Validate observable.
     *
     * @param text the text
     * @param item the item
     * @return the observable
     */
    Observable<RxValidationResult<T>> validate(String text, T item);
}
