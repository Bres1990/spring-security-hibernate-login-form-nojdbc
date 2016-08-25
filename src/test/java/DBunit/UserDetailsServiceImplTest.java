package DBunit;

import com.bres.siodme.annotation.DataSets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Adam on 2016-08-25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {udsiTestConfig.class})
@TestExecutionListeners({udsiTestExecutionListener.class})
public class UserDetailsServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
    @PersistenceContext protected EntityManager em;
    @Autowired
    UserDetailsService userDetailsService;


    @DataSets(setUpDataSet = "/src/main/resources/TestData.xlsx")
    @Test
    public void shouldReturnNoResult() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("TestUser");

        org.junit.Assert.assertNull(userDetails);
    }

    @DataSets(setUpDataSet = "/src/main/resources/TestData.xlsx")
    @Test
    public void shouldReturnAdminUserDetailsCorrectly() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("TestUser1");

        org.junit.Assert.assertNotNull(userDetails);
        org.junit.Assert.assertEquals("TestUser1", userDetails.getUsername());
        org.junit.Assert.assertEquals("administration", userDetails.getPassword());
        org.junit.Assert.assertEquals("ROLE_ADMIN", userDetails.getAuthorities());
    }
}
