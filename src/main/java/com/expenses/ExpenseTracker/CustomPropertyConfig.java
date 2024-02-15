package com.expenses.ExpenseTracker;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource("file:C:/Users/Admin/Desktop/expenseTracker.properties")
public class CustomPropertyConfig {
    private final Environment environment;

    public CustomPropertyConfig(Environment environment) {
        this.environment = environment;
    }

    public String getProperty(String key) {
        return environment.getProperty(key);
    }
}
