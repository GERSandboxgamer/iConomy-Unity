package de.sbg.unity.iconomy.Utils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author pott
 */
public class BankStatement implements Serializable{
    
    private final LocalDateTime time;
    private final String statement; 
    private final String from;
    
    public BankStatement(LocalDateTime time, String from, String statement) {
        this.time = time;
        this.statement = statement;
        this.from = from;
    }

    public String getStatement() {
        return statement;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getFrom() {
        
        return from;
    }
    
}
