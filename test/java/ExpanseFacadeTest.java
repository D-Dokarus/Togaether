import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import togaether.BL.Facade.ExpenseFacade;
import togaether.BL.Facade.TrophyFacade;
import togaether.BL.Model.ExpenseCategory;
import togaether.BL.Model.TrophyCategory;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExpanseFacadeTest {
  @Test
  void isAllConversionOfNameValid() {
    ArrayList<ExpenseCategory> liste;
    try {
      liste = (ArrayList<ExpenseCategory>) ExpenseFacade.getInstance().findAllCategories();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    for(ExpenseCategory ec : liste) {
      String convertedName = ExpenseFacade.getInstance().nameToFrench(ec.getName());
      String reconvertedName = ExpenseFacade.getInstance().frenchToName(convertedName);
      Assertions.assertTrue(ec.getName().equals(reconvertedName), "La catégorie de dépense "+ec.getName()+" n'a pas une bonne conversion de nom");
    }
  }
}
