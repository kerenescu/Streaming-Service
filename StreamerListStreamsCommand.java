public class StreamerListStreamsCommand extends StreamerCommand {

    public StreamerListStreamsCommand(Integer idStreamer) {
        super(idStreamer);
    }

    @Override
    public void execute() {
        getStreamer().listStreams();
    }
}
