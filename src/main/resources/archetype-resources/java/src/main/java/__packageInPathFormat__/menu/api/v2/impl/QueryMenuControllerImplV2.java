#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.menu.api.v2.impl;

import ${package}.menu.api.v1.dto.response.MenuDTO;
import ${package}.menu.api.v2.QueryMenuControllerV2;
import ${package}.menu.domain.Menu;
import ${package}.menu.mappers.DomainToDtoMapper;
import java.util.ArrayList;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryMenuControllerImplV2 implements QueryMenuControllerV2 {

  private DomainToDtoMapper mapper;

  public QueryMenuControllerImplV2(DomainToDtoMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public ResponseEntity<MenuDTO> getMenu(UUID id, String correlationId) {
    String restaurantId = "3930ddff-82ce-4f7e-b910-b0709b276cf0";

    Menu menu =
        new Menu(
            id.toString(), restaurantId, "0 Menu", "0 Menu Description", new ArrayList<>(), true);

    return ResponseEntity.ok(mapper.toMenuDto(menu));
  }
}
