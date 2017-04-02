package com.krunal.rxvalidation;

import android.widget.EditText;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.Validator;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/*
Example external validator. It could for example make a call to external API to validate email
 */
public class ExternalApiEmailValidator implements Validator<EditText> {

    @Override
    public Observable<RxValidationResult<EditText>> validate(String text, EditText item) {
        RxValidationResult<EditText> result;
        if (text.equals("demo@yahoo.in")) {
            result = RxValidationResult.createSuccess(item);
        } else {
            result = RxValidationResult.createImproper(item, "Improper mail");
        }

        return Observable.just(result).delay(1, TimeUnit.SECONDS);
    }
}
