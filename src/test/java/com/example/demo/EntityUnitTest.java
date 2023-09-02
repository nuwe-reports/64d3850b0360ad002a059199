package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    @BeforeEach
    void setUp() {
        d1 = new Doctor();
        d1.setFirstName("Jhon");
        d1.setLastName("Aristizabal");
        d1.setEmail("jfaswis2@gmail.com");
        d1.setAge(26);

        p1 = new Patient();
        p1.setFirstName("Frijolito");
        p1.setLastName("Ito");
        p1.setEmail("example@gmail.com");
        p1.setAge(26);

        r1 = new Room("Dermatology");

        a1 = new Appointment();
        a1.setDoctor(d1);
        a1.setPatient(p1);
        a1.setRoom(r1);
        a1.setStartsAt(LocalDateTime.of(2023, 1,1,10,0));
        a1.setFinishesAt(LocalDateTime.of(2023,1,1,10, 30));

        a2 = new Appointment();
        a2.setDoctor(d1);
        a2.setPatient(p1);
        a2.setRoom(r1);
        a2.setStartsAt(LocalDateTime.of(2023, 1,1,10,30));
        a2.setFinishesAt(LocalDateTime.of(2023,1,1,11, 0));
    }

    @Test
    public void should_set_id_appointment() {
        Appointment appointment = new Appointment();
        long expectedId = 12345;

        appointment.setId(expectedId);
        long actualId = appointment.getId();

        Assertions.assertEquals(expectedId, actualId);
    }

    @Test
    public void should_set_id_doctor() {
        Doctor doctor = new Doctor();
        long expectedId = 12345;

        doctor.setId(expectedId);
        long actualId = doctor.getId();

        Assertions.assertEquals(expectedId, actualId);
    }

    @Test
    public void should_set_id_patient() {
        Patient patient = new Patient();
        long expectedId = 12345;

        patient.setId(expectedId);
        long actualId = patient.getId();

        Assertions.assertEquals(expectedId, actualId);
    }

    @Test
    public void should_to_instance_room() {
        Room room = new Room();

        Assertions.assertNull(room.getRoomName());
    }

    @Test
    public void should_save_a_doctor_with_setters() {

        entityManager.persistAndFlush(d1);
        Doctor doctor = entityManager.find(Doctor.class, d1.getId());

        assertThat(doctor.getId()).isEqualTo(d1.getId());
        assertThat(doctor.getAge()).isEqualTo(d1.getAge());
        assertThat(doctor.getEmail()).isEqualTo(d1.getEmail());
        assertThat(doctor.getFirstName()).isEqualTo(d1.getFirstName());
        assertThat(doctor.getLastName()).isEqualTo(d1.getLastName());
    }

    @Test
    public void should_save_a_doctor_with_constructor() {
        Doctor d1 = new Doctor("Jhon", "Aristizabal", 26, "jfaswis2@gmail.com");
        entityManager.persistAndFlush(d1);
        Doctor doctor = entityManager.find(Doctor.class, d1.getId());

        assertThat(doctor.getId()).isEqualTo(d1.getId());
        assertThat(doctor.getAge()).isEqualTo(d1.getAge());
        assertThat(doctor.getEmail()).isEqualTo(d1.getEmail());
        assertThat(doctor.getFirstName()).isEqualTo(d1.getFirstName());
        assertThat(doctor.getLastName()).isEqualTo(d1.getLastName());
    }

    @Test
    public void should_save_a_patient_with_setters() {
        entityManager.persistAndFlush(p1);
        Patient patient = entityManager.find(Patient.class, p1.getId());

        assertThat(patient.getId()).isEqualTo(p1.getId());
        assertThat(patient.getAge()).isEqualTo(p1.getAge());
        assertThat(patient.getEmail()).isEqualTo(p1.getEmail());
        assertThat(patient.getFirstName()).isEqualTo(p1.getFirstName());
        assertThat(patient.getLastName()).isEqualTo(p1.getLastName());
    }

    @Test
    public void should_save_a_patient_with_constructor() {
        Patient p1 = new Patient("Ana", "Gonzales", 26, "example@gmail.com");
        entityManager.persistAndFlush(p1);
        Patient patient = entityManager.find(Patient.class, p1.getId());

        assertThat(patient.getId()).isEqualTo(p1.getId());
        assertThat(patient.getAge()).isEqualTo(p1.getAge());
        assertThat(patient.getEmail()).isEqualTo(p1.getEmail());
        assertThat(patient.getFirstName()).isEqualTo(p1.getFirstName());
        assertThat(patient.getLastName()).isEqualTo(p1.getLastName());
    }

    @Test
    public void should_save_a_room() {
        entityManager.persistAndFlush(r1);
        Room room = entityManager.find(Room.class, r1.getRoomName());

        assertThat(room.getRoomName()).isEqualTo(r1.getRoomName());
    }

    @Test
    public void should_save_a_appointment_with_setters() {
        entityManager.persistAndFlush(a1);
        Appointment appointment = entityManager.find(Appointment.class, a1.getId());

        assertThat(appointment.getId()).isEqualTo(a1.getId());
        assertThat(appointment.getDoctor()).isEqualTo(a1.getDoctor());
        assertThat(appointment.getPatient()).isEqualTo(a1.getPatient());
        assertThat(appointment.getStartsAt()).isEqualTo(a1.getStartsAt());
        assertThat(appointment.getRoom()).isEqualTo(a1.getRoom());
        assertThat(appointment.getFinishesAt()).isEqualTo(a1.getFinishesAt());
    }

    @Test
    public void should_save_a_appointment_with_constructor() {
        Appointment a1 = new Appointment(p1, d1, r1, LocalDateTime.of(2023, 1,1,10,0), LocalDateTime.of(2023,1,1,10, 30));
        entityManager.persistAndFlush(a1);
        Appointment appointment = entityManager.find(Appointment.class, a1.getId());

        assertThat(appointment.getId()).isEqualTo(a1.getId());
        assertThat(appointment.getDoctor()).isEqualTo(a1.getDoctor());
        assertThat(appointment.getPatient()).isEqualTo(a1.getPatient());
        assertThat(appointment.getStartsAt()).isEqualTo(a1.getStartsAt());
        assertThat(appointment.getRoom()).isEqualTo(a1.getRoom());
        assertThat(appointment.getFinishesAt()).isEqualTo(a1.getFinishesAt());
    }

    @Test
    public void should_overlaps_same_starts() {
        a3 = new Appointment();
        a3.setDoctor(d1);
        a3.setPatient(p1);
        a3.setRoom(r1);
        a3.setStartsAt(LocalDateTime.of(2023, 1,1,10,0));
        a3.setFinishesAt(LocalDateTime.of(2023,1,1,11, 0));

        boolean result = a1.overlaps(a3);
        Assertions.assertTrue(result);
    }

    @Test
    public void should_overlaps_same_finishes() {
        a3 = new Appointment();
        a3.setDoctor(d1);
        a3.setPatient(p1);
        a3.setRoom(r1);
        a3.setStartsAt(LocalDateTime.of(2023, 1,1,9,30));
        a3.setFinishesAt(LocalDateTime.of(2023,1,1,10, 30));

        boolean result = a1.overlaps(a3);
        Assertions.assertTrue(result);
    }

    @Test
    public void should_overlaps_finishes_is_after_starts_and_finishes_is_before_finishes() {
        a3 = new Appointment();
        a3.setDoctor(d1);
        a3.setPatient(p1);
        a3.setRoom(r1);
        a3.setStartsAt(LocalDateTime.of(2023, 1,1,10,15));
        a3.setFinishesAt(LocalDateTime.of(2023,1,1,11, 0));

        boolean result = a3.overlaps(a1);
        Assertions.assertTrue(result);
    }


    @Test
    public void should_overlaps_starts_is_after_starts_and_starts_is_before_finishes() {
        a3 = new Appointment();
        a3.setDoctor(d1);
        a3.setPatient(p1);
        a3.setRoom(r1);
        a3.setStartsAt(LocalDateTime.of(2023, 1,1,9,45));
        a3.setFinishesAt(LocalDateTime.of(2023,1,1,11, 0));

        a1.setFinishesAt(LocalDateTime.of(2023,1,1,11, 30));

        boolean result = a3.overlaps(a1);
        Assertions.assertTrue(result);
    }

    @Test
    public void should_not_overlaps() {
        boolean result = a1.overlaps(a2);
        Assertions.assertFalse(result);
    }

    @Test
    public void should_not_overlaps_different_room() {
        Room room = new Room("Oncology");

        a3 = new Appointment();
        a3.setDoctor(d1);
        a3.setPatient(p1);
        a3.setRoom(room);
        a3.setStartsAt(LocalDateTime.of(2023, 1,1,10,30));
        a3.setFinishesAt(LocalDateTime.of(2023,1,1,11, 0));

        boolean result = a3.overlaps(a1);
        Assertions.assertFalse(result);
    }

    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */
}
