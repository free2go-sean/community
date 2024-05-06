package com.free2go.community.common.exception;

public class EntityNotFoundException extends BusinessException{

    public EntityNotFoundException() {
        super("Entity Not Found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
