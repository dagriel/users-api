package com.picpay.users.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }

    @Bean
    public Docket usersApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("users")
                .select()
                /*.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())*/
                .apis(RequestHandlerSelectors.basePackage("com.picpay.users"))
                /*.paths(PathSelectors.regex("/"))*/
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Serviço de Cadastro de Usuários e Transações",
                "API utilizada para gestão do Cadastro de Usuários e Transações do Desafio",
                "1.0.0",
                "",
                new Contact(
                        "Dagriel Lacruz",
                        "",
                        "dagriel@gmail.com?subject=Desafio Backend"),
                "",
                "",
                new ArrayList<VendorExtension>()
        );
        return apiInfo;
    }

}
