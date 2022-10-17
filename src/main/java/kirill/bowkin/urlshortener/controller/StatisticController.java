package kirill.bowkin.urlshortener.controller;

import kirill.bowkin.urlshortener.dto.StatOneResponseDto;
import kirill.bowkin.urlshortener.service.urlBuilder.UrlBuilder;
import kirill.bowkin.urlshortener.service.urls.UrlsService;
import kirill.bowkin.urlshortener.view.UrlsWithRankView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StatisticController {

    private final UrlsService urlsService;
    @Value("${hostname}")
    private String hostname;

    public StatisticController(UrlsService urlsService) {
        this.urlsService = urlsService;
    }

    @GetMapping("/stats/{shortName}")
    public StatOneResponseDto statForOne(@PathVariable("shortName") String shortName) {
        String shortUrl = UrlBuilder.buildUrl(hostname, "/", shortName);

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short url wasn't found");
        }
    }

    @GetMapping("/stats")
    public List<StatOneResponseDto> statForAll(@RequestParam("page") int page, @RequestParam("count") int count) {

        List<UrlsWithRankView> allRankView = urlsService.findAllRankView(PageRequest.of(page, count));

        List<StatOneResponseDto> response = allRankView.stream()
                .map(rankView -> new StatOneResponseDto(
                        rankView.getShortUrl(),
                        rankView.getUrl(),
                        rankView.getRank(),
                        rankView.getCount())
                ).toList();

        return response;
    }
}
