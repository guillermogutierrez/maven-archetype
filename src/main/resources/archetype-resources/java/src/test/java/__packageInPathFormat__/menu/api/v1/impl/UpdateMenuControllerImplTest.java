#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.menu.api.v1.impl;

import static ${package}.menu.domain.MenuHelper.createMenu;
import static ${package}.util.TestHelper.getBaseURL;
import static ${package}.util.TestHelper.getRequestHttpEntity;
import static org.assertj.core.api.BDDAssertions.then;

import ${package}.menu.api.v1.dto.request.UpdateMenuRequest;
import ${package}.menu.api.v1.dto.response.ResourceUpdatedResponse;
import ${package}.menu.domain.Menu;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Tag("Integration")
@ActiveProfiles("test")
class UpdateMenuControllerImplTest {

  public static final String UPDATE_MENU = "%s${apiPath}/%s";

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate testRestTemplate;

  @Test
  void testUpdateSuccess() {
    // Given
    Menu menu = createMenu(0);

    UpdateMenuRequest request = new UpdateMenuRequest("new name", "new description", false);

    // When
    var response =
        this.testRestTemplate.exchange(
            String.format(UPDATE_MENU, getBaseURL(port), menu.getId()),
            HttpMethod.PUT,
            new HttpEntity<>(request, getRequestHttpEntity()),
            ResourceUpdatedResponse.class);

    // Then
    then(response).isNotNull();
    then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
