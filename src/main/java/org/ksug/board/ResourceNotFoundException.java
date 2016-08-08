package org.ksug.board;


import org.omg.CORBA.Object;
import org.springframework.http.HttpStatus;

/**
 * Project: SpringBootHandsonlan
 * FileName: ResourceNotFoundException
 * Date: 2016-08-08
 * Time: 오전 10:39
 * Author: Hadeslee
 * Note:
 * To change this template use File | Settings | File Templates.
 */
public abstract class ResourceNotFoundException extends RuntimeException {
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public String getError() {
        return "error." + getClass().getSimpleName();
    }

    public Object[] getArgs() {
        return null;
    }
}