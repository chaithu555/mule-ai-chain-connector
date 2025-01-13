package org.mule.extension.mulechain.internal.connection.provider;

import org.mule.extension.mulechain.internal.connection.HuggingFaceConnection;
import org.mule.extension.mulechain.internal.connection.paramter.HuggingFaceConnectionParameter;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("huggingFace")
@DisplayName("HuggingFace")
public class HuggingFaceConnectionProvider implements CachedConnectionProvider<HuggingFaceConnection> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HuggingFaceConnectionProvider.class);

  @ParameterGroup(name = ParameterGroup.CONNECTION)
  private HuggingFaceConnectionParameter huggingFaceConnectionParameter;

  @Override
  public HuggingFaceConnection connect() throws ConnectionException {
    try {
      HuggingFaceConnection huggingFaceConnection = new HuggingFaceConnection(huggingFaceConnectionParameter);
      huggingFaceConnection.connect();
      return huggingFaceConnection;
    } catch (ConnectionException e) {
      throw e;
    } catch (Exception e) {
      throw new ConnectionException("Hugging Face AI connection failed", e);
    }
  }

  @Override
  public void disconnect(HuggingFaceConnection huggingFaceConnection) {
    try {
      huggingFaceConnection.disconnect();
    } catch (Exception e) {
      LOGGER.error("Failed to close connection", e);
    }
  }

  @Override
  public ConnectionValidationResult validate(HuggingFaceConnection huggingFaceConnection) {
    try {
      if (huggingFaceConnection.isValid()) {
        return ConnectionValidationResult.success();
      } else {
        return ConnectionValidationResult.failure("Hugging Face AI Connection Invalid", null);
      }
    } catch (Exception e) {
      return ConnectionValidationResult.failure("Failed to validate connection to Hugging Face AI", e);
    }
  }
}
