package tutorialapp;

public class EnvConstants {
    public static final int STARTING_USERS;
    public static final int INCREMENT_RATE;
    public static final int INCREMENT_TIMES;
    public static final int RAMP_DURATION;
    public static final int LEVEL_DURATION;
    public static final String API_BASE_URL;

    static {
        STARTING_USERS = Integer.getInteger("STARTING_USERS", 10);
        INCREMENT_RATE = Integer.getInteger("INCREMENT_RATE", 5);
        INCREMENT_TIMES = Integer.getInteger("INCREMENT_TIMES", 5);
        RAMP_DURATION = Integer.getInteger("RAMP_DURATION", 10);
        LEVEL_DURATION = Integer.getInteger("LEVEL_DURATION", 10);
        API_BASE_URL = System.getProperty("API_BASE_URL",
                "http://ec2-43-205-199-146.ap-south-1.compute.amazonaws.com:8080/api");
        System.out.println(STARTING_USERS + ", " + INCREMENT_RATE + ", " + INCREMENT_TIMES + ", " + RAMP_DURATION + ", "
                + LEVEL_DURATION + ", " + API_BASE_URL);
    }
}
