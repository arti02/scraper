package leon.screen.scraper.processing;

import com.fasterxml.jackson.core.JsonProcessingException;
import leon.screen.scraper.api.ApiConfig;
import leon.screen.scraper.entity.League;
import leon.screen.scraper.mappers.JsonToLeaguesMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public record GetTopLeaguesServices(CreateGetRequestOperation createGetRequestOperation,
                                    JsonToLeaguesMapper jsonToLeaguesMapper,
                                    ApiConfig apiConfig) {

    public List<League> execute() throws JsonProcessingException {
        String respond = createGetRequestOperation.execute(apiConfig.getLeaguePath(), false);
        return jsonToLeaguesMapper.map(respond);
    }
}
