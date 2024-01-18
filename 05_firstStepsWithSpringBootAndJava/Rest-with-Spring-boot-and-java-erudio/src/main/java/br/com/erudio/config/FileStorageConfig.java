package br.com.erudio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//utilizamos annotation abaixo para passar para o spring que as propiedades utilizadas estão no arquetipo file.
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {
	
	private String uploadDir;

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	

}
