import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;


    @BeforeEach
    public void setUp() {
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
    }
    

    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws RestaurantNotFoundException {
        Restaurant newlyCreatedRestaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant = service.findRestaurantByName(newlyCreatedRestaurant.getName());
        assertNotNull(restaurant);
        assertEquals(newlyCreatedRestaurant.getName(),restaurant.getName());
    }


    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws RestaurantNotFoundException {

        assertThrows(RestaurantNotFoundException.class,()->{
            restaurant = service.findRestaurantByName("some invalid name");
        });
    }



    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws RestaurantNotFoundException {

        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    
    
    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws RestaurantNotFoundException {

        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(RestaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    
    
    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){

        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    
}