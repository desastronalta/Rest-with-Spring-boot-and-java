package br.com.erudio.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.erudio.config.FileStorageConfig;
import br.com.erudio.exceptions.FileStorageException;

@Service
public class FileStorageServices {
	
	private final Path fileStorageLocation;
	
	@Autowired
	public FileStorageServices(FileStorageConfig fileStorageConfig) {
		Path path = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath().normalize();
		this.fileStorageLocation = path;
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Não foi possivel criar o diretório onde os arquivos serão armazenados" , e);
		}
	}
	
	public String storeFile(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if(filename.contains("..")) {
				throw new FileStorageException("Desculpe o arquivo possui um caminho inválido" + filename);
			}
			//linhas que definem aonde sera salvo o arquivo
			Path targetLocation = this.fileStorageLocation.resolve(filename);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			//até aqui.
			return filename;
		} catch (Exception e) {
			throw new FileStorageException("nao foi possivel armazenar o arquivo: " +filename+". Por favor tente novamente");
		}
	}
}
