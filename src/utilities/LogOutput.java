/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import logging.Logger;
import logging.LoggerFactory;
import logging.LoggerFactoryImpl;
import logging.LoggerType;
import logging.TraceLevel;

public class LogOutput {
    
    private static logging.Logger log;     
    
    private static logging.Logger constructLog()
    {
        LoggerFactory factory = LoggerFactoryImpl.getFactory();  
        log = factory.createLogger(LoggerType.Console);
        log.setLevel(TraceLevel.Info);
        
        return log;
    }

    public static Logger getLogger()
    {
        if(log == null)
            return constructLog();
        else
            return log;
    }
}

