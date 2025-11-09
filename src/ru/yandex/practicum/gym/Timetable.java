package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();
    private final Map<Coach, Integer> coachTrainings = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        Coach coach = trainingSession.getCoach();

        if (!timetable.containsKey(dayOfWeek)) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }
        if (!timetable.get(dayOfWeek).containsKey(timeOfDay)) {
            timetable.get(dayOfWeek).put(timeOfDay, new ArrayList<>());
        }
        timetable.get(dayOfWeek).get(timeOfDay).add(trainingSession);

        if (!coachTrainings.containsKey(coach)) {
            coachTrainings.put(coach, 0);
        }
        coachTrainings.put(coach, coachTrainings.get(coach) + 1);
    }

    public Map<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return Collections.unmodifiableMap(timetable.get(dayOfWeek));
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return Collections.unmodifiableList(timetable.get(dayOfWeek).get(timeOfDay));
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        List<CounterOfTrainings> counterOfTrainings = new ArrayList<>();
        for (Coach coach : coachTrainings.keySet()) {
            counterOfTrainings.add(new CounterOfTrainings(coach, coachTrainings.get(coach)));
        }

        Collections.sort(counterOfTrainings);
        return Collections.unmodifiableList(counterOfTrainings);
    }
}
