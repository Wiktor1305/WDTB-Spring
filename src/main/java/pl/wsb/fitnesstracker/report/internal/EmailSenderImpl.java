package pl.wsb.fitnesstracker.report.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.report.api.MonthlyTrainingReportDto;

/**
 * implementacja interfejsu wykorzystujaca usluge email do wyyslki raportow
 */

@Component
@Slf4j
public class EmailSenderImpl implements EmailSender {
    @Override
    public void sendReport(MonthlyTrainingReportDto report) {
        log.info("wysylka raportu na adres email {}: treningow w miesiacu {}-{}: {}",
                report.getEmail(), report.getYear(), report.getMonth(), report.getTrainingCount());
    }
}