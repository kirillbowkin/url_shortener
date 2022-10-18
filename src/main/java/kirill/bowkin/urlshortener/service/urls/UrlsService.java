package kirill.bowkin.urlshortener.service.urls;


import kirill.bowkin.urlshortener.entity.UrlsEntity;
import kirill.bowkin.urlshortener.exception.UrlAlreadyExistsException;
import kirill.bowkin.urlshortener.exception.UrlFailedToSaveException;
import kirill.bowkin.urlshortener.repository.UrlsRankViewRepository;
import kirill.bowkin.urlshortener.repository.UrlsRepository;
import kirill.bowkin.urlshortener.service.shortUrlGenerator.ShortUrlGenerator;
import kirill.bowkin.urlshortener.view.UrlsWithRankView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrlsService {
    private final UrlsRepository urlsRepository;
    private final UrlsRankViewRepository urlsRankViewRepository;
    private final ShortUrlGenerator shortUrlGenerator;
    private final Logger logger = LoggerFactory.getLogger(UrlsService.class);
    private final int RETRY_NUM = 3;

    public UrlsService(UrlsRepository urlsRepository, UrlsRankViewRepository urlsRankViewRepository, ShortUrlGenerator shortUrlGenerator) {
        this.urlsRepository = urlsRepository;
        this.urlsRankViewRepository = urlsRankViewRepository;
        this.shortUrlGenerator = shortUrlGenerator;
    }

    public List<UrlsWithRankView> findAllRankView(Pageable pageable){
        return urlsRankViewRepository.findAll(pageable);
    }

    public Optional<UrlsWithRankView> findOneUrlsRankView(String shortUrl) {
        return urlsRankViewRepository.findById(shortUrl);
    }

    public void incrementUrlCounter(String shortUrl) {
        urlsRepository.incrementUrlCounter(shortUrl);
    }

    public Optional<UrlsEntity> findUrl(String shortUrl) {
        return urlsRepository.findById(shortUrl);
    }

    public UrlsEntity saveUrl(String url) throws UrlFailedToSaveException {
        Optional<UrlsEntity> urlsEntityOptional = urlsRepository.findByUrl(url);
        if(urlsEntityOptional.isPresent()) {
            return urlsEntityOptional.get();
        }

        UrlsEntity urlsEntity = new UrlsEntity();
        urlsEntity.setUrl(url);

        for (int i = 0; i < RETRY_NUM; i++) {
            String shortUrl = shortUrlGenerator.generateShortUrl(url + Math.random());
            urlsEntity.setShortUrl(shortUrl);
            try {
                UrlsEntity urlsEntitySaved = addUrlIfNotExists(urlsEntity);
                logger.info("IN save - Url {} saved successfully", url);
                return urlsEntitySaved;
            } catch (UrlAlreadyExistsException e) {
                logger.error("IN save - Failed to save url {} it's already exist, retry", url);
                e.printStackTrace();
            }
        }

        logger.error("IN save - Failed to save url {} after {} retries", url, RETRY_NUM);
        throw new UrlFailedToSaveException("IN save - Failed to save url");
    }

    private UrlsEntity addUrlIfNotExists(UrlsEntity urlsEntity) throws UrlAlreadyExistsException {
        boolean exists = urlsRepository.existsById(urlsEntity.getShortUrl());
        if (exists) {
            throw new UrlAlreadyExistsException("Url already exists in db");
        }

        return urlsRepository.save(urlsEntity);
    }
}
