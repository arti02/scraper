package leon.screen.scraper.entity;

import leon.screen.scraper.enums.SportFamily;

import static leon.screen.scraper.processing.GetMatchesInfoService.COMMA;

public record League(Long id, SportFamily sport, String name) {
    @Override
    public String toString() {
        return sport + COMMA + name;
    }
}
