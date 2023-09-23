public class UserRecommendCommand extends UserCommand{
    private StreamType type;

    public UserRecommendCommand(Integer idUser, StreamType type) {
        super(idUser);
        this.type = type;
    }

    @Override
    public void execute() {
        getUser().displayRecommend(type);
    }
}
