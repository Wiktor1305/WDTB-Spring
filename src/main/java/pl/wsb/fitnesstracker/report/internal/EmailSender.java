package pl.wsb.fitnesstracker.report.internal;

import pl.wsb.fitnesstracker.report.api.MonthlyTrainingReportDto;

/**
 * interfejs odpowiedizalny za wysylanie raportow mailowych do userow
 */

interface EmailSender {
    void sendReport(MonthlyTrainingReportDto report);
}