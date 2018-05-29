import com.em.client.UserRestClient;
import com.em.model.RestResponse;
import com.em.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by guchong on 5/23/2018.
 */
@Ignore
public class UserRestClientTest {

    private UserRestClient userRestClient;

    @Before
    public void setup(){
        userRestClient = new UserRestClient("127.0.0.1",8080);
    }

    @Test
    public void testUserCreate(){
        RestResponse ret = userRestClient.newUser("guchong","123456",2,"em guchong","44-85-00-F8-E6-6B,46-85-00-F8-E6-6B");
        Assert.assertNotNull(ret);
        Assert.assertTrue(ret.isSuccess());
        ret = userRestClient.newUser("guchong","123456",2,"em guchong","44-85-00-F8-E6-6B,46-85-00-F8-E6-6B");
        Assert.assertFalse(ret.isSuccess());
    }

    @Test
    public void testUserUpdate(){
        RestResponse ret = userRestClient.updateUser("guchong","44-85-00-F8-E6-6B,46-85-00-F8-E6-7B");
        Assert.assertNotNull(ret);
        Assert.assertTrue(ret.isSuccess());
    }
}
