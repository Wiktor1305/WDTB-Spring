package pl.wsb.fitnesstracker.training.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;


interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByUser(User user);
    List<Training> findAllByEndTimeAfter(Date date);
    List<Training> findAllByActivityType(ActivityType type);

}
