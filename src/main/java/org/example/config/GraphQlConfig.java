package org.example.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Configuration
public class GraphQlConfig {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(){
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.GraphQLLong)
                .scalar(ExtendedScalars.GraphQLBigDecimal)
                .scalar(ExtendedScalars.Json)
                .scalar(timestampScalarType())
                .scalar(ExtendedScalars.Date);
    }

    @Bean
    public GraphQLScalarType timestampScalarType() {
        return GraphQLScalarType.newScalar()
                .name("Timestamp")
                .description("Timestamp scalar")
                .coercing(new Coercing() {
                    @Override
                    public String serialize(Object input) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
                        return formatter.format((Date)input);
                    }

                    @Override
                    public Object parseValue(Object o) throws CoercingParseValueException {
                        return o;
                    }

                    @Override
                    public Object parseLiteral(Object o) throws CoercingParseLiteralException {
                        return o.toString();
                    }
                })
                .build();

    }
}
