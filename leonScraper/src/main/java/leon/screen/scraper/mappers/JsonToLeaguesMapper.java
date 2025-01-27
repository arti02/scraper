package leon.screen.scraper.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import leon.screen.scraper.entity.League;
import leon.screen.scraper.enums.SportFamily;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static leon.screen.scraper.enums.SportFamily.getByName;

@Service
public record JsonToLeaguesMapper() {

    public List<League> map(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        List<League> result = new ArrayList<>();
        for (JsonNode sportNode : rootNode) {
            JsonNode regions = sportNode.get("regions");
            int counter = 0;

            regionLoop:
            for (JsonNode region : regions) {
                JsonNode leagues = region.get("leagues");

                for (JsonNode league : leagues) {
                    if (counter == 2) {
                        break regionLoop;
                    }
                    boolean isTopLeague = league.get("top").asBoolean();
                    if (isTopLeague) {
                        SportFamily family = getByName(sportNode.get("family").asText());
                        if (family != null) {
                            result.add(new League(league.get("id").asLong(), family, league.get("name").asText()));
                            counter++;
                        }
                    }
                }
            }
        }
        return result;
    }


}
