import java.util.ArrayList;
import java.util.Map;

public class UserContainer extends CSVPopulable {
    private ArrayList<User> users = new ArrayList<>();

    public void addUser(User u) {
        users.add(u);
    }

    public boolean removeUser(Integer id) {
        return users.removeIf(user -> user.getID().equals(id));
    }

    // verific daca am un user cu ID -ul dat ca parametru si il returnez
    public User getUser(Integer id) {
        return users.stream().filter(user -> user.getID().equals(id)).findFirst().orElse(null);
    }
    @Override // protected abstract void addItem(Map<String, String> data); in CSVPopulable
    protected void addItem(Map<String, String> data) {
        User u = new User(data); // antet constructor in User --> public User(Map<String, String> data)
                                 // mapez antetul CSV - ului cu corespondenti pe linii
        users.add(u); // tip ArrayList private in StreamerContainer
    }
}
