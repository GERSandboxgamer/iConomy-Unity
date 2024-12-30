package de.sbg.unity.iconomy.Utils;

public class AtmUtils {
    
    public AtmType getAtmType(int id) {
            switch (id) {
                case 1 -> {
                    return AtmType.Standart;
                }
                case 2 -> {
                    return AtmType.In;
                }
                case 3 -> {
                    return AtmType.Out;
                }
                default -> {
                    return AtmType.Standart;
                }
            }
        }

    public enum AtmType {

        Standart(1),
        In(2),
        Out(3);
        //Information(4); //TODO Version 2.1

        private final int id;

        AtmType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
        
    }

}
