package app.servicestation.datagenerator.model;

import java.util.List;

public interface Measurable {

    Identifier getIdentifier();
    String getType();
    <T> T getMeasure(Identifier identifiable);
    List<Identifier> getMeasureTypes();
}
