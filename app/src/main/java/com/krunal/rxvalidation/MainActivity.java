package com.krunal.rxvalidation;

import android.app.DatePickerDialog;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.kevadiyakrunalk.rxvalidation.RxValidationResult;
import com.kevadiyakrunalk.rxvalidation.RxValidator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static final String dateFormat = "dd-MM-yyyy";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    private TextInputLayout ilEmail;
    private TextInputEditText email;
    private TextInputLayout ilPassword;
    private TextInputEditText password;
    private TextInputLayout ilConfirmPassword;
    private TextInputEditText confirmPassword;
    private TextInputLayout ilBirthday;
    private TextInputEditText birthday;
    private TextInputLayout ilIp4Address;
    private TextInputEditText ip4Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initValue();
    }

    public void initValue() {
        RxValidator.createFor(email)
                .nonEmpty()
                .email()
                .with(new CustomEmailDomainValidator())
                .with(new ExternalApiEmailValidator())
                .onValueChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override
                    public void call(RxValidationResult<EditText> result) {
                        ilEmail.setError(result.isProper() ? null : result.getMessage());
                        Log.e("VALIDATION", "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("VALIDATION", "Validation error" + throwable);
                    }
                });

        RxValidator.createFor(password)
                .nonEmpty()
                .minLength(5, "Min length is 5")
                .onFocusChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override
                    public void call(RxValidationResult<EditText> result) {
                        ilPassword.setError(result.isProper() ? null : result.getMessage());
                        Log.e("VALIDATION", "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("VALIDATION", "Validation error" + throwable);
                    }
                });

        RxValidator.createFor(confirmPassword)
                .sameAs(password, "Password does not match")
                .onFocusChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override
                    public void call(RxValidationResult<EditText> result) {
                        ilConfirmPassword.setError(result.isProper() ? null : result.getMessage());
                        Log.e("VALIDATION", "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("VALIDATION", "Validation error" + throwable);
                    }
                });

        RxValidator.createFor(birthday)
                .age("You have to be 18y old", 18, sdf)
                .onValueChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override
                    public void call(RxValidationResult<EditText> result) {
                        ilBirthday.setError(result.isProper() ? null : result.getMessage());
                        Log.e("VALIDATION", "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("VALIDATION", "Validation error" + throwable);
                    }
                });

        RxValidator.createFor(ip4Address)
                .ip4("Invalid IP4 format")
                .onValueChanged()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxValidationResult<EditText>>() {
                    @Override
                    public void call(RxValidationResult<EditText> result) {
                        ilIp4Address.setError(result.isProper() ? null : result.getMessage());
                        Log.e("VALIDATION", "Validation result " + result.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("VALIDATION", "Validation error" + throwable);
                    }
                });

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(birthday);
            }
        });
    }

    private void showDatePicker(final EditText birthday) {
        DatePickerDialog dpd =
                new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Date selectedDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                        birthday.setText(sdf.format(selectedDate));
                    }
                }, 2016, 0, 1);
        dpd.show();
    }

    private void initView() {
        ilEmail = (TextInputLayout) findViewById(R.id.il_email);
        email = (TextInputEditText) findViewById(R.id.email);
        ilPassword = (TextInputLayout) findViewById(R.id.il_password);
        password = (TextInputEditText) findViewById(R.id.password);
        ilConfirmPassword = (TextInputLayout) findViewById(R.id.il_confirm_password);
        confirmPassword = (TextInputEditText) findViewById(R.id.confirm_password);
        ilBirthday = (TextInputLayout) findViewById(R.id.il_birthday);
        birthday = (TextInputEditText) findViewById(R.id.birthday);
        ilIp4Address = (TextInputLayout) findViewById(R.id.il_ip4_address);
        ip4Address = (TextInputEditText) findViewById(R.id.ip4_address);
    }
}
