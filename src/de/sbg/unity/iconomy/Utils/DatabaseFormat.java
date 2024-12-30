package de.sbg.unity.iconomy.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @hidden
 * @author pbronke
 */
public class DatabaseFormat {

    public byte[] toBlob(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(bos);
        
        oos.writeObject(obj);
        return bos.toByteArray();
    }
    
    public Object toObject(byte[] b) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(b));
        return is.readObject();
    }

}
