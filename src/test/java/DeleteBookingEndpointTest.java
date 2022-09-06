import entities.Auth;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class DeleteBookingEndpointTest {


    private static RestfulBookerAPI api;

    @BeforeClass
    public static void createTestEnvironment() {
        api = new RestfulBookerAPI("https://restful-booker.herokuapp.com");
    }

    @Test
    public void deleteBooking() {
        String username = "admin";
        String password = "password123";
        Auth auth = new Auth(username,password);
        String token = api.auth(auth);

        List<Integer> bookingList = api.getBookingIds();

        int random = (int) (Math.random() * (bookingList.size())+1);
        System.out.println("This is the random number: " + random);

        Response response = api.deleteBooking(token, bookingList.get(random));

        Assert.assertEquals("The status code is not the expected one",response.statusCode(), 201);

        List<Integer> updatedList = api.getBookingIds();

        Assert.assertFalse("The booking wasn't deleted as expected", updatedList.contains(bookingList.get(random)));
        Assert.assertTrue(updatedList.size() < bookingList.size());
    }

}
