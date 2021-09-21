#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.menu.api.v1.impl;

import static org.springframework.http.HttpStatus.OK;

import ${package}.menu.api.v1.UpdateCategoryController;
import ${package}.menu.api.v1.dto.request.UpdateCategoryRequest;
import ${package}.menu.api.v1.dto.response.ResourceUpdatedResponse;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for updating category.
 *
 * @author ArathyKrishna
 */
@RestController
public class UpdateCategoryControllerImpl implements UpdateCategoryController {

  public UpdateCategoryControllerImpl() {}

  @Override
  public ResponseEntity<ResourceUpdatedResponse> updateMenuCategory(
      UUID menuId, UUID categoryId, @Valid UpdateCategoryRequest body, String correlationId) {
    return new ResponseEntity<>(new ResourceUpdatedResponse(UUID.randomUUID()), OK);
  }
}
