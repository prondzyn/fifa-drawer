package com.prondzyn.fifadrawer.lang;

public class LoadingException extends RuntimeException {

  public LoadingException(Exception exception) {
    super(exception);
  }
}
