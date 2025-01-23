package org.mule.extension.mulechain.internal.connection;

import org.mule.runtime.api.connection.ConnectionException;

public interface Connection {

  void connect() throws ConnectionException;

  void disconnect();

  boolean isValid();

}
