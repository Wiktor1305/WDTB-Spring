package pl.wsb.fitnesstracker.training.api;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Date;

/**
 * Encja reprezentująca trening w systemie śledzenia aktywności fizycznej.
 * Zawiera informacje o użytkowniku, czasie, typie aktywności i metrykach wydajności.
 */



@Entity
@Table(name = "trainings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    @Column(name = "distance")
    private double distance;

    @Column(name = "average_speed")
    private double averageSpeed;

    public Training(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }


    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Ustawia czas rozpoczęcia treningu.
     * @param startTime data i czas rozpoczęcia
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Ustawia czas zakończenia treningu.
     * @param endTime data i czas zakończenia
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Ustawia typ aktywności treningu.
     * @param activityType typ aktywności
     */
    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    /**
     * Ustawia średnią prędkość treningu.
     * @param averageSpeed średnia prędkość
     */
    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

}