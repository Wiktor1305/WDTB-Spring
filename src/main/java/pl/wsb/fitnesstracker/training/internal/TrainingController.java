package pl.wsb.fitnesstracker.training.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


/**
 * Kontroler REST obsługujący żądania HTTP związane z treningami.
 * Udostępnia endpointy do zarządzania sesjami treningowymi.
 */

@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {
    private final TrainingServiceImpl trainingService;

    @Autowired
    public TrainingController(TrainingServiceImpl trainingService) {
        this.trainingService = trainingService;
    }

    /**
     * Pobiera wszystkie treningi z systemu.
     * @return lista wszystkich treningów
     */
    @GetMapping
    public List<Training> getAll() {
        return trainingService.getAll();
    }

    /**
     * Pobiera wszystkie treningi konkretnego użytkownika.
     * @param userId identyfikator użytkownika
     * @return lista treningów użytkownika
     */
    @GetMapping("/{userId}")
    public List<Training> getByUser(@PathVariable Long userId) {
        return trainingService.getByUser(userId);
    }

    /**
     * Pobiera treningi zakończone po określonej dacie.
     * @param afterTime data w formacie yyyy-MM-dd
     * @return lista treningów po danej dacie
     */
    @GetMapping("/finished/{afterTime}")
    public List<Training> getAfterDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String afterTime) {
        LocalDate date = LocalDate.parse(afterTime);
        Date converted = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return trainingService.getAfterDate(converted);
    }

    /**
     * Pobiera treningi określonego typu aktywności.
     * @param activityType typ aktywności
     * @return lista treningów danego typu
     */
    @GetMapping("/activityType")
    public List<Training> getByActivity(@RequestParam ActivityType activityType) {
        return trainingService.getByActivity(activityType);
    }

    /**
     * Tworzy nowy trening.
     * @param dto dane nowego treningu
     * @return utworzony trening
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Training create(@RequestBody TrainingDto dto) {
        return trainingService.create(dto);
    }

    /**
     * Aktualizuje istniejący trening.
     * @param trainingId identyfikator treningu
     * @param dto zaktualizowane dane treningu
     * @return zaktualizowany trening
     */
    @PutMapping("/{trainingId}")
    public Training updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto dto) {
        return trainingService.update(trainingId, dto);
    }
}

