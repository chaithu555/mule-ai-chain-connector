package org.mule.extension.mulechain.internal.connection.provider;

import org.mule.extension.mulechain.internal.connection.AnthropicConnection;
import org.mule.extension.mulechain.internal.connection.paramter.AnthropicConnectionParameter;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("anthropic")
@DisplayName("Anthropic")
public class AnthropicConnectionProvider implements CachedConnectionProvider<AnthropicConnection> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AnthropicConnectionProvider.class);

  @ParameterGroup(name = ParameterGroup.CONNECTION)
  private AnthropicConnectionParameter anthropicConnectionParameter;

  @Override
  public AnthropicConnection connect() throws ConnectionException {
    try {
      AnthropicConnection anthropicConnection = new AnthropicConnection(anthropicConnectionParameter);
      anthropicConnection.connect();
      return anthropicConnection;
    } catch (ConnectionException e) {
      throw e;
    } catch (Exception e) {
      throw new ConnectionException("Anthropic connection failed", e);
    }
  }

  @Override
  public void disconnect(AnthropicConnection anthropicConnection) {
    try {
      anthropicConnection.disconnect();
    } catch (Exception e) {
      LOGGER.error("Failed to close connection", e);
    }
  }

  @Override
  public ConnectionValidationResult validate(AnthropicConnection anthropicConnection) {
    try {
      if (anthropicConnection.isValid()) {
        return ConnectionValidationResult.success();
      } else {
        return ConnectionValidationResult.failure("Anthropic Connection Invalid", null);
      }
    } catch (Exception e) {
      return ConnectionValidationResult.failure("Failed to validate connection to Anthropic", e);
    }
  }
}
