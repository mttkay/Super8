package com.github.super8.apis;

public class ServerCommunicationException extends Exception {

  private static final long serialVersionUID = 1L;

  public ServerCommunicationException(String message) {
    super(message);
  }

  public ServerCommunicationException(Throwable cause) {
    super("Sorry, I tried communicating with the Internet, but failed. Try again later.", cause);
  }

  public ServerCommunicationException(String message, Throwable cause) {
    super(message, cause);
  }

}
