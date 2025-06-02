package pl.wsb.fitnesstracker.training.api;

import java.util.List;

/**
 * serwis do zarzadzania treningami userow: pobieranie, dodawanie, szukanie treningow
 */

public interface TrainingService extends TrainingProvider {
    List<Training> findTrainingsByUser(Long userId);
}