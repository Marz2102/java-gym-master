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
                () -> Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size(), 1),
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
                () -> Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size(), 1),
                () -> Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstKey(), new TimeOfDay(13, 0)),
                () -> Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastKey(), new TimeOfDay(20, 0)),
                () -> Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstEntry().getValue().size(), 1),
                () -> Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastEntry().getValue().size(), 1),
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
                () -> Assertions.assertEquals(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, firstDateTime).size(), 1),
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

        Assertions.assertEquals(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, firstDateTime).size(), 2);
    }

    @Test
    void testGetGroupAndCoachForTrainingSession() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSessionChild = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSessionChild);

        Assertions.assertAll(
                () -> Assertions.assertEquals(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).getFirst().getGroup(), group),
                () -> Assertions.assertEquals(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).getFirst().getCoach(), coach)
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
                () -> Assertions.assertEquals(timetable.getCountByCoaches().getFirst().getCoach(), coachChild),
                () -> Assertions.assertEquals(timetable.getCountByCoaches().getLast().getCoach(), coachAdult),
                () -> Assertions.assertEquals(timetable.getCountByCoaches().getFirst().getCountOfTrainings(), 2),
                () -> Assertions.assertEquals(timetable.getCountByCoaches().getLast().getCountOfTrainings(), 1)
        );
    }

    @Test
    void testGetCountByCoachesForEmptyTimetable() {
        Assertions.assertTrue(timetable.getCountByCoaches().isEmpty());
    }
}
