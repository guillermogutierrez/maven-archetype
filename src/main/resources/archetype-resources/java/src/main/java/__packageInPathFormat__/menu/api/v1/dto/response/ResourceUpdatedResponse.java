#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.menu.api.v1.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author ArathyKrishna */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceUpdatedResponse {
  @JsonProperty("id")
  private UUID id = null;
}
