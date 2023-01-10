import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import togaether.BL.Facade.TrophyFacade;
import togaether.BL.Model.TrophyCategory;

import java.sql.SQLException;
import java.util.ArrayList;

public class TrophyFacadeTest {

  @Test
  void isAllConversionOfNameValid() {
    ArrayList<TrophyCategory> liste;
    try {
      liste = (ArrayList<TrophyCategory>) TrophyFacade.getInstance().getAllCategories();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    for(TrophyCategory tc : liste) {
      String convertedName = TrophyFacade.getInstance().nameToFrench(tc.getName());
      String reconvertedName = TrophyFacade.getInstance().frenchToName(convertedName);
      Assertions.assertTrue(tc.getName().equals(reconvertedName), "La catégorie de trophée"+tc.getName()+" n'a pas une bonne conversion de nom");
    }
  }
}
