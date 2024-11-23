package com.selflearn.tree.datamapper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserValidate {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
