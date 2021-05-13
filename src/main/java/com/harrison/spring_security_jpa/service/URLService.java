package com.harrison.spring_security_jpa.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.harrison.spring_security_jpa.exception.MyException;
import com.harrison.spring_security_jpa.model.Url;
import com.harrison.spring_security_jpa.repository.UrlRepository;

@Service
public class URLService {
	@Autowired
	private UrlRepository urlRepository;
	@Autowired
	private UrlValidator urlValidator;

	/** Regex utilizada para extrair domínio de url */
	String domainRegex = "(https?://)?(www\\.)?.+((\\.com)|(\\.br)|(\\.com.br)|(\\.org)|(\\.io)){1}(:\\d{1,5})?";

	public List<Url> getAllUrlsByUsername(String username){
		return this.urlRepository.findAllByUserUsername(username);
	}

	public Url getUrl(long id) throws MyException{
		Optional<Url> url = urlRepository.findById(id);
		url.orElseThrow(() -> new MyException("Recurso não encontrado", HttpStatus.NOT_FOUND));
		return url.get();
	}

	public Url cadasterUrl(Url url) throws MyException {
		url.setRegistrationData(new Date(Calendar.getInstance().getTimeInMillis())); 
		// Se a url completa for válida salva salve-a no banco
		if (urlValidator.isValid(url.getCompleteUrl())) {
			try {
				return this.urlRepository.save(generateShortenedUrl(url));
			}catch (DataIntegrityViolationException e) {
				throw new MyException("Ocorreu um erro. Não foi possível salvar o recurso, já existe um recurso igual cadastrado",HttpStatus.BAD_REQUEST);
			}
		}
		return null;
	}

	public Url updateUrl(Url url,long id) throws MyException {
		if(this.urlValidator.isValid(url.getCompleteUrl())) {
			Optional<Url> urlReturned = this.urlRepository.findById(url.getId());
			urlReturned.orElseThrow(()-> new MyException("Não foi possível atualizar o recurso, uma vez que ele não existe",HttpStatus.NOT_FOUND));
			url.setId(id);
			return this.urlRepository.save(url);
		}
		throw new MyException("Ocorreu um erro. Não foi possível atualizar o recurso, uma vez que a completeUrl informada é inválida",HttpStatus.BAD_REQUEST);
	}

	public boolean deleteUrl(long id) throws MyException {
		try {
			this.urlRepository.deleteById(id);
			return true;
		}catch (EmptyResultDataAccessException e) {
			throw new MyException("Ocorreu um erro. Recurso informada não existe, logo não é possível deletá-lo", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			throw new MyException("Ocorreu um erro. Não foi possível deletar", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Gera uma url encurtada
	 * 
	 * @param url
	 * @return Url
	 */
	public Url generateShortenedUrl(Url url) {
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String domain = "", path = "";
		
		// Remove barra no final da url completa, caso exista
		url.setCompleteUrl(url.getCompleteUrl().split("/$")[0]);
		// Gerando path com 5 dígitos para a shortened url
		for (int x = 1; x <= 5; x++) {
			path += characters.charAt((int) Math.floor(Math.random() * characters.length()));
		}
		// Extrai o domínio da url completa
		Matcher matcher = Pattern.compile(domainRegex, Pattern.CASE_INSENSITIVE).matcher(url.getCompleteUrl());
		if (matcher.find()) {
			domain = url.getCompleteUrl().substring(matcher.start(), matcher.end());
		}
		// Seta o valor do domínio concatenado com o path de 5 dígitos gerado
		url.setShortenedUrl(domain + "/" + path);
		return url;
	}

}
