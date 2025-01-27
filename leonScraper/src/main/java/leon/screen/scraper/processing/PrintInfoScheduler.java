package leon.screen.scraper.processing;

import com.fasterxml.jackson.core.JsonProcessingException;
import leon.screen.scraper.entity.League;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record PrintInfoScheduler(GetTopLeaguesServices topLeaguesServices,
                                 GetMatchesInfoService getMatchesInfoService) {


    @Scheduled(cron = "${cronExpression}")
    public void execute() throws JsonProcessingException {
        List<League> leagues = topLeaguesServices.execute();
        getMatchesInfoService.execute(leagues);
    }


}
