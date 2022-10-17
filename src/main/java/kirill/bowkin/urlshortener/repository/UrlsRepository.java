package kirill.bowkin.urlshortener.repository;


import kirill.bowkin.urlshortener.entity.UrlsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UrlsRepository extends JpaRepository<UrlsEntity, String> {
    Optional<UrlsEntity> findByUrl(String url);

    @Modifying
    @Transactional
    @Query(value = "UPDATE urls SET count = count + 1 WHERE short_url = :shortUrl",
            nativeQuery = true)
    void incrementCounter(@Param("shortUrl") String shortUrl);

}
