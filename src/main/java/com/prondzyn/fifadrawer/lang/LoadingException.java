package com.prondzyn.fifadrawer.lang;

public class LoadingException extends RuntimeException {

  public LoadingException(String message, Exception exception) {
    super(message, exception);
  }
}
