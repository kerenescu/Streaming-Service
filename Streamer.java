import java.util.Map;

public class Streamer {
    private final Integer streamerType;
    private final Integer id;
    private final String name;

    public Streamer(Integer streamerType, Integer id, String name) {
        this.streamerType = streamerType;
        this.id = id;
        this.name = name;
    }
    public Streamer(Map<String, String> data) {
        streamerType = Integer.parseInt(data.get("streamerType"));
        id = Integer.parseInt(data.get("id"));
        name = data.get("name");
    }

    public Integer getStreamerType() {
        return streamerType;
    }
    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void listStreams() {
        StreamContainer streams = Application.getInstance().getStreams(); // obtin lista de streamuri de tip CONTAINER
        Application.displayStreams(streams.getStreamerStreams(id)); // in paranteza am ARRAY cu STREAMuri ale
    }                                                               // aceluiasi STREAMER
}
