#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.stepdefinitions;

import ${package}.api.menu.MenuActions;
import io.cucumber.java.Before;
import net.serenitybdd.cucumber.suiteslicing.SerenityTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

  private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);
  private static boolean firstTestRun = false;

  @Before
  public void before() {
    SerenityTags.create().tagScenarioWithBatchingInfo();
  }

  @Before
  public static void beforeAll() {
    if (!firstTestRun) {
      LOGGER.info("Get the Authorization Token");
      MenuActions.getAuthToken();

      firstTestRun = true;
    }
  }
}
