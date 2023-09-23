import java.util.ArrayList;
import java.util.Map;

public class StreamerContainer extends CSVPopulable {
    private ArrayList<Streamer> streamers = new ArrayList<>();

//    public void addStreamer(Streamer s) {
//        streamers.add(s);
//    }

//    //public boolean removeStreamer(Integer id) {
//        return streamers.removeIf(stream -> stream.getID().equals(id));
//    }

    // GeekForGeeks <--> teorie ca sa inteleg cum folosesc **stream**
    //   A stream is not a data structure instead it takes input from the Collections, Arrays or I/O channels.
    //   Streams don’t change the original data structure, they only provide the result as per the pipelined methods.
    //   Each intermediate operation is lazily executed and returns a stream as a result, hence various
    //   intermediate operations can be pipelined. Terminal operations mark the end of the stream and return the result.
    //// filter: The filter method is used to select elements as per the Predicate passed as argument.
    ////// --> List result = names.stream().filter(s->s.startsWith("S")).collect(Collectors.toList());
    //// findAny() + orElse()

    // verific daca am un streamer cu ID - ul dat ca parametru
    public Streamer getStreamer(Integer id) { // --> stream + filter + findAny & orElse
        return streamers.stream().filter(streamer -> streamer.getID().equals(id)).findAny().orElse(null);
    }
    @Override // protected abstract void addItem(Map<String, String> data); in CSVPopulable
    protected void addItem(Map<String, String> data) { // --> adaug un streamer in lista de streameri
        Streamer s = new Streamer(data); // antet constructor in Streamer --> public Streamer(Map<String, String> data)
        streamers.add(s); // tip ArrayList private in StreamerContainer
    }
}
