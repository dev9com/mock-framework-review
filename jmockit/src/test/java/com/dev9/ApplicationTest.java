package com.dev9;

import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.springframework.boot.SpringApplication;
import org.testng.annotations.Test;

/**
 * @author Justin Graham
 * @since 6/28/17
 */
@Test
public class ApplicationTest {

    @Mocked private SpringApplication springApplication;
    @Tested private Application tested;

    @Test
    public void testMain() throws Exception {
        final String[] args = new String[0];
        Application.main(args);
        new Verifications() {{ SpringApplication.run(Application.class, args); }};
    }
}
