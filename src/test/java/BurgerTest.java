import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    Database dataBase = new Database();
    private final int allBurgerIngredients = dataBase.availableIngredients().size();

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredientFilling;

    @Before
    public void startUp() {

        burger = new Burger();
        MockitoAnnotations.openMocks(this);
        Mockito.when(mockBun.getPrice()).thenReturn(1255f);
        Mockito.when(mockBun.getName()).thenReturn("Краторная булка N-200i");
        Mockito.when(mockIngredientFilling.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(mockIngredientFilling.getName()).thenReturn("dinosaur");
        Mockito.when(mockIngredientFilling.getPrice()).thenReturn(200F);
    }


    @Test
    public void addIngredientBurgerTest() {

        for(int i = 0; i < allBurgerIngredients; i++) {
            burger.addIngredient(dataBase.availableIngredients().get(i));
        }
        int actualCount = burger.ingredients.size();

        assertEquals("Ингридиенты не были добавлены", allBurgerIngredients, actualCount);

    }

    @Test
    public void removeIngredientBurgerTest() {

        for(int i = 0; i < allBurgerIngredients; i++) {
            burger.addIngredient(dataBase.availableIngredients().get(i));
        }
        burger.removeIngredient(allBurgerIngredients-1);
        int actualCount = burger.ingredients.size();

        assertEquals("Ингредиент не был удален",allBurgerIngredients-1, actualCount);
    }

    @Test
    public void moveIngredientBurgerTest() {

        for(int i = 0; i < allBurgerIngredients; i++) {
            burger.addIngredient(dataBase.availableIngredients().get(i));
        }
        Ingredient ingredient = burger.ingredients.get(allBurgerIngredients-1);
        burger.moveIngredient(allBurgerIngredients-1, 0);

        assertEquals("Ингредиент не был перемещен", burger.ingredients.get(0), ingredient);
    }

    @Test
    public void getPriceBurgerTest() {

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredientFilling);

        float actualPrice = burger.getPrice();
        float expectedPrice = 2*1255+mockIngredientFilling.getPrice();

        assertEquals("Стоимость бургера подсчитана некорректно",expectedPrice, actualPrice, 0);
    }

    @Test
    public void getReceiptTest(){

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredientFilling);
        String ingredient = mockIngredientFilling.getName();

        assertTrue("В рецепте указаны некорректные данные",burger.getReceipt().contains("Краторная булка N-200i")&&burger.getReceipt().contains(ingredient));

    }

}
