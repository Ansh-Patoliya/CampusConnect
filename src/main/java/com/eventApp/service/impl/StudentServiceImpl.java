package com.eventApp.service.impl;

import com.eventApp.model.Event;
import com.eventApp.model.Student;
import com.eventApp.model.User;
import com.eventApp.repository.StudentRepository;
import com.eventApp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

/**
 * Spring Boot implementation of StudentService.
 * 
 * This is the Spring Boot version of the original StudentServiceImpl class.
 * Changes from original:
 * - Uses Spring's @Service annotation for automatic bean creation
 * - Uses @Autowired for dependency injection instead of manual instantiation
 * - Uses StudentRepository instead of StudentDAO
 * - Uses @Transactional for automatic transaction management
 * - Replaced custom MyEventLL with standard List<Event>
 * - Added proper exception handling with Spring's exception hierarchy
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    // TODO: Add EventRepository when Event entity is converted
    // private final EventRepository eventRepository;
    
    // Session-based storage for current user's events (in real app, use session/security context)
    private List<Event> currentUserEvents = new ArrayList<>();
    private int currentEventIndex = 0;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentByUser(User user) {
        if (user == null) {
            return null;
        }
        return getStudentByUserId(user.getUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return null;
        }
        Optional<Student> student = studentRepository.findById(userId);
        return student.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> sortEventList() {
        // TODO: Implement when Event entity and repository are created
        // For now, return empty list as placeholder
        /*
        // Get current logged-in user (from Spring Security context)
        User currentUser = getCurrentUser();
        
        // Fetch approved, not completed events
        List<Event> eventList = eventRepository.findByStatusAndCompletion("Approved", "Not Completed");
        
        // Fetch interest categories for the user
        List<String> interestList = studentRepository.findInterestsByStudentId(currentUser.getUserId());

        // Sort events: first by interest priority, then by event date
        eventList.sort(Comparator.comparingInt((Event e) -> {
            int index = interestList.indexOf(e.getCategory());
            return index == -1 ? Integer.MAX_VALUE : index; // events not in interests go to the end
        }).thenComparing(Event::getEventDate));

        return eventList;
        */
        return new ArrayList<>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> sortEventListForStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // TODO: Implement when Event entity and repository are created
        /*
        // Fetch approved, not completed events
        List<Event> eventList = eventRepository.findByStatusAndCompletion("Approved", "Not Completed");
        
        // Fetch interest categories for the student
        List<String> interestList = studentRepository.findInterestsByStudentId(studentId);

        // Sort events: first by interest priority, then by event date
        eventList.sort(Comparator.comparingInt((Event e) -> {
            int index = interestList.indexOf(e.getCategory());
            return index == -1 ? Integer.MAX_VALUE : index;
        }).thenComparing(Event::getEventDate));

        return eventList;
        */
        return new ArrayList<>();
    }

    @Override
    public void loadStudentEvents() {
        currentUserEvents = sortEventList();
        currentEventIndex = 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Event viewCurrentEvent() {
        if (currentUserEvents.isEmpty()) {
            return null;
        }
        return currentUserEvents.get(currentEventIndex);
    }

    @Override
    @Transactional(readOnly = true)
    public Event getNextEvent() {
        if (currentUserEvents.isEmpty()) {
            return null;
        }
        currentEventIndex = (currentEventIndex + 1) % currentUserEvents.size();
        return currentUserEvents.get(currentEventIndex);
    }

    @Override
    @Transactional(readOnly = true)
    public Event getPreviousEvent() {
        if (currentUserEvents.isEmpty()) {
            return null;
        }
        currentEventIndex = (currentEventIndex - 1 + currentUserEvents.size()) % currentUserEvents.size();
        return currentUserEvents.get(currentEventIndex);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> myEventListByDate(String userId) {
        // TODO: Implement when Event entity and EventRegistration are created
        /*
        // Get events the student is registered for, sorted by date
        return eventRegistrationRepository.findEventsByStudentIdOrderByDate(userId);
        */
        return new ArrayList<>();
    }

    @Override
    public Student saveStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (!studentRepository.existsById(student.getUserId())) {
            throw new IllegalArgumentException("Student with ID " + student.getUserId() + " does not exist");
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (!studentRepository.existsById(userId)) {
            throw new IllegalArgumentException("Student with ID " + userId + " does not exist");
        }
        studentRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return studentRepository.findByDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsBySemester(int semester) {
        if (semester < 1) {
            return new ArrayList<>();
        }
        return studentRepository.findBySemester(semester);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // TODO: Implement when Spring Security is set up
    private User getCurrentUser() {
        // This would typically get the current user from Spring Security context
        // return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null;
    }
}