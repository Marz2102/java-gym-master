package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimetableTest {
    private static Timetable timetable;

    @BeforeEach
    public void createTimetable() {
        timetable = new Timetable();
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size()),
                () -> Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty())
        );
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size()),
                () -> Assertions.assertEquals(new TimeOfDay(13, 0), timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstKey()),
                () -> Assertions.assertEquals(new TimeOfDay(20, 0), timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastKey()),
                () -> Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstEntry().getValue().size()),
                () -> Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastEntry().getValue().size()),
                () -> Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty())
        );
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TimeOfDay firstDateTime = new TimeOfDay(13, 0);
        TimeOfDay secondDateTime = new TimeOfDay(14, 0);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, firstDateTime).size()),
                () -> Assertions.assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, secondDateTime).isEmpty())
        );
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachChild = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Плавание для взрослых", Age.ADULT, 60);
        Coach coachAdult = new Coach("Иванов", "Михаил", "Сергеевич");

        TrainingSession singleTrainingSessionChild = new TrainingSession(groupChild, coachChild,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession singleTrainingSessionAdult = new TrainingSession(groupAdult, coachAdult,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));


        timetable.addNewTrainingSession(singleTrainingSessionChild);
        timetable.addNewTrainingSession(singleTrainingSessionAdult);

        TimeOfDay firstDateTime = new TimeOfDay(13, 0);

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, firstDateTime).size());
    }

    @Test
    void testGetGroupAndCoachForTrainingSession() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSessionChild = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSessionChild);

        Assertions.assertAll(
                () -> Assertions.assertEquals(group, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).getFirst().getGroup()),
                () -> Assertions.assertEquals(coach, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).getFirst().getCoach())
        );
    }

    @Test
    void testGetTrainingSessionsForEmptyTimetable() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).isEmpty()),
                () -> Assertions.assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0)).isEmpty())
        );
    }

    @Test
    void testGetCountByCoaches() {
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachChild = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Плавание для взрослых", Age.ADULT, 60);
        Coach coachAdult = new Coach("Иванов", "Михаил", "Сергеевич");

        TrainingSession firstTrainingSessionChild = new TrainingSession(groupChild, coachChild,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession firstTrainingSessionAdult = new TrainingSession(groupAdult, coachAdult,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession secondTrainingSessionChild = new TrainingSession(groupChild, coachChild,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(firstTrainingSessionChild);
        timetable.addNewTrainingSession(firstTrainingSessionAdult);
        timetable.addNewTrainingSession(secondTrainingSessionChild);

        Assertions.assertAll(
                () -> Assertions.assertEquals(coachChild, timetable.getCountByCoaches().getFirst().getCoach()),
                () -> Assertions.assertEquals(coachAdult, timetable.getCountByCoaches().getLast().getCoach()),
                () -> Assertions.assertEquals(2, timetable.getCountByCoaches().getFirst().getCountOfTrainings()),
                () -> Assertions.assertEquals(1, timetable.getCountByCoaches().getLast().getCountOfTrainings())
        );
    }

    @Test
    void testGetCountByCoachesForEmptyTimetable() {
        Assertions.assertTrue(timetable.getCountByCoaches().isEmpty());
    }
}
