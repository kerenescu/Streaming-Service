import java.time.Instant;

public class StreamerAddStreamCommand extends StreamerCommand {
    private Integer id;
    private StreamType type;
    private Integer streamGenre;
    private Long length;
    private String name;

    public StreamerAddStreamCommand(Integer idStreamer, Integer id, StreamType type, Integer streamGenre, Long length, String name) {
        super(idStreamer);
        this.id = id;
        this.type = type;
        this.streamGenre = streamGenre;
        this.length = length;
        this.name = name;
    }

    @Override
    public void execute() {
        try {
            Stream s = StreamFactory.getStream(type, id, streamGenre, idStreamer, 0L, length, Instant.now().getEpochSecond(), name);
            Application.getInstance().getStreams().addStream(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
