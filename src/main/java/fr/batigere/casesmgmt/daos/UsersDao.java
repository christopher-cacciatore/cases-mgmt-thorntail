package fr.batigere.casesmgmt.daos;

import fr.batigere.casesmgmt.entities.UserEntity;

public interface UsersDao {

    public UserEntity findByUsername(String username);
}
