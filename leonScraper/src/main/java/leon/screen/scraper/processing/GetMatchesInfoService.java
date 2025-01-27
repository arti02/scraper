package leon.screen.scraper.processing;

import leon.screen.scraper.api.ApiConfig;
import leon.screen.scraper.entity.League;
import leon.screen.scraper.entity.Match;
import leon.screen.scraper.mappers.JsonToMatchesMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static java.util.concurrent.CompletableFuture.runAsync;

@Service
public record GetMatchesInfoService(CreateGetRequestOperation createGetRequestOperation,
                                    JsonToMatchesMapper jsonToMatchesMapper,
                                    ApiConfig apiConfig,

                                    @Qualifier("applicationTaskExecutor") Executor executor) {


    public static final String COMMA = ", ";
    public static final String TAB = "  ";
    public static final String INSERT_LEAGUES_ID = "{leagueId}";

    public void execute(List<League> leagues) {
        List<CompletableFuture<String>> futures = leagues.stream()
                .map(league -> CompletableFuture.supplyAsync(() -> processMatch(league), executor))
                .toList();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        futures.forEach(future -> {
            try {
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private String processMatch(League league) {
        StringBuilder output = new StringBuilder();
        String response = createGetRequestOperation
                .execute(apiConfig.getMatchesPath().replace(INSERT_LEAGUES_ID, league.id().toString()), true);
        try {
            if (response == null) {
                return "";
            }
            List<Match> result = jsonToMatchesMapper.map(response);
            output.append(league).append("\n");
            result.forEach(m -> {
                output.append(TAB).append(m).append("\n");
                m.markets().forEach(market -> {
                    output.append(TAB).append(TAB).append(market.marketName()).append("\n");
                    market.outcomes().forEach(outcome ->
                            output.append(TAB).append(TAB).append(TAB).append(outcome).append("\n"));
                });
            });
            output.append("--------------------------------------------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }


}
