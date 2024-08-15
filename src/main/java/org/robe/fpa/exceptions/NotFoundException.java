package org.robe.fpa.exceptions;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 6388457131393001158L;

    public NotFoundException(String message) {
        super(message);
    }
}
