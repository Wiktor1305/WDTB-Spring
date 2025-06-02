package pl.wsb.fitnesstracker.report.api;

/**
 * serwis odpowiedzialny za generowanie i zarzadzanie raportami treningowymi uzytkownikow
 */

public interface ReportService {
    void generateAndSendMonthlyReports(int year, int month);
}
