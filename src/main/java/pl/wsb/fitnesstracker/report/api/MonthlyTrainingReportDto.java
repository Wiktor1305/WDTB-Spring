package pl.wsb.fitnesstracker.report.api;

/**
 * DTO reprezentujacy miesieczny raport treningowy uzytkownika
 * zawiera podstawowe dane uzytkownika oraz liczbe treningow wykonanych w okreslonym miesiacu i roku
 */

public class MonthlyTrainingReportDto {
    private Long userId;
    private String email;
    private int year;
    private int month;
    private int trainingCount;

    public MonthlyTrainingReportDto(Long userId, String email, int year, int month, int trainingCount) {
        this.userId = userId;
        this.email = email;
        this.year = year;
        this.month = month;
        this.trainingCount = trainingCount;
    }

    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getTrainingCount() { return trainingCount; }
}