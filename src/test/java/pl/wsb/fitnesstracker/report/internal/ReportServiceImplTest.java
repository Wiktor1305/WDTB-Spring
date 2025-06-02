package pl.wsb.fitnesstracker.report.internal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wsb.fitnesstracker.report.api.MonthlyTrainingReportDto;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingService;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserService;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * tescik jednostkowy sprawdzajacy poprawnosc generowania i wysylania raportow
 */

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private TrainingService trainingService;

    @Mock
    private UserService userService;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private ReportServiceImpl reportService;

    static class TestUser extends User {
        private final Long id;
        private final String email;

        public TestUser(Long id, String email) {
            super();
            this.id = id;
            this.email = email;
        }

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public String getEmail() {
            return email;
        }
    }

    static class TestTraining extends Training {
        private final Date startTime;

        public TestTraining(Date startTime) {
            super();
            this.startTime = startTime;
        }

        @Override
        public Date getStartTime() {
            return startTime;
        }
    }

    @Test
    void testGenerateAndSendMonthlyReports() {
        TestUser user = new TestUser(1L, "test@example.com");
        //utworz test usera

        Date trainingDate = new Date(1750032000000L); // 2025-06-15T00:00:00Z
        TestTraining training = new TestTraining(trainingDate);
        //stworz testowy trening z czerwca 2025

        when(userService.findAllUsers()).thenReturn(List.of(user));
        when(trainingService.findTrainingsByUser(any())).thenReturn(List.of(training));
        //mockuj zwracane listy

        reportService.generateAndSendMonthlyReports(2025, 6);

        ArgumentCaptor<MonthlyTrainingReportDto> captor = ArgumentCaptor.forClass(MonthlyTrainingReportDto.class);
        verify(emailSender, times(1)).sendReport(captor.capture());
        //sprawdz, czy mail był wyslany z poprawnym raportem

        MonthlyTrainingReportDto sentReport = captor.getValue();
        assertEquals(1L, sentReport.getUserId());
        assertEquals("test@example.com", sentReport.getEmail());
        assertEquals(2025, sentReport.getYear());
        assertEquals(6, sentReport.getMonth());
        assertEquals(1, sentReport.getTrainingCount());
    }
}