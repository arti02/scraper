package leon.screen.scraper.entity;

import java.util.List;

public record Market(String marketName, List<Outcome> outcomes) {
    @Override
    public String toString() {
        return marketName +"\n" ;
    }
}
