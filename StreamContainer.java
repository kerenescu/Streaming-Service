import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;

interface StreamContainerSubject {
    List<StreamObserver> observers = new ArrayList<>();

    default void registerObserver(StreamObserver observer) {
        observers.add(observer);
    }

    default void unregisterObserver(StreamObserver observer) {
        observers.remove(observer);
    }

    default void notifyStreamUpdate(StreamType type) {
        for (StreamObserver observer : observers) {
            observer.notifyStreamAdded(type);
        }
    }
}

public class StreamContainer extends CSVPopulable implements StreamContainerSubject {
    private ArrayList<Stream> streams = new ArrayList<>();

    public void addStream(Stream s) {
        streams.add(s);
    }

    public boolean removeStream(Integer id) {
        return streams.removeIf(stream -> stream.getID().equals(id));
    }

    // obtin Stream dupa ID
    public Stream getStream(Integer id) { // obtin Stream dupa ID
        return streams.stream().filter(stream -> stream.getID().equals(id)).findAny().orElse(null);
    }

    //bag STREAMURI in LISTA
    @Override
    protected void addItem(Map<String, String> data) {
        StreamType type = StreamType.fromInteger(Integer.parseInt(data.get("streamType")));

        if (type == null) {
            return;
        }

        try {
            Stream s = StreamFactory.getStream(type, data);
            streams.add(s);
            notifyStreamUpdate(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stream[] getStreamerStreams(Integer idStreamer) { // returnez ARRAY de STREAMuri ce apartin aceluiasti STREAMER
        return streams.stream()
                .filter(stream -> stream.getStreamerID().equals(idStreamer))
                // .sorted((s1, s2) -> s2.getID().compareTo(s1.getID()))
                .toArray(Stream[]::new);
    }

    //OBTIN STREMURILE DE TOP DPDV AL ASCULTARILOR
    public List<Integer> getTopStreams(List<Integer> streamers, List<Integer> listenedStreams, StreamType type) {
        return streams.stream()
                .filter(stream -> streamers.contains(stream.getStreamerID()) && !listenedStreams.contains(stream.getID()) && stream.isType(type))
                .sorted((s1, s2) -> s2.getNoOfStreams().compareTo(s1.getNoOfStreams())) // SORTEZ DUPA ASCULTARI
                .limit(5) // VREAU 5 RECOMANDARI
                .map(Stream::getID) // transform din obiect de tip Stream in Integer reprezentand id-ul streamului
                .collect(Collectors.toList()); // bag in Lista de STREAMURI pe care o returnez
    }

    // OBTIN SURPRIZA
    public List<Integer> getSurpriseStreams(List<Integer> streamers, StreamType type) {
        return streams.stream()
                .filter(stream -> !streamers.contains(stream.getStreamerID()) && stream.isType(type)) // verific ca STREAMERUL e NEASCULTAT
                .sorted((s1, s2) -> {
                    // Sortez dupa DATA le vreau pe cele mai RECENTE!!
                    if (DateUtils.isSameDay(new Date(s1.getDateAdded()), new Date(s2.getDateAdded()))) {
                        // Dacă au fost adaugate in acceasi zi, atunci veți alege stream-ul cu cele mai multe ascultari.
                        return s2.getNoOfStreams().compareTo(s1.getNoOfStreams());
                    }
                    return s2.getDateAdded().compareTo(s1.getDateAdded());
                })
                .limit(3) // VREAU 3 SURPRIZE
                .map(Stream::getID) // transform din obiect de tip Stream in Integer reprezentand id-ul streamului
                .collect(Collectors.toList()); // bag in ARRAY de STREAMURI pe care ulterior la displayuiesc
    }
}
