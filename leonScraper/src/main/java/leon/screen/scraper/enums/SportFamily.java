package leon.screen.scraper.enums;

import java.util.Arrays;

import static java.util.Arrays.stream;

public enum SportFamily {

    SOCCER("Soccer"),
    ICE_HOCKEY("IceHockey"),
    BASKETBALL("Basketball"),
    TENNIS("Tennis");

    private String name;
    SportFamily(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SportFamily getByName(String name){
        return stream(SportFamily.values())
                .filter(s-> s.getName().equals(name))
                .findFirst().orElse(null);

    }


}
