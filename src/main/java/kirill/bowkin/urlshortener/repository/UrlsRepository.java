package kirill.bowkin.urlshortener.repository;


import kirill.bowkin.urlshortener.entity.UrlsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlsRepository extends JpaRepository<UrlsEntity, String> {
    Optional<UrlsEntity> findByUrl(String url);
}
