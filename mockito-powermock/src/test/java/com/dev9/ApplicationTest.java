package com.dev9;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.boot.SpringApplication;
import org.testng.annotations.Test;

import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * @author Justin Graham
 * @since 6/29/17
 */
@Test
@PrepareForTest(SpringApplication.class)
public class ApplicationTest extends PowerMockTestCase {

    @Test
    public void testMain() throws Exception {
        final String[] args = new String[0];
        mockStatic(SpringApplication.class);
        doAnswer((i) -> null).when(SpringApplication.class, "run", Application.class, args);

        Application.main(args);

        verifyStatic();
        SpringApplication.run(Application.class, args);
    }
}
