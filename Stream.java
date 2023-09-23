import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

enum StreamType {
    PIESA_MUZICALA , PODCAST, AUDIOBOOK;
    public static StreamType fromInteger (Integer x){
        switch(x) {
            case 1:
                return PIESA_MUZICALA;
            case 2:
                return PODCAST;
            case 3:
                return AUDIOBOOK;
        }
        return null;
    }
    public static StreamType fromString (String x){
        switch(x) {
            case "SONG":
                return PIESA_MUZICALA;
            case "PODCAST":
                return PODCAST;
            case "AUDIOBOOK":
                return AUDIOBOOK;
        }
        return null;
    }
}
abstract class Stream {
    protected Integer id, streamGenre, streamerId;
    protected Long noOfStreams, length, dateAdded;
    protected String name;
    public Stream(Integer id, Integer streamGenre, Integer streamerId, Long noOfStreams, Long length, Long dateAdded, String name) {
        this.id = id;
        this.streamGenre = streamGenre;
        this.streamerId = streamerId;
        this.noOfStreams = noOfStreams;
        this.length = length;
        this.dateAdded = dateAdded;
        this.name = name;
    }
    public Stream(Map<String, String> data) {
        this.id = Integer.parseInt(data.get("id"));
        this.streamGenre = Integer.parseInt(data.get("streamGenre"));
        this.streamerId = Integer.parseInt(data.get("streamerId"));
        this.noOfStreams = Long.parseLong(data.get("noOfStreams"));
        this.length = Long.parseLong(data.get("length"));
        this.dateAdded = Long.parseLong(data.get("dateAdded"));
        this.name = data.get("name");
    }

    public abstract boolean isType(StreamType type);
    public Integer getID() {
        return id;
    }
    public abstract String getGenreName();
    public Integer getStreamerID() {
        return streamerId;
    }
    public Long getNoOfStreams() {
        return noOfStreams;
    }
    public Long getLength() {
        return length;
    }
    public String parseLength() {
        Duration d = Duration.ofSeconds(length);

        if (d.toHours() > 0) {
            return String.format("%02d:%02d:%02d", d.toHours(), d.toMinutesPart(), d.toSecondsPart());
        }
        return String.format("%02d:%02d", d.toMinutesPart(), d.toSecondsPart());
    }

    public Long getDateAdded() {
        return dateAdded;
    }

    public String parseDateAdded() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        return fmt.format(new Date(dateAdded * 1000));
    }

    public String getName() {
        return name;
    }

    public String formatJSON() {  // functie de conversie in format JSON =/
        Streamer streamer = Application.getInstance().getStreamers().getStreamer(streamerId);
        String [][] data = {
                {"id", id.toString()},
                {"name", name},
                {"streamerName", streamer.getName()},
                {"noOfListenings", noOfStreams.toString()},
                {"length", parseLength()},
                {"dateAdded", parseDateAdded()}
        };
        String[] fileds_array = Arrays.asList(data).stream().map(line -> String.format("\"%s\":\"%s\"", line[0], line[1])).toArray(String[]::new);
        return String.format("{%s}", String.join(",", fileds_array));
    }

    public void listen() {
        noOfStreams++;
    }
}

class PiesaMuzicala extends Stream{
    public PiesaMuzicala(Integer id, Integer streamGenre, Integer streamerId, Long noOfStreams, Long length, Long dateAdded, String name) throws Exception {
        super(id, streamGenre, streamerId, noOfStreams, length, dateAdded, name);

        if(streamGenre < 1 || streamGenre > 5){
            throw new Exception("Input invalid");
        }
    }

    public PiesaMuzicala(Map<String, String> data) throws Exception {
        super(data);
        if(streamGenre < 1 || streamGenre > 5){
            throw new Exception("Input invalid");
        }
    }

    @Override
    public String getGenreName() {
        switch (streamGenre){
            case 1:
                return "pop";
            case 2:
                return "latin";
            case 3:
                return "house";
            case 4:
                return "dance";
            case 5:
                return "trap";
        }
        return null;
    }

    @Override
    public boolean isType(StreamType type) {
        return type == StreamType.PIESA_MUZICALA;
    }
}

class Podcast extends Stream{
    public Podcast(Integer id, Integer streamGenre, Integer streamerId, Long noOfStreams, Long length, Long dateAdded, String name) throws Exception {
        super(id, streamGenre, streamerId, noOfStreams, length, dateAdded, name);
        if(streamGenre < 1 || streamGenre > 3){
            throw new Exception("Input invalid");
        }
    }

    public Podcast(Map<String, String> data) throws Exception {
        super(data);
        if(streamGenre < 1 || streamGenre > 3){
            throw new Exception("Input invalid");
        }
    }

    @Override
    public String getGenreName() {
        switch (streamGenre){
            case 1:
                return "documentary";
            case 2:
                return "celebrities";
            case 3:
                return "tech";
        }
        return null;
    }

    @Override
    public boolean isType(StreamType type) {
        return type == StreamType.PODCAST;
    }

}

class Audiobook extends Stream{
    public Audiobook(Integer id, Integer streamGenre, Integer streamerId, Long noOfStreams, Long length, Long dateAdded, String name) throws Exception {
        super(id, streamGenre, streamerId, noOfStreams, length, dateAdded, name);
        if(streamGenre < 1 || streamGenre > 3){
            throw new Exception("Input invalid");
        }
    }

    public Audiobook(Map<String, String> data) throws Exception {
        super(data);
        if(streamGenre < 1 || streamGenre > 3){
            throw new Exception("Input invalid");
        }
    }

    @Override
    public String getGenreName() {
        switch (streamGenre){
            case 1:
                return "fiction";
            case 2:
                return "personal development";
            case 3:
                return "children";
        }
        return null;
    }

    @Override
    public boolean isType(StreamType type) {
        return type == StreamType.AUDIOBOOK;
    }
}

class StreamFactory { // gen Factory
    public static Stream getStream(StreamType type, Integer id, Integer streamGenre, Integer streamerId, Long noOfStreams, Long length, Long dateAdded, String name) throws Exception {
        switch(type){
            case PIESA_MUZICALA:
                return new PiesaMuzicala(id, streamGenre, streamerId, noOfStreams, length, dateAdded, name);
            case PODCAST:
                return new Podcast(id, streamGenre, streamerId, noOfStreams, length, dateAdded, name);
            case AUDIOBOOK:
                return new Audiobook(id, streamGenre, streamerId, noOfStreams, length, dateAdded, name);
        }
        return null;
    }

    public static Stream getStream(StreamType type, Map<String, String> data) throws Exception {
        switch(type){
            case PIESA_MUZICALA:
                return new PiesaMuzicala(data);
            case PODCAST:
                return new Podcast(data);
            case AUDIOBOOK:
                return new Audiobook(data);
        }
        return null;
    }
}
