#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api;

public enum OAuthConfigurations {
  CLIENT_ID(System.getenv("CLIENT_ID")),
  CLIENT_SECRET(System.getenv("CLIENT_SECRET")),
  AUDIENCE(System.getenv("AUDIENCE")),
  GRANT_TYPE(System.getenv("GRANT_TYPE")),
  OAUTH_TOKEN_URL(System.getenv("OAUTH_TOKEN_URL"));

  private final String config;

  OAuthConfigurations(String config) {
    this.config = config;
  }

  public String getOauthConfiguration() {
    return config;
  }
}
