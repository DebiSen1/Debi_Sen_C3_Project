import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RestaurantTest {
    
	Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    

    @BeforeEach
    public void setUp() {

    	openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);

    }

    
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        Restaurant mockedRestaurant = Mockito.mock(Restaurant.class);
        LocalTime currentTime = LocalTime.parse("11:30:00");
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(currentTime);
        Boolean isOpen = restaurant.isRestaurantOpen(mockedRestaurant);
        Assertions.assertTrue(isOpen);
    }

    
    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        Restaurant mockedRestaurant = Mockito.mock(Restaurant.class);
        LocalTime currentTime = LocalTime.parse("23:30:00");
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(currentTime);
        Boolean isOpen = restaurant.isRestaurantOpen(mockedRestaurant);
        Assertions.assertFalse(isOpen);

    }


    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
    	
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    
    
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws ItemNotFoundException {
    	
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(ItemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }


    //TDD--test case is failing as the required method is not implemented.
    @Test
    public void calculateTotalPrice_should_return_total_order_value() {

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int expectedTotal = 119+269;

        int actualTotal = restaurant.calculateTotalPrice(restaurant.getMenu());

        assertEquals(expectedTotal,actualTotal);
    }
}