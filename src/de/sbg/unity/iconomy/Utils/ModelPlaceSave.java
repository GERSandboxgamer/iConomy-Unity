package de.sbg.unity.iconomy.Utils;

import de.chaoswg.model3d.Model3DObject;
import de.chaoswg.model3d.Model3DPlace;
import de.sbg.unity.iconomy.Utils.AtmUtils.AtmType;

public class ModelPlaceSave {

    private final Model3DPlace place;
    private final Model3DObject model;
    private final AtmType atmType;
    private final int ID;

    public ModelPlaceSave(Model3DPlace place, Model3DObject model, int id, AtmType type) {
        this.place = place;
        this.model = model;
        this.ID = model.getID();
        this.atmType = type;
    }

    public AtmType getAtmType() {
        return atmType;
    }
    
    public Model3DObject getModel() {
        return model;
    }

    public Model3DPlace getPlace() {
        return place;
    }

    public int getID() {
        return ID;
    }

    

}