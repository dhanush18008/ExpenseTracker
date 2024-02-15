package com.expenses.ExpenseTracker.Config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "basicAuth",
        scheme = "basic")
public class OpenApiConfig
{
    @Bean
    public OpenAPI usersMicroserviceOpenAPI()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("Expense Tracker Application")
                        .description("Expense Tracker simplifies budget management through categorized expenses. Create, read, update, and delete categories effortlessly. Easily monitor spending habits and download detailed expenditure history for efficient financial management.")
                        .version("1.0"));
    }
}