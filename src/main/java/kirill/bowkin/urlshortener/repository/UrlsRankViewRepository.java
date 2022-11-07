package kirill.bowkin.urlshortener.repository;

import kirill.bowkin.urlshortener.view.UrlsWithRankView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UrlsRankViewRepository extends Repository<UrlsWithRankView, String> {

    @Query(value =
            "select * " +
                    "from " +
                    "(select short_url," +
                    " url," +
                    " rank() over (order by count desc) rank," +
                    " count from urls) subQuery" +
                    " where subQuery.short_url = :shortUrl",
            nativeQuery = true)
    Optional<UrlsWithRankView> findById(@Param("shortUrl") String shortUrl);


    @Query(value =
            "select " +
                    "short_url," +
                    " url," +
                    " rank() over (order by count desc) rank," +
                    " count " +
                    "from urls",
            nativeQuery = true)
    List<UrlsWithRankView> findAll(Pageable pageable);


}
