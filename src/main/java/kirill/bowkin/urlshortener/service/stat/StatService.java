package kirill.bowkin.urlshortener.service.stat;

import kirill.bowkin.urlshortener.dto.StatOneResponseDto;
import kirill.bowkin.urlshortener.exception.UrlDoesNotExistsException;
import kirill.bowkin.urlshortener.service.urlBuilder.UrlBuilder;
import kirill.bowkin.urlshortener.service.urls.UrlsService;
import kirill.bowkin.urlshortener.view.UrlsWithRankView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatService {
    private final UrlsService urlsService;
    private final Logger logger = LoggerFactory.getLogger(StatService.class);

    public StatService(UrlsService urlsService) {
        this.urlsService = urlsService;
    }

    public StatOneResponseDto statForOne(String shortName) throws UrlDoesNotExistsException {
        String shortUrl = new UrlBuilder()
                .setDelimiter("/l/")
                .setShortenedString(shortName)
                .build();

        Optional<UrlsWithRankView> urlsWithRankViewOptional = urlsService.findOneUrlsRankView(shortUrl);

        if (urlsWithRankViewOptional.isPresent()) {
            UrlsWithRankView urlsWithRankView = urlsWithRankViewOptional.get();
            return new StatOneResponseDto(
                    urlsWithRankView.getShortUrl(),
                    urlsWithRankView.getUrl(),
                    urlsWithRankView.getRank(),
                    urlsWithRankView.getCount()
            );
        } else {
            logger.error("IN statForOne - Short url {} wasn't found", shortName);
            throw new UrlDoesNotExistsException("Url " + shortUrl + " " + "does not exists");
        }
    }

    public List<StatOneResponseDto> statForAll(int page, int count) {
        List<UrlsWithRankView> allRankView = urlsService.findAllRankView(PageRequest.of(page, count));

        return allRankView.stream()
                .map(rankView -> new StatOneResponseDto(
                        rankView.getShortUrl(),
                        rankView.getUrl(),
                        rankView.getRank(),
                        rankView.getCount())
                ).toList();
    }
}
