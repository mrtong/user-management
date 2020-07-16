package com.foo.ing.usermanagement.pact;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContextManager;

@RunWith(PactRunner.class)
@Provider("usermanagement")
@PactFolder("pacts")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"server.port=6699"}
)
@Slf4j
public class UserDetailsServicePactTest {

    private int port = 6699;

    @Before
    public void before() throws Exception {
        new TestContextManager(getClass()).prepareTestInstance(this);
    }

    @TestTarget
    public final Target target = new HttpTarget(port);

    @State("have a valid userdetails")
    public void findAValidUserDetails() {
        log.debug("Pact test to have a valid userdetails.");
    }

    @State("No UserDetails were found")
    public void noUserDetailsFound() {
        log.debug("Pact test to what will happen if no user details were found.");
    }

}
