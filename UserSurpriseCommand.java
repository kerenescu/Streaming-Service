public class UserSurpriseCommand extends UserCommand {
    private StreamType type;

    public UserSurpriseCommand(Integer idUser, StreamType type) {
        super(idUser);
        this.type = type;
    }

    @Override
    public void execute() {
        getUser().displaySurprise(type);
    }
}
