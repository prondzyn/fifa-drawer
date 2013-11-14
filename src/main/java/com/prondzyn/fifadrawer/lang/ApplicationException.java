package com.prondzyn.fifadrawer.lang;

public class ApplicationException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "This should never happen but who knows.";

  public ApplicationException() {
    this(DEFAULT_MESSAGE);
  }

  public ApplicationException(String message) {
    super(message);
  }

  public ApplicationException(Exception cause) {
    this(DEFAULT_MESSAGE, cause);
  }

  public ApplicationException(String message, Exception cause) {
    super(message, cause);
  }

}
