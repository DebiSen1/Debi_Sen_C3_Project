import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant {
	
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    
    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public String getName() {
        return name;
    }

    public LocalTime getCurrentTime(){
        return  LocalTime.now();
    }

    public List<Item> getMenu() {
        return Collections.unmodifiableList(menu);
    }

    
    public boolean isRestaurantOpen(Restaurant restaurant) {
        if(restaurant.getCurrentTime().isBefore(closingTime) && restaurant.getCurrentTime().isAfter(openingTime))
            return true;
        else return false;
    }


    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        
        return null;
    }

    
    public void addToMenu(String name, int price) {

    	Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    
    public void removeFromMenu(String itemName) throws ItemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new ItemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }

    public void displayDetails(){

        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    //method is implemented to pass the failing test case
    public int calculateTotalPrice(List<Item> menu){

        int totalAmount = 0;

        for(Item item : menu){
            int price = item.getPrice();
            totalAmount = totalAmount+price;
        }
        return totalAmount;
    }

}
