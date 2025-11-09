package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private Coach coach;
    private int countOfTrainings;

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
        return o.getCountOfTrainings() - this.getCountOfTrainings();
    }
}
