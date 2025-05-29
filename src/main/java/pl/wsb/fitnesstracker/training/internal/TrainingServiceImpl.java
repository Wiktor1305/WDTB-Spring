
package pl.wsb.fitnesstracker.training.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementacja serwisu do zarządzania operacjami związanymi z treningami.
 * Zapewnia operacje CRUD oraz różne funkcjonalności wyszukiwania dla rekordów treningów.
 */

@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository repository;
    private final UserProvider userProvider;

    @Autowired
    public TrainingServiceImpl(TrainingRepository repository, UserProvider userProvider) {
        this.repository = repository;
        this.userProvider = userProvider;
    }

    @Override
    public Optional<Training> getTraining(Long trainingId) {
        return repository.findById(trainingId);
    }

    /**
     * Pobiera trening po jego ID lub rzuca wyjątek, jeśli nie zostanie znaleziony.
     *
     * @param id identyfikator treningu do pobrania
     * @return encja treningu
     * @throws TrainingNotFoundException jeśli trening nie zostanie znaleziony
     */

    public Training getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new TrainingNotFoundException(id));
    }

    /**
     * Pobiera wszystkie rekordy treningów z bazy danych.
     *
     * @return lista wszystkich treningów
     */

    public List<Training> getAll() {
        return repository.findAll();
    }

    /**
     * Pobiera wszystkie treningi dla określonego użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @return lista treningów dla określonego użytkownika
     * @throws IllegalArgumentException jeśli użytkownik nie zostanie znaleziony
     */

    public List<Training> getByUser(Long userId) {
        User user = userProvider.getUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return repository.findAllByUser(user);
    }

    /**
     * Pobiera wszystkie treningi dla określonego typu aktywności.
     *
     * @param type typ aktywności do filtrowania
     * @return lista treningów pasujących do typu aktywności
     */

    public List<Training> getByActivity(ActivityType type) {
        return repository.findAllByActivityType(type);
    }

    /**
     * Pobiera wszystkie treningi zakończone po określonej dacie.
     *
     * @param date data do filtrowania
     * @return lista treningów zakończonych po określonej dacie
     */

    public List<Training> getAfterDate(Date date) {
        return repository.findAllByEndTimeAfter(date);
    }

    /**
     * Tworzy nowy rekord treningu.
     *
     * @param dto obiekt DTO zawierający szczegóły treningu
     * @return utworzona encja treningu
     * @throws IllegalArgumentException jeśli użytkownik określony w DTO nie zostanie znaleziony
     */

    public Training create(TrainingDto dto) {
        User user = userProvider.getUser(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return repository.save(TrainingMapper.fromDto(dto, user));
    }

    /**
     * Aktualizuje dystans określonego treningu.
     *
     * @param id identyfikator treningu do aktualizacji
     * @param distance nowa wartość dystansu
     * @return zaktualizowana encja treningu
     * @throws TrainingNotFoundException jeśli trening nie zostanie znaleziony
     */

    public Training updateDistance(Long id, double distance) {
        Training training = getOrThrow(id);
        training.setDistance(distance);
        return repository.save(training);
    }

    /**
     * Aktualizuje dane treningu.
     *
     * @param id identyfikator treningu do aktualizacji
     * @param dto obiekt DTO zawierający zaktualizowane wartości
     * @return zaktualizowana encja treningu
     * @throws TrainingNotFoundException jeśli trening nie zostanie znaleziony
     */
    public Training update(Long id, TrainingDto dto) {
        Training training = getOrThrow(id);

        if (dto.getStartTime() != null) {
            training.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            training.setEndTime(dto.getEndTime());
        }
        if (dto.getActivityType() != null) {
            training.setActivityType(dto.getActivityType());
        }
        training.setDistance(dto.getDistance());
        training.setAverageSpeed(dto.getAverageSpeed());

        return repository.save(training);
    }

}