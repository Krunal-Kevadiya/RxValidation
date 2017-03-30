package com.kevadiyakrunalk.rxvalidation;

import android.widget.TextView;

/**
 * The type Rx validation result.
 *
 * @param <T> the type parameter
 */
public class RxValidationResult<T extends TextView> {

    private T item;
    private boolean isProper;
    private String message;

    public RxValidationResult(T item, boolean isProper, String message) {
        this.item = item;
        this.isProper = isProper;
        this.message = message;
    }

    /**
     * Create improper rx validation result.
     *
     * @param <T>     the type parameter
     * @param item    the item
     * @param message the message
     * @return the rx validation result
     */
    public static <T extends TextView> RxValidationResult<T> createImproper(T item, String message) {
        return new RxValidationResult<>(item, false, message);
    }

    /**
     * Create success rx validation result.
     *
     * @param <T>  the type parameter
     * @param item the item
     * @return the rx validation result
     */
    public static <T extends TextView> RxValidationResult<T> createSuccess(T item) {
        return new RxValidationResult<>(item, true, "");
    }

    /**
     * Gets validated text.
     *
     * @return the validated text
     */
    public String getValidatedText() {
        return item.getText().toString();
    }

    /**
     * Gets item.
     *
     * @return the item
     */
    public T getItem() {
        return item;
    }

    /**
     * Sets item.
     *
     * @param item the item
     */
    public void setItem(T item) {
        this.item = item;
    }

    /**
     * Is proper boolean.
     *
     * @return the boolean
     */
    public boolean isProper() {
        return isProper;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "RxValidationResult{" +
                "itemValue=" + item.getText().toString() +
                ", isProper=" + isProper +
                ", message='" + message + '\'' +
                '}';
    }
}
