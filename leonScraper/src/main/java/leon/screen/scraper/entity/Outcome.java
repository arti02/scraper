package leon.screen.scraper.entity;

import static leon.screen.scraper.processing.GetMatchesInfoService.COMMA;

public record Outcome(String name, double coefficient, String id){
    @Override
    public String toString() {
        return name + COMMA + coefficient + COMMA + id;
    }
}
