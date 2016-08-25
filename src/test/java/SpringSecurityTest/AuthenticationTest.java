package SpringSecurityTest;

import com.bres.siodme.config.SecurityConfig;
import com.bres.siodme.config.SpringWebConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by Adam on 2016-08-09.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityConfig.class, SpringWebConfig.class})
@WebAppConfiguration
public class AuthenticationTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private Filter springSecurityFilterChain;
    @Autowired
    private UserDetailsService userDetailsService;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void shouldLogAdminIntoAdminPage() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("ADMINISTRATOR");
        mvc.perform(get("/admin").with(user(userDetails)))
                .andExpect(authenticated()
                        .withUsername("ADMINISTRATOR")
                        .withRoles("ADMIN"))
                .andExpect(MockMvcResultMatchers.view()
                        .name("admin"))
        ;
    }

    @Test
    public void shouldPreventUserFromAccessingAdminPage() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("username");
        mvc.perform(get("/admin").with(user(userDetails)))
                .andExpect(MockMvcResultMatchers.status()
                        .isForbidden())
        ;
    }

    @Test
    public void shouldPreventAdminFromAccessingUserWelcomePage() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("ADMINISTRATOR");
        mvc.perform(get("/welcome").with(user(userDetails)))
                .andExpect(MockMvcResultMatchers.status()
                        .isForbidden())
        ;
    }


    @Test
    public void shouldTestRememberMeTimeout() throws Exception {

    }

    @Test
    public void shouldAssertThatFindByUsernameReturnsUniqueRecord() throws Exception {

    }
}