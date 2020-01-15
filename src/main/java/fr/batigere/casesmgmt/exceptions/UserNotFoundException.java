package fr.batigere.casesmgmt.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Exception cause){
        super(cause);
    }
}
