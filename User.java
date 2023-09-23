import java.util.*;
import java.util.stream.Collectors;

abstract class StreamObserver {
    protected void register(StreamContainerSubject subject) {
        subject.registerObserver(this);
    }

    abstract protected void notifyStreamAdded(StreamType type);
}

class User extends StreamObserver {

    private Integer ID;
    private String name;
    private List<Integer> streams;
    private Map<StreamType, List<Integer>> recommend;
    private Map<StreamType, List<Integer>> surprise;

    public User(String name) {
        this.name = name;
        this.streams = new ArrayList<>();
    }

    public User(Map<String, String> data) {
        this.ID = Integer.parseInt(data.get("id"));
        this.name = data.get("name");
        this.streams = new ArrayList<>();
        for (String split : data.get("streams").split(" ")) {
            streams.add(Integer.parseInt(split));
        };

        this.recommend = new EnumMap<>(StreamType.class);
        this.surprise = new EnumMap<>(StreamType.class);

        updateRecommend(StreamType.PIESA_MUZICALA);
        updateRecommend(StreamType.PODCAST);
        updateRecommend(StreamType.AUDIOBOOK);

        updateSurprise(StreamType.PIESA_MUZICALA);
        updateSurprise(StreamType.PODCAST);
        updateSurprise(StreamType.AUDIOBOOK);

        register(Application.getInstance().getStreams());
    }

    public String getName() {
        return name;
    }

//    public List<Integer> getHistory() {
//        return streams;
//    }
//
//    public List<Integer> getRecommend(StreamType type) {
//        return recommend.get(type);
//    }
//
//    public List<Integer> getSurprise(StreamType type) {
//        return surprise.get(type);
//    }

    public Integer getID() {
        return ID;
    }

    // An array constructor reference (TypeName[]::new) <--
    public void listStreams() {
        Application.displayStreamsId(streams.toArray(Integer[]::new));
    }

    public void listen(Integer idStream) {
        Stream s = Application.getInstance().getStreams().getStream(idStream);
        if (s != null) {
            streams.add(idStream);
            s.listen();
        }
    }

    public void displayRecommend(StreamType type) {
        Application.displayStreamsId(recommend.get(type).toArray(Integer[]::new));
    }

    public void displaySurprise(StreamType type) {
        Application.displayStreamsId(surprise.get(type).toArray(Integer[]::new));
    }

    @Override
    protected void notifyStreamAdded(StreamType type) {
        updateRecommend(type);
        updateSurprise(type);
    }

    private void updateRecommend(StreamType type) {
        List<Integer> listenedStreamers = listenedStreamers(type); // LISTA de IDuri de STREAMERI ascultati
        StreamContainer streamContainer = Application.getInstance().getStreams(); // obtin CONTAINERUL de STREAMURI
        recommend.put(type, streamContainer.getTopStreams(listenedStreamers, streams, type));
    }

    private void updateSurprise(StreamType type) {
        List<Integer> listenedStreamers = listenedStreamers(type); // LISTA de IDuri de STREAMERI ascultati
        StreamContainer streamContainer = Application.getInstance().getStreams(); // obtin CONTAINERUL de STREAMURI
        surprise.put(type, streamContainer.getSurpriseStreams(listenedStreamers, type));
    }

    protected List<Integer> listenedStreamers(StreamType type) {
        Application app = Application.getInstance(); // noua referinta la instanta singleton
        StreamContainer streamContainer = app.getStreams(); // obrin CONTAINER de STREAMURI
        return streams.stream().map(streamId -> streamContainer.getStream(streamId).getStreamerID()).distinct().collect(Collectors.toList());
        // return streams.stream().map(streamId -> streamContainer.getStream(streamId).getStreamerID()).collect(Collectors.toList()); FIXME ??
        // imi fac ARRAY de INTEGER cu LAMBDA
        // mapez elem listei STREAMS din USER
        // getStreamer e in STREAM si returneaza ID-ul INTEGER al STREAMERului STREAMului
        // soretz ca fiind distincte (pot si fara pe teste hmmmm) FIXME: pai merge, dar cauti de mai multe ori acelasi
        // bag in LISTA de INTEGER
    }
}
