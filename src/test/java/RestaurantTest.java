import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    Restaurant restaurant;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant = Mockito.spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        LocalTime currentMockedTime = LocalTime.parse("14:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currentMockedTime);
        assertEquals(true, restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurant = Mockito.spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        LocalTime currentMockedTime = LocalTime.parse("23:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currentMockedTime);
        assertEquals(false, restaurant.isRestaurantOpen());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>Part 3: Adding a Feature using TDD<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_no_items_to_order_display_total_value_as_0() throws itemNotFoundException {
        restaurant =new Restaurant("Yash's cafe","Ahmedabad",openingTime,closingTime);
        restaurant.addToMenu("Item1",100);
        restaurant.addToMenu("Item2", 200);

        List<String> itemNames = new ArrayList<String>();

        int totalPrice = restaurant.calculateTotalPrice(itemNames);
        assertEquals(0, totalPrice);
    }

    @Test
    public void adding_items_to_order_display_total_value_as_their_individual_price_summation() throws itemNotFoundException {
        restaurant =new Restaurant("Yash's cafe","Ahmedabad",openingTime,closingTime);
        restaurant.addToMenu("Item1",100);
        restaurant.addToMenu("Item2", 200);
        restaurant.addToMenu("Item3", 300);

        List<String> itemNames = new ArrayList<String>();
        itemNames.add("Item1");
        itemNames.add("Item3");

        int totalPrice = restaurant.calculateTotalPrice(itemNames);
        assertEquals(400, totalPrice);
    }

    @Test
    public void adding_items_to_order_which_is_not_in_restaurant_menu_should_throw_an_exception() throws itemNotFoundException {
        restaurant =new Restaurant("Yash's cafe","Ahmedabad",openingTime,closingTime);
        restaurant.addToMenu("Item1",100);
        restaurant.addToMenu("Item2", 200);
        restaurant.addToMenu("Item3", 300);

        List<String> itemNames = new ArrayList<String>();
        itemNames.add("Item5");

        assertThrows(itemNotFoundException.class,
                ()->restaurant.calculateTotalPrice(itemNames));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Part 3: Adding a Feature using TDD>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}