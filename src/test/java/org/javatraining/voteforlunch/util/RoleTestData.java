package org.javatraining.voteforlunch.util;

import org.javatraining.voteforlunch.model.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleTestData {
    public static final int ROLE_ID_SEQ = 1;
    public static final int ROLE_1_ID = ROLE_ID_SEQ;
    public static final int ROLE_2_ID = ROLE_ID_SEQ + 1;
    public static final int ROLE_3_ID = ROLE_ID_SEQ + 2;

    public final static Role ROLE_1 = new Role(ROLE_1_ID,"USER");
    public final static Role ROLE_2 = new Role(ROLE_2_ID,"ADMIN");
    public final static Role ROLE_3 = new Role(ROLE_3_ID,"SUPERADMIN");

    public static List<Role> ROLES = new ArrayList<>() {{
       add(ROLE_1);
       add(ROLE_2);
    }};


}
