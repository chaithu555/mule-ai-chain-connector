package org.mule.extension.mulechain.internal.connection.provider;

import org.mule.extension.mulechain.internal.connection.OllamaConnection;
import org.mule.extension.mulechain.internal.connection.paramter.OllamaConnectionParameter;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("ollama")
@DisplayName("Ollama")
public class OllamaConnectionProvider implements CachedConnectionProvider<OllamaConnection> {

  private static final Logger LOGGER = LoggerFactory.getLogger(OllamaConnectionProvider.class);

  @ParameterGroup(name = ParameterGroup.CONNECTION)
  private OllamaConnectionParameter ollamaConnectionParameter;

  @Override
  public OllamaConnection connect() throws ConnectionException {
    try {
      OllamaConnection ollamaConnection = new OllamaConnection(ollamaConnectionParameter);
      ollamaConnection.connect();
      return ollamaConnection;
    } catch (ConnectionException e) {
      throw e;
    } catch (Exception e) {
      throw new ConnectionException("Ollama connection failed", e);
    }
  }

  @Override
  public void disconnect(OllamaConnection ollamaConnection) {
    try {
      ollamaConnection.disconnect();
    } catch (Exception e) {
      LOGGER.error("Failed to close connection", e);
    }
  }

  @Override
  public ConnectionValidationResult validate(OllamaConnection ollamaConnection) {
    try {
      if (ollamaConnection.isValid()) {
        return ConnectionValidationResult.success();
      } else {
        return ConnectionValidationResult.failure("Ollama Connection Invalid", null);
      }
    } catch (Exception e) {
      return ConnectionValidationResult.failure("Failed to validate connection to Ollama", e);
    }
  }
}
