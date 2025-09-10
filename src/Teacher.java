import java.util.Random;


public class Teacher extends Person {

    public Teacher() {

    }

    @Override
    void updateStatus(double deathRateWeekly) {
        Random rand = new Random();

        if (isInfected()) {
            daysNeededForIncubation = (int) (rand.nextDouble() * (maxDaysIncubationPeriod - minDaysIncubationPeriod + 1) + minDaysIncubationPeriod);
            personHealthStatus = HealthStatus.INCUBATION;
        }

        if (isIncubation()) {
            if (daysForIncubation < daysNeededForIncubation) {
                daysForIncubation++;
            } else {
                infectedNum++;
                if (rand.nextDouble() <= deathRateWeekly) {
                    deadNum++;
                }
                personHealthStatus = HealthStatus.HEALTHY;

                daysForIncubation = 0;
                daysNeededForIncubation = 0;
            }
        }
    }
}
