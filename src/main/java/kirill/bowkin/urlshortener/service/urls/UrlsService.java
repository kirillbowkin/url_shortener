package kirill.bowkin.urlshortener.service.urls;


import kirill.bowkin.urlshortener.entity.UrlsEntity;
import kirill.bowkin.urlshortener.exception.UrlAlreadyExistsException;
import kirill.bowkin.urlshortener.exception.UrlFailedToSaveException;
import kirill.bowkin.urlshortener.repository.UrlsRepository;
import kirill.bowkin.urlshortener.service.shortUrlGenerator.ShortUrlGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlsService {
    private final UrlsRepository urlsRepository;
    private final ShortUrlGenerator shortUrlGenerator;

    public UrlsService(UrlsRepository urlsRepository, ShortUrlGenerator shortUrlGenerator) {
        this.urlsRepository = urlsRepository;
        this.shortUrlGenerator = shortUrlGenerator;
    }

    public void incrementCounter(String shortUrl) {
        urlsRepository.incrementCounter(shortUrl);
    }

    public Optional<UrlsEntity> find(String shortUrl) {
        return urlsRepository.findById(shortUrl);
    }

    public UrlsEntity save(String url) throws UrlFailedToSaveException {
        Optional<UrlsEntity> urlsEntityOptional = urlsRepository.findByUrl(url);
        if(urlsEntityOptional.isPresent()) {
            return urlsEntityOptional.get();
        }

        UrlsEntity urlsEntity = new UrlsEntity();
        urlsEntity.setUrl(url);

        int retry_num = 3;
        for (int i = 0; i < retry_num; i++) {
            String shortUrl = shortUrlGenerator.generateShortUrl(url + Math.random());
            urlsEntity.setShortUrl(shortUrl);
            try {
                return addIfNotExists(urlsEntity);
            } catch (UrlAlreadyExistsException e) {
                e.printStackTrace();
            }
        }

        throw new UrlFailedToSaveException("Failed to save url");
    }

    private UrlsEntity addIfNotExists(UrlsEntity urlsEntity) throws UrlAlreadyExistsException {
        boolean exists = urlsRepository.existsById(urlsEntity.getShortUrl());
        if (exists) {
            throw new UrlAlreadyExistsException("Url already exists in db");
        }

        return urlsRepository.save(urlsEntity);
    }
}
