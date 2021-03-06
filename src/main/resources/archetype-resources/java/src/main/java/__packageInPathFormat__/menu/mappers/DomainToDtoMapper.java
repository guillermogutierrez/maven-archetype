#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.menu.mappers;

import ${package}.menu.api.v1.dto.response.CategoryDTO;
import ${package}.menu.api.v1.dto.response.ItemDTO;
import ${package}.menu.api.v1.dto.response.MenuDTO;
import ${package}.menu.api.v1.dto.response.SearchMenuResultItem;
import ${package}.menu.domain.Category;
import ${package}.menu.domain.Item;
import ${package}.menu.domain.Menu;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DomainToDtoMapper {

  public static MenuDTO toMenuDto(Menu menu) {
    return new MenuDTO(
        UUID.fromString(menu.getId()),
        UUID.fromString(menu.getRestaurantId()),
        menu.getName(),
        menu.getDescription(),
        menu.getCategories() != null
            ? menu.getCategories().stream()
                .map(DomainToDtoMapper::toCategoryDto)
                .collect(Collectors.toList())
            : null,
        menu.getEnabled());
  }

  public static CategoryDTO toCategoryDto(Category category) {
    return new CategoryDTO(
        category.getId(),
        category.getName(),
        category.getDescription(),
        category.getItems() != null
            ? category.getItems().stream()
                .map(DomainToDtoMapper::toItemDto)
                .collect(Collectors.toList())
            : null);
  }

  public static ItemDTO toItemDto(Item item) {
    return new ItemDTO(
        item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getAvailable());
  }

  public static SearchMenuResultItem toSearchMenuResultItem(Menu menu) {
    return new SearchMenuResultItem(
        UUID.fromString(menu.getId()),
        UUID.fromString(menu.getRestaurantId()),
        menu.getName(),
        menu.getDescription(),
        menu.getEnabled());
  }
}
