package com.example.demo.exception;

import java.time.Instant;

public class Error {
    private final int status;
    private final String message;
    private final String path;
    private final Instant timeStamp;

    public Error(int status,String message,String path){
        this.status=status;
        this.message=message;
        this.path=path;
        this.timeStamp=Instant.now();
    }

    public int getStatus() {
        return status;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
