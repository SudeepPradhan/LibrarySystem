/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validation;

/**
 *
 * @author Sudeep
 */
public interface Validator<T>
{

    /**
     *
     * @param entity
     * @return
     */
    ValidateOutput isValid(T entity);
}
