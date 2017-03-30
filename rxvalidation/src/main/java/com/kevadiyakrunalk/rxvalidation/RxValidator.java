package com.kevadiyakrunalk.rxvalidation;

import android.widget.EditText;
import android.widget.TextView;

import com.kevadiyakrunalk.commonutils.widget.textview.RxTextView;
import com.kevadiyakrunalk.commonutils.widget.view.RxView;
import com.kevadiyakrunalk.rxvalidation.validators.AgeValidator;
import com.kevadiyakrunalk.rxvalidation.validators.DigitValidator;
import com.kevadiyakrunalk.rxvalidation.validators.EmailValidator;
import com.kevadiyakrunalk.rxvalidation.validators.InListValidator;
import com.kevadiyakrunalk.rxvalidation.validators.Ip4Validator;
import com.kevadiyakrunalk.rxvalidation.validators.LengthValidator;
import com.kevadiyakrunalk.rxvalidation.validators.MaxLengthValidator;
import com.kevadiyakrunalk.rxvalidation.validators.MinLengthValidator;
import com.kevadiyakrunalk.rxvalidation.validators.NonEmptyValidator;
import com.kevadiyakrunalk.rxvalidation.validators.PatternFindValidator;
import com.kevadiyakrunalk.rxvalidation.validators.PatternMatchesValidator;
import com.kevadiyakrunalk.rxvalidation.validators.SameAsValidator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * The type Rx validator.
 */
public class RxValidator {

    private List<Validator<EditText>> validators = new ArrayList<>();
    private List<Validator<EditText>> externalValidators = new ArrayList<>();
    private Observable<String> changeEmitter;
    private EditText et;

    private RxValidator(EditText et) {
        this.et = et;
    }

    /**
     * Create for rx validator.
     *
     * @param et the et
     * @return the rx validator
     */
    public static RxValidator createFor(EditText et) {
        return new RxValidator(et);
    }

    /**
     * On focus changed rx validator.
     *
     * @return the rx validator
     */
    public RxValidator onFocusChanged() {
        this.changeEmitter = RxView.focusChanges(et).skip(1).filter(hasFocus -> !hasFocus).map(aBoolean -> et.getText().toString());
        return this;
    }

    /**
     * On value changed rx validator.
     *
     * @return the rx validator
     */
    public RxValidator onValueChanged() {
        this.changeEmitter = RxTextView.textChanges(et).skip(1).map(charSequence -> charSequence.toString());
        return this;
    }

    /**
     * On subscribe rx validator.
     *
     * @return the rx validator
     */
    public RxValidator onSubscribe() {
        this.changeEmitter = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(et.getText().toString());
                subscriber.onCompleted();
            }
        });
        return this;
    }

    /**
     * Email rx validator.
     *
     * @return the rx validator
     */
    public RxValidator email() {
        this.validators.add(new EmailValidator());
        return this;
    }

    /**
     * Email rx validator.
     *
     * @param invalidEmailMessage the invalid email message
     * @return the rx validator
     */
    public RxValidator email(String invalidEmailMessage) {
        this.validators.add(new EmailValidator(invalidEmailMessage));
        return this;
    }

    /**
     * Email rx validator.
     *
     * @param invalidEmailMessage the invalid email message
     * @param pattern             the pattern
     * @return the rx validator
     */
    public RxValidator email(String invalidEmailMessage, Pattern pattern) {
        this.validators.add(new EmailValidator(invalidEmailMessage, pattern));
        return this;
    }

    /**
     * Ip 4 rx validator.
     *
     * @return the rx validator
     */
    public RxValidator ip4() {
        this.validators.add(new Ip4Validator());
        return this;
    }

    /**
     * Ip 4 rx validator.
     *
     * @param invalidIp4Message the invalid ip 4 message
     * @return the rx validator
     */
    public RxValidator ip4(String invalidIp4Message) {
        this.validators.add(new Ip4Validator(invalidIp4Message));
        return this;
    }

    /**
     * Pattern matches rx validator.
     *
     * @param invalidValueMessage the invalid value message
     * @param pattern             the pattern
     * @return the rx validator
     */
    public RxValidator patternMatches(String invalidValueMessage, Pattern pattern) {
        this.validators.add(new PatternMatchesValidator(invalidValueMessage, pattern));
        return this;
    }

    /**
     * Pattern matches rx validator.
     *
     * @param invalidValueMessage the invalid value message
     * @param pattern             the pattern
     * @return the rx validator
     */
    public RxValidator patternMatches(String invalidValueMessage, String pattern) {
        this.validators.add(new PatternMatchesValidator(invalidValueMessage, pattern));
        return this;
    }

    /**
     * Pattern find rx validator.
     *
     * @param invalidValueMessage the invalid value message
     * @param pattern             the pattern
     * @return the rx validator
     */
    public RxValidator patternFind(String invalidValueMessage, Pattern pattern) {
        this.validators.add(new PatternFindValidator(invalidValueMessage, pattern));
        return this;
    }

    /**
     * Pattern find rx validator.
     *
     * @param invalidValueMessage the invalid value message
     * @param pattern             the pattern
     * @return the rx validator
     */
    public RxValidator patternFind(String invalidValueMessage, String pattern) {
        this.validators.add(new PatternFindValidator(invalidValueMessage, pattern));
        return this;
    }

    /**
     * Non empty rx validator.
     *
     * @return the rx validator
     */
    public RxValidator nonEmpty() {
        this.validators.add(new NonEmptyValidator());
        return this;
    }

    /**
     * Non empty rx validator.
     *
     * @param cannotBeEmptyMessage the cannot be empty message
     * @return the rx validator
     */
    public RxValidator nonEmpty(String cannotBeEmptyMessage) {
        this.validators.add(new NonEmptyValidator(cannotBeEmptyMessage));
        return this;
    }

    /**
     * Digit only rx validator.
     *
     * @return the rx validator
     */
    public RxValidator digitOnly() {
        this.validators.add(new DigitValidator());
        return this;
    }

    /**
     * Digit only rx validator.
     *
     * @param digitOnlyErrorMessage the digit only error message
     * @return the rx validator
     */
    public RxValidator digitOnly(String digitOnlyErrorMessage) {
        this.validators.add(new DigitValidator(digitOnlyErrorMessage));
        return this;
    }

    /**
     * Min length rx validator.
     *
     * @param length the length
     * @return the rx validator
     */
    public RxValidator minLength(int length) {
        this.validators.add(new MinLengthValidator(length));
        return this;
    }

    /**
     * Min length rx validator.
     *
     * @param length           the length
     * @param badLengthMessage the bad length message
     * @return the rx validator
     */
    public RxValidator minLength(int length, String badLengthMessage) {
        this.validators.add(new MinLengthValidator(length, badLengthMessage));
        return this;
    }

    /**
     * Max length rx validator.
     *
     * @param length the length
     * @return the rx validator
     */
    public RxValidator maxLength(int length) {
        this.validators.add(new MaxLengthValidator(length));
        return this;
    }

    /**
     * Max length rx validator.
     *
     * @param length           the length
     * @param badLengthMessage the bad length message
     * @return the rx validator
     */
    public RxValidator maxLength(int length, String badLengthMessage) {
        this.validators.add(new MaxLengthValidator(length, badLengthMessage));
        return this;
    }

    /**
     * Length rx validator.
     *
     * @param length the length
     * @return the rx validator
     */
    public RxValidator length(int length) {
        this.validators.add(new LengthValidator(length));
        return this;
    }

    /**
     * Length rx validator.
     *
     * @param length           the length
     * @param badLengthMessage the bad length message
     * @return the rx validator
     */
    public RxValidator length(int length, String badLengthMessage) {
        this.validators.add(new LengthValidator(length, badLengthMessage));
        return this;
    }

    /**
     * Age rx validator.
     *
     * @param badAgeMessage the bad age message
     * @param age           the age
     * @param sdf           the sdf
     * @return the rx validator
     */
    public RxValidator age(String badAgeMessage, int age, SimpleDateFormat sdf) {
        this.validators.add(new AgeValidator(badAgeMessage, sdf, age));
        return this;
    }

    /**
     * Same as rx validator.
     *
     * @param anotherTextView the another text view
     * @param message         the message
     * @return the rx validator
     */
    public RxValidator sameAs(TextView anotherTextView, String message) {
        this.validators.add(new SameAsValidator(anotherTextView, message));
        return this;
    }

    /**
     * In rx validator.
     *
     * @param message      the message
     * @param properValues the proper values
     * @return the rx validator
     */
    public RxValidator in(String message, List<String> properValues) {
        this.validators.add(new InListValidator(message, properValues));
        return this;
    }

    /**
     * With rx validator.
     *
     * @param externalValidator the external validator
     * @return the rx validator
     */
    public RxValidator with(Validator<EditText> externalValidator) {
        this.externalValidators.add(externalValidator);
        return this;
    }

    /**
     * To observable observable.
     *
     * @return the observable
     */
    public Observable<RxValidationResult<EditText>> toObservable() {
        if (changeEmitter == null) {
            throw new ChangeEmitterNotSetException(
                    "Change emitter have to be set. Did you forget to set onFocusChanged, onValueChanged or onSubscribe?");
        }
        Observable<RxValidationResult<EditText>> validationResultObservable =
                changeEmitter.concatMap(new Func1<String, Observable<RxValidationResult<EditText>>>() {
                    @Override
                    public Observable<RxValidationResult<EditText>> call(final String value) {
                        return Observable.from(validators)
                                .concatMap(new Func1<Validator<EditText>, Observable<RxValidationResult<EditText>>>() {
                                            @Override
                                            public Observable<RxValidationResult<EditText>> call(
                                                    Validator<EditText> validator) {
                                                return validator.validate(value, et);
                                            }
                                        });
                    }
                })
                        .buffer(validators.size())
                        .map(objects -> ValidationResultHelper.getFirstBadResultOrSuccess(objects));

        if (externalValidators.isEmpty()) {
            return validationResultObservable;
        }
        return validationResultObservable.flatMap(
                new Func1<RxValidationResult<EditText>, Observable<RxValidationResult<EditText>>>() {
                    @Override
                    public Observable<RxValidationResult<EditText>> call(
                            final RxValidationResult<EditText> result) {
                        // if normal validators doesn't found error, launch external one
                        if (result.isProper()) {
                            return Observable.from(externalValidators)
                                    .concatMap(
                                            new Func1<Validator<EditText>, Observable<RxValidationResult<EditText>>>() {
                                                @Override
                                                public Observable<RxValidationResult<EditText>> call(
                                                        Validator<EditText> validator) {
                                                    return validator.validate(result.getValidatedText(), result.getItem());
                                                }
                                            })
                                    .buffer(externalValidators.size())
                                    .map(objects -> ValidationResultHelper.getFirstBadResultOrSuccess(objects));
                        } else {
                            return Observable.just(result);
                        }
                    }
                });
    }
}

