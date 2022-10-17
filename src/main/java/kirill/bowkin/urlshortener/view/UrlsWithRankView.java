package kirill.bowkin.urlshortener.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
public class UrlsWithRankView {

    @Id
    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "url")
    private String url;

    @Column(name = "rank")
    private int rank;

    @Column(name = "count")
    private int count;

    public UrlsWithRankView() {
    }

    public UrlsWithRankView(String shortUrl, String url, int rank, int count) {
        this.shortUrl = shortUrl;
        this.url = url;
        this.rank = rank;
        this.count = count;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public int getRank() {
        return rank;
    }

    public int getCount() {
        return count;
    }

}
