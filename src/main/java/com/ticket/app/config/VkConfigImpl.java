package com.ticket.app.config;


import com.ticket.app.config.interfce.VkConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "file:./vk.properties")
public class VkConfigImpl implements VkConfig {

    private String applicationId;

    private String secretCode;

    @Autowired
    public VkConfigImpl(Environment env) {
        applicationId = env.getRequiredProperty("vk.app.id");
        secretCode = env.getRequiredProperty("vk.secret.code");
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getSecretCode() {
        return secretCode;
    }
}
