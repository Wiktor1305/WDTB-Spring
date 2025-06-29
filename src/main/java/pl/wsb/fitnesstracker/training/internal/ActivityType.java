package pl.wsb.fitnesstracker.training.internal;

/**
 * Typ wyliczeniowy reprezentujący rodzaje aktywności fizycznej dostępne w systemie.
 */

public enum ActivityType {

    RUNNING("Running"),
    CYCLING("Cycling"),
    WALKING("Walking"),
    SWIMMING("Swimming"),
    TENNIS("Tenis");

    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
