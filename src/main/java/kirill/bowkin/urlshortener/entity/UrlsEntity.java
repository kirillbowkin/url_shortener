package kirill.bowkin.urlshortener.entity;

import javax.persistence.*;

@Entity
@Table(name = "urls")
public class UrlsEntity {
    @Id
    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "url")
    private String url;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "count")
    private Integer count = 0;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public UrlsEntity(String shortUrl, String url, Integer count) {
        this.shortUrl = shortUrl;
        this.url = url;
        this.count = count;
    }

    public UrlsEntity() {
    }
}
