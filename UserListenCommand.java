public class UserListenCommand extends UserCommand {
    private Integer idStream;

    public UserListenCommand(Integer idUser, Integer idStream) {
        super(idUser);
        this.idStream = idStream;
    }

    @Override
    public void execute() {
        getUser().listen(idStream);
    }
}
