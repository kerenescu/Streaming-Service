public class StreamerDeleteStreamCommand extends StreamerCommand {
    private Integer idStream;

    public StreamerDeleteStreamCommand(Integer idStreamer, Integer idStream) {
        super(idStreamer);
        this.idStream = idStream;
    }

    @Override
    public void execute() {
        Application.getInstance().getStreams().removeStream(idStream);
    }
}
