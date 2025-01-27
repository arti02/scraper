package leon.screen.scraper.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import leon.screen.scraper.entity.Market;
import leon.screen.scraper.entity.Match;
import leon.screen.scraper.entity.Outcome;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record JsonToMatchesMapper() {

    public List<Match> map(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        List<Match> matches = new ArrayList<>();
        int count = 0;
        JsonNode events = rootNode.get("events");

        for (JsonNode event : events) {
            String matchName = event.get("nameDefault").asText();
            long kickoffTime = event.get("kickoff").asLong();
            String dateTime = java.time.Instant.ofEpochMilli(kickoffTime).toString();
            long matchId = event.get("id").asLong();

            List<Market> markets = new ArrayList<>();
            JsonNode marketsNode = event.get("markets");

            if (count == 2){
                break;
            }
            if (marketsNode == null){
                continue;
            }
            marketsNode.forEach(marketNode -> {
                String marketName = marketNode.get("name").asText();
                List<Outcome> outcomes = new ArrayList<>();

                JsonNode runners = marketNode.get("runners");
                runners.forEach(runner -> {
                    String outcomeName = runner.get("name").asText();
                    double price = runner.get("price").asDouble();
                    String outcomeId = runner.get("id").asText();
                    outcomes.add(new Outcome(outcomeName, price, outcomeId));
                });

                markets.add(new Market(marketName, outcomes));
            });

            matches.add(new Match(matchName, dateTime, matchId, markets));
            count++;
        }

        return matches;
    }
}
