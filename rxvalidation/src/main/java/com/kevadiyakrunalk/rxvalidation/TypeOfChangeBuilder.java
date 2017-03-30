package com.kevadiyakrunalk.rxvalidation;

/**
 * The interface Type of change builder.
 */
public interface TypeOfChangeBuilder {
  /**
   * On focus changed rx validator.
   *
   * @return the rx validator
   */
  RxValidator onFocusChanged();

  /**
   * On value changed rx validator.
   *
   * @return the rx validator
   */
  RxValidator onValueChanged();

  /**
   * On subscribe rx validator.
   *
   * @return the rx validator
   */
  RxValidator onSubscribe();
}
