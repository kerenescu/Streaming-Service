import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    // singleton
    private static Application instance;

    // application data
    private StreamerContainer streamers;
    private StreamContainer streams;
    private UserContainer users;
    private List<Command> commands;

    private Application() {
        streamers = new StreamerContainer();
        streams = new StreamContainer();
        users = new UserContainer();
        commands = new ArrayList<>();
    }
    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
    public static void destroyInstance() {
        instance = null;
    }
    public void populateData(String streamerPath, String streamPath, String userPath){
        streamers.populateFrom(streamerPath);
        streams.populateFrom(streamPath);
        users.populateFrom(userPath);
    }
    public StreamerContainer getStreamers() {
        return streamers;
    }
    public StreamContainer getStreams() {
        return streams;
    }
    public UserContainer getUsers() {
        return users;
    }

    // command invoker
    public void registerCommand(Command c) {
        commands.add(c);
    }
    public void executeCommands() {
        for (Command c : commands) {
            c.execute();
        }
    }

    // display helpers
    public static void displayStreams(Stream[] streams) { // le bag frumos cu paranteze
        String[] objects = Arrays.asList(streams).stream().map(Stream::formatJSON).toArray(String[]::new);
        System.out.println(String.format("[%s]", String.join(",", objects)));
    }

    // An array constructor reference (TypeName[]::new) <--
    public static void displayStreamsId(Integer[] streams) {
        StreamContainer streamContainer = getInstance().getStreams(); // efectiv intoarce lista de streamuri
        String[] objects = Arrays.asList(streams).stream().map(streamId -> streamContainer.getStream(streamId).formatJSON()).toArray(String[]::new);
        String res = String.format("[%s]", String.join(",", objects));
        System.out.println(res);
    }
}
