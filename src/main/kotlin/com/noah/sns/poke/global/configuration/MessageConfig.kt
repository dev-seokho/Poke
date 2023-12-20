import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource

@Configuration
class MessageConfig {

    @Bean
    fun messageSource(): ReloadableResourceBundleMessageSource {
        return ReloadableResourceBundleMessageSource().apply {
            this.setBasename("classpath:/messages/messages")
            this.setDefaultEncoding("UTF-8")
        }
    }
}