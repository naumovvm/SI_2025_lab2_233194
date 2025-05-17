import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SILab2Test {
    @Test
    public void testCheckCartBranchCoverage(){
        // Go testirame prv allItems
        Exception exception1 = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(null, "1234567890123456");
        });
        assertEquals("allItems list can't be null!", exception1.getMessage());

        // Testirame so nevalidno ime na predmet
        List<Item> items1 = Arrays.asList(new Item(null, 1, 100, 0));
        Exception exception2 = assertThrows(RuntimeException.class, () -> {
           SILab2.checkCart(items1, "1234567890123456");
        });
        assertEquals("Invalid item!", exception2.getMessage());

        // Testirame so nevalidna dolzhina na brojot na karticka
        List<Item> items2 = Arrays.asList(new Item("Milka", 1, 100, 0));
        Exception exception3 = assertThrows(RuntimeException.class, () -> {
           SILab2.checkCart(items2, "1234");
        });
        assertEquals("Invalid card number!", exception3.getMessage());

        // Testirame so nevaliden/nevalidni karakter/i vo brojot na kartickata
        List<Item> items3 = Arrays.asList(new Item("Dorina", 1, 100, 0));
        Exception exception4 = assertThrows(RuntimeException.class, () -> {
           SILab2.checkCart(items3, "1b3456789012345a");
        });
        assertEquals("Invalid character in card number!", exception4.getMessage());

        // Validen predmet i validen broj na karticka
        List<Item> items4 = Arrays.asList(new Item("Snikers", 1, 100, 0));
        double result = SILab2.checkCart(items4, "1234567890123456");
        assertEquals(100.0, result);
    }

    @Test // tuka testirame za popust, so discount,so cenata i so quantity, pa i dve tocni edno netocno
    public void testCheckCartDiscounts(){
        // Prviot test e so popust od 0.1
        List<Item> items1 = Arrays.asList(new Item("Milka", 2, 100, 0.1));
        double result1 = SILab2.checkCart(items1, "1234567890123456"); // 100*(1-0.1)*2=100*0.9*2=180 i potoa 180-30=150 deducirana finalna suma
        assertEquals(150.0, result1);

        // Vtoriot test e so cena > 300
        List<Item> items2 = Arrays.asList(new Item("Kafe lavazza", 1, 350, 0));
        double result2 = SILab2.checkCart(items2, "1234567890123456"); // treba od 350 da se odzeme -30 i da se dobie 320
        assertEquals(320.0, result2);

        // Tret test kade imame kolicina pogolema od 10
        List<Item> items3 = Arrays.asList(new Item("Pivo", 11, 100, 0));
        double result3 = SILab2.checkCart(items3, "1234567890123456"); // -30 + 100*11 = 1070
        assertEquals(1070.0, result3);

        // Testirame i so popust i so pogolema cena od 300
        List<Item> items4 = Arrays.asList(new Item("Kafe lavazza", 1, 350, 0.2));
        double result4 = SILab2.checkCart(items4, "1234567890123456");
        assertEquals(250.0, result4);
    }
}
