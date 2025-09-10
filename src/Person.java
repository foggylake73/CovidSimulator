import java.util.Random;


public class Person {
    static int infectedNum = 0;
    static int deadNum = 0;
    int minDaysIncubationPeriod = 2;
    int maxDaysIncubationPeriod = 14;
    int daysForIncubation = 0;
    int daysNeededForIncubation = 0;
    int daysAtHome = 0;
    int daysNeededAtHome = 5;
    int minDaysContagious = 0;
    int maxDaysContagious = 3;
    int daysContagious = 0;

    enum HealthStatus {
        HEALTHY,
        INFECTED,
        INCUBATION,
        ATHOME,
        DEAD
    }
    HealthStatus personHealthStatus = HealthStatus.HEALTHY;

    public Person() {

    }

    static void resetNum() {
        infectedNum = 0;
        deadNum = 0;
    }

    boolean isHealthy() {
        return (personHealthStatus == HealthStatus.HEALTHY);
    }
    boolean isInfected() {
        return (personHealthStatus == HealthStatus.INFECTED);
    }
    boolean isIncubation() {
        return (personHealthStatus == HealthStatus.INCUBATION);
    }
    boolean isAtHome() { return (personHealthStatus == HealthStatus.ATHOME); }
    boolean isDead() { return (personHealthStatus == HealthStatus.DEAD); }
    boolean isContagious() {
        if (isIncubation() && daysNeededForIncubation - daysForIncubation <= daysContagious) {
            return true;
        }
        return false;
    }

    // runs after school because it goes: out-of-school, in-school, check and update
    void updateStatus(double deathRateWeekly) {
        Random rand = new Random();

        if (isInfected()) {
            daysNeededForIncubation = (int) (rand.nextDouble() * (maxDaysIncubationPeriod - minDaysIncubationPeriod + 1) + minDaysIncubationPeriod);
            daysContagious = (int) (rand.nextDouble() * (maxDaysContagious - minDaysContagious + 1) + minDaysContagious);
            personHealthStatus = HealthStatus.INCUBATION;
        }

        if (isIncubation()) {
            if (daysForIncubation < daysNeededForIncubation) {
                daysForIncubation++;
            } else {
                infectedNum++;
                personHealthStatus = HealthStatus.ATHOME;

                daysForIncubation = 0;
                daysNeededForIncubation = 0;
            }
        }

        if (isAtHome()) {
            if (rand.nextDouble() <= deathRateWeekly) {
                deadNum++;
                personHealthStatus = HealthStatus.DEAD;
                return;
            }

            if (daysAtHome < daysNeededAtHome) {
                daysAtHome++;
            } else {
                personHealthStatus = HealthStatus.HEALTHY;
                daysAtHome = 0;
            }
        }
    }

    void updateInfection(double infectionRateWeekly) {
        if (isHealthy()) {
            Random rand = new Random();
            if (rand.nextDouble() <= infectionRateWeekly) {
                personHealthStatus = HealthStatus.INFECTED;
            }
        }
    }
}
