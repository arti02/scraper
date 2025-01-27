package leon.screen.scraper.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfig {
    private String leaguePath;

    private String matchesPath;

    public String getLeaguePath() {
        return leaguePath;
    }

    public void setLeaguePath(String leaguePath) {
        this.leaguePath = leaguePath;
    }

    public String getMatchesPath() {
        return matchesPath;
    }

    public void setMatchesPath(String matchesPath) {
        this.matchesPath = matchesPath;
    }
}
