package pl.wsb.fitnesstracker.training.internal;

import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;

/**
 * Klasa mapująca odpowiedzialna za konwersję między obiektami Training a TrainingDto.
 * Zapewnia metody do transformacji danych treningu między warstwami aplikacji.
 */

public class TrainingMapper {

    /**
     * Konwertuje obiekt Training na TrainingDto.
     *
     * @param training obiekt Training zawierający dane treningu
     * @param user obiekt User reprezentujący użytkownika, którego dotyczy trening
     * @return obiekt TrainingDto zawierający zmapowane dane treningu
     */

    public static TrainingDto toDto(Training training, User user) {
        TrainingDto dto = new TrainingDto();
        dto.setId(training.getId());
        dto.setUserId(user.getId());
        dto.setStartTime(training.getStartTime());
        dto.setEndTime(training.getEndTime());
        dto.setActivityType(training.getActivityType());
        dto.setDistance(training.getDistance());
        dto.setAverageSpeed(training.getAverageSpeed());
        return dto;
    }

    /**
     * Konwertuje obiekt TrainingDto na Training.
     *
     * @param dto obiekt TrainingDto zawierający dane treningu
     * @param user obiekt User reprezentujący użytkownika, którego dotyczy trening
     * @return nowy obiekt Training utworzony na podstawie przekazanych danych
     */

    public static Training fromDto(TrainingDto dto, User user) {
        return new Training(
                user,
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getActivityType(),
                dto.getDistance(),
                dto.getAverageSpeed()
        );
    }
}
