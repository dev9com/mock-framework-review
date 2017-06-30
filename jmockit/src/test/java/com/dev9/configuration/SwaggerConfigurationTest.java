package com.dev9.configuration;

import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.testng.annotations.Test;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static com.google.common.base.Predicates.not;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Justin Graham
 * @since 6/28/17
 */
@Test
public class SwaggerConfigurationTest {

    @Mocked private RequestHandlerSelectors requestHandlerSelectors;
    @Mocked private PathSelectors pathSelectors;
    @Mocked private ApiInfoBuilder apiInfoBuilder;
    @Mocked private ApiInfo apiInfo;
    @Mocked private Docket docket;
    @Tested private SwaggerConfiguration tested;

    @Test
    public void testApi() throws Exception {
        assertThat(tested.api(apiInfo)).isNotNull();
        new Verifications() {{
            new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.dev9.controller"))
                    .paths(not(PathSelectors.regex("/")))
                    .build()
                    .apiInfo(apiInfo);
        }};
    }

    @Test
    public void testApiInfo() throws Exception {
        assertThat(tested.apiInfo()).isNotNull();
        new Verifications() {{
            new ApiInfoBuilder()
                    .title("Test App")
                    .description("A simple SpringBoot app to compare mocking frameworks")
                    .version("1.0")
                    .build();
        }};
    }
}
