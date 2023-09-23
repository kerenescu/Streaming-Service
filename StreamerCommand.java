public abstract class StreamerCommand implements Command {
    Integer idStreamer;

    public StreamerCommand(Integer idStreamer) {
        this.idStreamer = idStreamer;
    }

    protected Streamer getStreamer() {
        return Application.getInstance().getStreamers().getStreamer(idStreamer);
    }
}
