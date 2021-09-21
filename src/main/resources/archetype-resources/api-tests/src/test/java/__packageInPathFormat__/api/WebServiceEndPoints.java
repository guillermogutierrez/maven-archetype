#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api;

public enum WebServiceEndPoints {
  BASE_URL(System.getenv("BASE_URL")),
  CATEGORY("/category"),
  STATUS("/health"),
  MENU("${apiPath}"),
  MENU_V2("/v2/menu"),
  ITEMS("/items");

  private final String url;

  WebServiceEndPoints(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}
