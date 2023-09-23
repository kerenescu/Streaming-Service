public class UserListCommand extends UserCommand {
    public UserListCommand(Integer idUser) {
        super(idUser);
    }
    @Override
    public void execute() {
        getUser().listStreams();
    }
}
