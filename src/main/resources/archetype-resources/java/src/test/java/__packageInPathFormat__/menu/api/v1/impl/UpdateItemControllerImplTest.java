#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.menu.api.v1.impl;

import static ${package}.menu.domain.CategoryHelper.createCategory;
import static ${package}.menu.domain.ItemHelper.createItem;
import static ${package}.menu.domain.ItemHelper.createItems;
import static ${package}.menu.domain.MenuHelper.createMenu;
import static ${package}.util.TestHelper.getBaseURL;
import static ${package}.util.TestHelper.getRequestHttpEntity;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.http.HttpStatus.OK;

import ${package}.menu.api.v1.dto.request.UpdateItemRequest;
import ${package}.menu.api.v1.dto.response.ResourceUpdatedResponse;
import ${package}.menu.domain.Category;
import ${package}.menu.domain.Item;
import ${package}.menu.domain.Menu;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

/** @author ArathyKrishna */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Tag("Integration")
@ActiveProfiles("test")
class UpdateItemControllerImplTest {

  public static final String UPDATE_ITEM = "%s${apiPath}/%s/category/%s/items/%s";

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate testRestTemplate;

  @Test
  void testUpdateItemSuccess() {
    // Given
    Menu menu = createMenu(0);
    Category category = createCategory(0);
    Item item = createItem(0);
    category.addOrUpdateItem(item);
    menu.addOrUpdateCategory(category);

    UpdateItemRequest request =
        new UpdateItemRequest("Some Name", "Some Description", 13.56d, true);

    // When
    String requestUrl =
        String.format(UPDATE_ITEM, getBaseURL(port), menu.getId(), category.getId(), item.getId());

    var response =
        this.testRestTemplate.exchange(
            requestUrl,
            HttpMethod.PUT,
            new HttpEntity<>(request, getRequestHttpEntity()),
            ResourceUpdatedResponse.class);

    // Then
    then(response).isNotNull();
    then(response.getStatusCode()).isEqualTo(OK);
  }

  @Test
  void testUpdateItemDescription() {
    // Given
    Menu menu = createMenu(0);
    Category category = createCategory(0);
    List<Item> items = createItems(2);
    category.setItems(items);
    menu.addOrUpdateCategory(category);

    UpdateItemRequest request =
        new UpdateItemRequest(items.get(0).getName(), "Some Description2", 13.56d, true);

    // When
    String requestUrl =
        String.format(
            UPDATE_ITEM, getBaseURL(port), menu.getId(), category.getId(), items.get(0).getId());

    var response =
        this.testRestTemplate.exchange(
            requestUrl,
            HttpMethod.PUT,
            new HttpEntity<>(request, getRequestHttpEntity()),
            ResourceUpdatedResponse.class);

    // Then
    then(response).isNotNull();
    then(response.getStatusCode()).isEqualTo(OK);
  }
}
