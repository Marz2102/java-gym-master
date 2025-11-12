package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private final Coach coach;
    private final int countOfTrainings;

    public CounterOfTrainings(Coach coach, int countOfTrainings) {
        this.coach = coach;
        this.countOfTrainings = countOfTrainings;
    }

    public int getCountOfTrainings() {
        return countOfTrainings;
    }

    public Coach getCoach() {
        return coach;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return Integer.compare(o.countOfTrainings, countOfTrainings);
    }

    @Override
    public String toString() {
        return "Coach: " + coach + '\n' +
                "countOfTrainings:" + countOfTrainings;
    }
}
