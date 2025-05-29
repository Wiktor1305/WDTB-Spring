package pl.wsb.fitnesstracker.training.api;

import lombok.Data;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;

/**
 * Obiekt transferu danych (DTO) dla encji Training.
 * Używany do przekazywania danych treningu między warstwami bez eksponowania szczegółów encji.
 */


@Data
public class TrainingDto {
    private Long id;
    private Long userId;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;
}
