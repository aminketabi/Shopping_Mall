package Users;

public class Administrator extends User {
    private String id;
    public Administrator(String username, String password, String email, String id) {
        super(username, password, email, "Administrator");
        this.id = id;
    }


}
