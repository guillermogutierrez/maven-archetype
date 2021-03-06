#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.menu.api.v1.impl;

import static ${package}.menu.domain.MenuHelper.createMenu;
import static ${package}.util.TestHelper.getBaseURL;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import ${package}.core.api.dto.ErrorResponse;
import ${package}.menu.api.v1.dto.request.CreateItemRequest;
import ${package}.menu.api.v1.dto.response.ResourceCreatedResponse;
import ${package}.menu.domain.Category;
import ${package}.menu.domain.Menu;
import java.util.ArrayList;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Tag("Integration")
@ActiveProfiles("test")
class CreateItemControllerImplTest {

  public static final String CREATE_ITEM = "%s${apiPath}/%s/category/%s/items";
  @LocalServerPort private int port;

  @Autowired private TestRestTemplate testRestTemplate;

  @Test
  void testAddItem() {
    // Given
    Menu menu = createMenu(1);
    Category category =
        new Category(randomUUID().toString(), "cat name", "cat description", new ArrayList<>());
    menu.addOrUpdateCategory(category);

    CreateItemRequest request =
        new CreateItemRequest("Some Name", "Some Description", 13.56d, true);

    // When
    var response =
        this.testRestTemplate.postForEntity(
            String.format(CREATE_ITEM, getBaseURL(port), menu.getId(), category.getId()),
            request,
            ResourceCreatedResponse.class);

    // Then

    then(response).isNotNull();
    then(response.getBody()).isNotNull();
    then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  void testInvalidCategoryIdWilThrowBadRequest() {

    // Given
    CreateItemRequest request =
        new CreateItemRequest("Some Name", "Some Description", 13.56d, true);

    // When
    var response =
        this.testRestTemplate.postForEntity(
            String.format(CREATE_ITEM, getBaseURL(port), randomUUID(), "xyz"),
            request,
            ErrorResponse.class);

    // Then
    then(response).isNotNull();
    then(response.getStatusCode()).isEqualTo(BAD_REQUEST);
  }
}
