package pl.wsb.fitnesstracker.report.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.report.api.ReportService;
import java.time.LocalDate;

/**
 * generowanie i wysylanie miesiecznych raportow
 */

@Component
@RequiredArgsConstructor
public class MonthlyReportScheduler {

    private final ReportService reportService;

    @Scheduled(cron = "0 0 1 1 * ?")
    public void generateReports() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        reportService.generateAndSendMonthlyReports(lastMonth.getYear(), lastMonth.getMonthValue());
    }
    //uruchamianie o 1:00 w nocy
}