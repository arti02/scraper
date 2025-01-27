package leon.screen.scraper.entity;

import java.util.List;

import static leon.screen.scraper.processing.GetMatchesInfoService.COMMA;

public record Match(String name, String dateTime, Long id, List<Market> markets) {
    @Override
    public String toString() {
        return  name + COMMA + dateTime  + COMMA + id;
    }
}
