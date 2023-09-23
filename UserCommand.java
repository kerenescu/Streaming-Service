public abstract class UserCommand implements Command {
    Integer idUser;

    public UserCommand(Integer idUser) {
        this.idUser = idUser;
    }

    protected User getUser() {
        return Application.getInstance().getUsers().getUser(idUser);
    }
}
