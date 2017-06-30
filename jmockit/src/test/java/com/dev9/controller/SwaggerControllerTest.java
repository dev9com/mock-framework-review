package com.dev9.controller;

import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.springframework.web.servlet.view.RedirectView;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Justin Graham
 * @since 6/30/17
 */
@Test
public class SwaggerControllerTest {

    @Mocked private RedirectView redirectView;
    @Tested private SwaggerController tested;

    @Test
    public void testRedirectToSwagger() throws Exception {
        assertThat(tested.redirectToSwagger()).isNotNull();
        new Verifications() {{ new RedirectView("/swagger-ui.html"); }};
    }
}
