package com.kevadiyakrunalk.rxvalidation;

import android.widget.EditText;

import java.util.List;

/**
 * The type Validation result helper.
 */
public class ValidationResultHelper {
    /**
     * Gets first bad result or success.
     *
     * @param results the results
     * @return the first bad result or success
     */
    public static RxValidationResult<EditText> getFirstBadResultOrSuccess(
            List<RxValidationResult<EditText>> results) {
        RxValidationResult<EditText> firstBadResult = null;
        for (RxValidationResult<EditText> result : results) {
            if (!result.isProper()) {
                firstBadResult = result;
                break;
            }
        }
        if (firstBadResult == null) {
            // if there is no bad result, then return first success
            return results.get(0);
        } else {
            return firstBadResult;
        }
    }
}
