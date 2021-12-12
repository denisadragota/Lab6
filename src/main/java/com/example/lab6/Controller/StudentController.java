package com.example.lab6.Controller;

import com.example.lab6.Exceptions.InputException;
import com.example.lab6.Exceptions.NullException;
import com.example.lab6.Model.Course;
import com.example.lab6.Model.Student;
import com.example.lab6.Repository.CourseRepository;
import com.example.lab6.Repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * JavaFX FXML Controller for Student Side
 *
 * @author Denisa Dragota
 * @version 12/12/2021
 */
public class StudentController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private Text credits;

    @FXML
    private Text titleCenter;

    @FXML
    private Text titleLeft1;

    @FXML
    private Text titleLeft2;

    @FXML
    private Long loggedInStudentId;

    Window window;

    @FXML
    Button closeButton;

    @FXML
    Button creditsButton;

    @FXML
    Button enrollButton;

    @FXML
    Text studentName;

    @FXML
    Button backToStartButton;

    @FXML
    Label enrollTitleLabel;

    @FXML
    Label responseLabel;

    @FXML
    Label loginErrorLabel;

    @FXML
    Button submitButton;

    @FXML
    ChoiceBox<String> chooseCourse;

    @FXML
    HBox hbox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //button action: go to Login View
        closeButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/StudentLoginView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("User Login");
            stage.show();
        });


        //button action: redirect to Credits View
        creditsButton.setOnAction(actionEvent -> {
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreditsView.fxml"));
            loader.setController(this);
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            borderPane.setCenter(parent);
        });

        //button action: redirects to Enroll View
        enrollButton.setOnAction(actionEvent -> {
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnrollView.fxml"));
            loader.setController(this);
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            borderPane.setCenter(parent);
        });

        //button action: enroll the logged in student to the chosen course
        submitButton.setOnAction(e ->
        {
            this.responseLabel.setVisible(false);

            int chosenIndex = chooseCourse.getSelectionModel().getSelectedIndex();

            if (chosenIndex != -1) {
                try {
                    Course course = this.coursesRepo.findAll().get(chosenIndex);
                    Student student = this.studentsRepo.findOne(loggedInStudentId);
                    this.register(course, student);
                    responseLabel.setText("Successfully enrolled!");
                    this.responseLabel.setVisible(true);

                } catch (SQLException | NullException | InputException ex) {
                    responseLabel.setText(ex.getMessage());
                    this.responseLabel.setVisible(true);

                }
            } else {
                responseLabel.setText("No course chosen!");
                this.responseLabel.setVisible(true);

            }

        });

        //give to the ChoiceBox the course list from the Repository
        try {
            this.chooseCourse.setItems(FXCollections.observableArrayList(this.coursesRepo.findAll()
                    .stream()
                    .map(Course::toShow)
                    .collect(Collectors.toList())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.chooseCourse.setTooltip(new Tooltip("Select the course"));

        //store the current logged in studentId
        if (loggedInStudentId != null) {
            try {
                this.setCredits();
                this.studentName.setText(this.studentsRepo.findOne(loggedInStudentId).getFirstName());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullException e) {
                e.printStackTrace();
            }
        }

    }

    private StudentRepository studentsRepo;
    private CourseRepository coursesRepo;


    public StudentController() throws SQLException {
        this.studentsRepo = new StudentRepository();
        this.coursesRepo = new CourseRepository();
        this.credits = new Text();
        this.closeButton = new Button();
        this.creditsButton = new Button();
        this.studentName = new Text();
        this.titleCenter = new Text();
        this.titleCenter.setText("Student Login");
        this.submitButton = new Button();
        this.enrollButton = new Button();
        this.enrollTitleLabel = new Label("Choose the course you want to enroll to");
        this.responseLabel = new Label();
        this.chooseCourse = new ChoiceBox<>();
        this.hbox = new HBox(chooseCourse);
        this.loginErrorLabel = new Label();


    }

    /**
     * Validates the input student credentials
     *
     * @param firstName from user introduced first name
     * @param lastName  from user introduced last name
     * @param password  from user introduced password
     * @return the logged in Student if the input credentials are correct, else null
     * @throws SQLException  if connection to database could not succeed
     * @throws NullException if input parameter id is NULL
     */
    private Student validStudent(String firstName, String lastName, String password) throws SQLException, NullException {
        Student student = this.studentsRepo.findOne(Long.valueOf(password));
        if (student != null && student.getFirstName().equals(firstName) && student.getLastName().equals(lastName)) {
            return student;
        }
        return null;
    }


    /**
     * redirects to the Student Account if the Log In was successful
     *
     * @throws SQLException  if connection to database could not succeed
     * @throws NullException if input parameter id is NULL
     * @throws IOException
     */
    @FXML
    public void login() throws SQLException, NullException, IOException {
        Student loggedInStudent = this.validStudent(firstName.getText(), lastName.getText(), password.getText());
        if (loggedInStudent != null) {
            loggedInStudentId = loggedInStudent.getStudentId();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentAccount.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Student Panel");
            stage.show();

        } else {
            firstName.requestFocus();
            lastName.requestFocus();
            password.requestFocus();
            this.loginErrorLabel.setText("Login Error! Incorrect name or password.");
        }
    }


    /**
     * sets the text of the credits Label to the current logged in Student
     *
     * @throws SQLException  if connection to database could not succeed
     * @throws NullException if input parameter id is NULL
     */
    @FXML
    public void setCredits() throws SQLException, NullException {
        credits.setText(((Integer) this.studentsRepo.findOne(loggedInStudentId).getTotalCredits()).toString());
    }


    /**
     * redirects to the Main Panel
     *
     * @throws IOException
     */
    @FXML
    private void backToStart() throws IOException {

        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/mainPanelView.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Start Page");
        stage.show();
    }


    /**
     * enroll the student to the course
     *
     * @param course  the course to enroll to
     * @param student the current logged in student
     * @return
     * @throws InputException if the student could not be enrolled to the course
     * @throws SQLException   if connection to database could not succeed
     * @throws NullException  if input parameter id is NULL
     */
    public boolean register(Course course, Student student) throws InputException, SQLException, NullException {

        //check if course exists in repo
        if (course == null || coursesRepo.findOne(course.getCourseId()) == null) {
            throw new InputException("Non-existing course id!");
        }

        //check if student exists in repo
        if (student == null || studentsRepo.findOne(student.getStudentId()) == null) {
            throw new InputException("Non-existing student id!");
        }
        List<Student> courseStudents = course.getStudentsEnrolled();
        //check if course has free places
        if (courseStudents.size() == course.getMaxEnrollment()) {
            throw new InputException("Course has no free places!");
        }

        //check if student is already enrolled
        boolean found = courseStudents
                .stream()
                .anyMatch(s -> s.compareTo(student));

        if (found)
            throw new InputException("Student already enrolled!");

        //if student has over 30 credits after enrolling to this course
        int studCredits = student.getTotalCredits() + course.getCredits();
        if (studCredits > 30)
            throw new InputException("Total number of credits exceeded!");

        //add student to course
        //update courses repo
        courseStudents.add(student);
        course.setStudentsEnrolled(courseStudents);
        coursesRepo.update(course);

        //update total credits of student
        student.setTotalCredits(studCredits);

        //update enrolled courses of Student
        List<Course> studCourses = student.getEnrolledCourses();
        studCourses.add(course);
        student.setEnrolledCourses(studCourses);

        //update students Repo
        studentsRepo.update(student);
        return true;
    }

}