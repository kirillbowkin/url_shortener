package kirill.bowkin.urlshortener.dto;

public record StatOneResponseDto(String link,
                                 String original,
                                 int rank,
                                 int count) {
}
