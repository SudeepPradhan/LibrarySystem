/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validation;

import interfaces.ValidateOutput;

/**
 *
 * @author Sudeep
 */
public class ValidateOutputImpl implements ValidateOutput {
   private final String error;
   private final boolean isValid;

   public ValidateOutputImpl(boolean isValid, String error) {
      this.error = error;
      this.isValid = isValid;
   }

    @Override
    public String getError() {
        return this.error;
    }

    @Override
    public boolean isValid() {
        return this.isValid;
    }
}
