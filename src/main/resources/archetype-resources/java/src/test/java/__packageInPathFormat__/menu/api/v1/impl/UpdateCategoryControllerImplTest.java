#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.menu.api.v1.impl;

import static ${package}.menu.domain.CategoryHelper.createCategories;
import static ${package}.menu.domain.CategoryHelper.createCategory;
import static ${package}.menu.domain.MenuHelper.createMenu;
import static ${package}.util.TestHelper.getBaseURL;
import static ${package}.util.TestHelper.getRequestHttpEntity;
import static java.util.UUID.fromString;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.http.HttpStatus.OK;

import ${package}.core.api.dto.ErrorResponse;
import ${package}.menu.api.v1.dto.request.UpdateCategoryRequest;
import ${package}.menu.api.v1.dto.response.ResourceUpdatedResponse;
import ${package}.menu.domain.Category;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Tag("Integration")
@ActiveProfiles("test")
class UpdateCategoryControllerImplTest {

  public static final String UPDATE_CATEGORY = "%s${apiPath}/%s/category/%s";

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate testRestTemplate;

  @Test
  void testUpdateCategorySuccess() {
    // Given
    Menu menu = createMenu(0);
    Category category = createCategory(0);
    menu.addOrUpdateCategory(category);

    UpdateCategoryRequest request = new UpdateCategoryRequest("new Category", "new Description");

    // When
    String requestUrl =
        String.format(
            UPDATE_CATEGORY,
            getBaseURL(port),
            fromString(menu.getId()),
            fromString(category.getId()));

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
  void testUpdateCategoryWhenMultipleCategoriesExists() {
    // Given
    Menu menu = createMenu(0);
    List<Category> categories = createCategories(2);
    menu.setCategories(categories);

    UpdateCategoryRequest request = new UpdateCategoryRequest("new Category", "new Description");

    // When
    String requestUrl =
        String.format(UPDATE_CATEGORY, getBaseURL(port), menu.getId(), categories.get(0).getId());

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
  void testUpdateOnlyCategoryDescription() {
    // Given
    Menu menu = createMenu(0);
    List<Category> categoryList = createCategories(2);
    menu.setCategories(categoryList);

    UpdateCategoryRequest request =
        new UpdateCategoryRequest(categoryList.get(1).getName(), "new Description");

    // When
    String requestUrl =
        String.format(
            UPDATE_CATEGORY,
            getBaseURL(port),
            fromString(menu.getId()),
            fromString(categoryList.get(1).getId()));
    var response =
        this.testRestTemplate.exchange(
            requestUrl,
            HttpMethod.PUT,
            new HttpEntity<>(request, getRequestHttpEntity()),
            ErrorResponse.class);

    // Then
    then(response).isNotNull();
    then(response.getStatusCode()).isEqualTo(OK);
  }
}
