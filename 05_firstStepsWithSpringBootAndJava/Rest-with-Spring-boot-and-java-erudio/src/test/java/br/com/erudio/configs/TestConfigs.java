package br.com.erudio.configs;

public class TestConfigs {
 /*
  * classe criada para passar as configurações utilizadas nos testes de integração.
  */
	public static final int SERVER_PORT = 8888;
	public static final String HEADER_PARAM_AUTHORIZATION = "Athorization";
	public static final String HEADER_PARAM_ORIGIN = "Origin";
	
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_XML = "application/xml";
	public static final String CONTENT_TYPE_YML = "application/x-yml";
	
	public static final String ORIGIN_LOCAL = "https:localhost:8080";	
	public static final String ORIGIN_WRONG = "https:localhost:9090";	
}
