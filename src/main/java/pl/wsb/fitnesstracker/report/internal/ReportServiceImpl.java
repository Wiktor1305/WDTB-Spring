package pl.wsb.fitnesstracker.report.internal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.report.api.MonthlyTrainingReportDto;
import pl.wsb.fitnesstracker.report.api.ReportService;
import pl.wsb.fitnesstracker.training.api.TrainingService;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *implementacja RaportService
 * generowanie, agregowanie i wysylka raportow
 */

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final TrainingService trainingService;
    private final UserService userService;
    private final EmailSender emailSender;

    @Override
    public void generateAndSendMonthlyReports(int year, int month) {
        List<User> allUsers = userService.findAllUsers();
        for (User user : allUsers) {
            int count = (int) trainingService.findTrainingsByUser(user.getId())
                    .stream()
                    .filter(t -> {
                        Date start = t.getStartTime();
                        if (start == null) return false;
                        LocalDateTime ldt = start.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();
                        return ldt.getYear() == year && ldt.getMonthValue() == month;
                    })
                    .count();

            MonthlyTrainingReportDto report = new MonthlyTrainingReportDto(
                    user.getId(),
                    user.getEmail(),
                    year,
                    month,
                    count
            );
            emailSender.sendReport(report);
        }
    }
}