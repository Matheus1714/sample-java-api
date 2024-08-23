package views;

import models.UserModel;

public class UserView {
    public String render(UserModel user) {
        return String.format(
            "{ \"name\": \"%s\", \"email\": \"%s\" }",
            user.getName(),
            user.getEmail()
        );
    }
}
