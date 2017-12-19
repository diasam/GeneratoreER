package view;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Important events for
 */
public class ChronoEvents {
    private static ChronoEvents ourInstance = new ChronoEvents();
    private final ObservableList<DiagramElement> events = FXCollections.observableArrayList(new ArrayList<DiagramElement>());
    public static ChronoEvents getInstance() {
        return ourInstance;
    }

    private ChronoEvents() {
    }
    public Optional<DiagramElement> getLast() {
        return events.size() == 0 ? Optional.empty() : Optional.of(events.get(events.size()-1));
    }

    public ObservableList<DiagramElement> getEvents() {
        return events;
    }
}
