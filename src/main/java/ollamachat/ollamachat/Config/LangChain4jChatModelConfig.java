package ollamachat.ollamachat.Config;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChain4jChatModelConfig {
    @Bean
    ChatLanguageModel chatLanguageMode(){
        return OllamaChatModel.builder()
                .modelName("llama3:latest")
                .baseUrl("http://localhost:11434")
                .build();
    }

}
