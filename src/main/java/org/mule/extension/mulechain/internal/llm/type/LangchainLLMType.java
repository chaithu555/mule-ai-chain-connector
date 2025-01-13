/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.mulechain.internal.llm.type;

import dev.langchain4j.model.anthropic.AnthropicChatModelName;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.mistralai.MistralAiChatModelName;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiImageModelName;
import org.mule.extension.mulechain.internal.config.LangchainLLMConfiguration;
import org.mule.extension.mulechain.internal.config.util.LangchainLLMInitializerUtil;
import org.mule.extension.mulechain.internal.error.exception.ConfigValidationException;
import org.mule.extension.mulechain.internal.llm.config.ConfigExtractor;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum LangchainLLMType {
  OPENAI("OPENAI", getOpenAIModelNameStream(), LangchainLLMInitializerUtil::createOpenAiChatModel), GROQAI_OPENAI("GROQAI_OPENAI",
      OPENAI.getModelNameStream(), LangchainLLMInitializerUtil::createGroqOpenAiChatModel), MISTRAL_AI("MISTRAL_AI",
          getMistralAIModelNameStream(), LangchainLLMInitializerUtil::createMistralAiChatModel), OLLAMA("OLLAMA",
              getOllamaModelNameStream(), LangchainLLMInitializerUtil::createOllamaChatModel), ANTHROPIC("ANTHROPIC",
                  getAnthropicModelNameStream(), LangchainLLMInitializerUtil::createAnthropicChatModel), AZURE_OPENAI(
                      "AZURE_OPENAI", OPENAI.getModelNameStream(),
                      LangchainLLMInitializerUtil::createAzureOpenAiChatModel), HUGGING_FACE(
                          "HUGGING_FACE", getHuggingFaceModelNameStream(),
                          LangchainLLMInitializerUtil::createHuggingFaceChatModel), GEMINI_AI("GEMINI_AI",
                              getGoogleGeminiModelNameStream(),
                              LangchainLLMInitializerUtil::createGoogleGeminiChatModel);

  private final String value;
  private final Stream<String> modelNameStream;

  private final BiFunction<ConfigExtractor, LangchainLLMConfiguration, ChatLanguageModel> configBiFunction;

  LangchainLLMType(String value, Stream<String> modelNameStream,
                   BiFunction<ConfigExtractor, LangchainLLMConfiguration, ChatLanguageModel> configBiFunction) {
    this.value = value;
    this.modelNameStream = modelNameStream;
    this.configBiFunction = configBiFunction;
  }

  public String getValue() {
    return value;
  }

  public Stream<String> getModelNameStream() {
    return modelNameStream;
  }

  public BiFunction<ConfigExtractor, LangchainLLMConfiguration, ChatLanguageModel> getConfigBiFunction() {
    return configBiFunction;
  }

  private static Stream<String> getOpenAIModelNameStream() {
    return Stream.concat(Arrays.stream(OpenAiChatModelName.values()), Arrays.stream(OpenAiImageModelName.values()))
        .map(String::valueOf);
  }

  private static Stream<String> getMistralAIModelNameStream() {
    return Arrays.stream(MistralAiChatModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getOllamaModelNameStream() {
    return Arrays.stream(OllamaModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getHuggingFaceModelNameStream() {
    return Arrays.stream(HuggingFaceModelName.values()).map(String::valueOf);
  }

  private static Stream<String> getGoogleGeminiModelNameStream() {
    return Arrays.stream(GeminiModelName.values()).map(String::valueOf);
  }


  private static Stream<String> getAnthropicModelNameStream() {
    return Arrays.stream(AnthropicChatModelName.values()).map(String::valueOf);
  }

  public static LangchainLLMType fromValue(String value) {
    return Arrays.stream(LangchainLLMType.values())
        .filter(langchainLLMType -> langchainLLMType.value.equals(value))
        .findFirst()
        .orElseThrow(() -> new ConfigValidationException("Unsupported LLM Type: " + value));
  }

  public enum OllamaModelName {
    MISTRAL("mistral"), PHI3("phi3"), ORCA_MINI("orca-mini"), LLAMA2("llama2"), CODE_LLAMA("codellama"), TINY_LLAMA("tinyllama");

    private final String value;

    OllamaModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  public enum HuggingFaceModelName {
    TII_UAE_FALCON_7B_INSTRUCT("tiiuae/falcon-7b-instruct"), PHI3("microsoft/Phi-3.5-mini-instruct"), MISTRAL_7B_INSTRUCT_V03(
        "mistralai/Mistral-7B-Instruct-v0.3"), TINY_LLAMA("TinyLlama/TinyLlama-1.1B-Chat-v1.0");

    private final String value;

    HuggingFaceModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  public enum GeminiModelName {
    GEMINI_1_5_FLASH("gemini-1.5-flash"), GEMINI_1_5_PRO("gemini-1.5-pro"), GEMINI_1_PRO(
        "gemini-1.0-pro");

    private final String value;

    GeminiModelName(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

}
