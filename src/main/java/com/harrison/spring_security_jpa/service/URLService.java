package com.harrison.spring_security_jpa.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<Url> getAllUrlsByUsername(String username) {
		return this.urlRepository.findAllByUserUsername(username);
	}

	public Url getShortenedUrl(Url url) {
		return urlRepository.findByCompleteUrl(url.getCompleteUrl());
	}

	public Url registerUrl(Url url) {
		url.setRegistrationData(new Date(Calendar.getInstance().getTimeInMillis()));
		// Se a url completa for válida salva salve-a no banco
		if (urlValidator.isValid(url.getCompleteUrl())) {
			return this.urlRepository.save(generateShortenedUrl(url));
		}
		return null;
	}

	public boolean updateUrl(Url url) {
		System.out.println(this.urlValidator.isValid(url.getCompleteUrl()));
		if(this.urlValidator.isValid(url.getCompleteUrl())) {
			this.urlRepository.save(url);
			return true;
		}
		return false;
	}

	public boolean deleteUrl(long id) {
		this.urlRepository.deleteById(id);
		return true;
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
