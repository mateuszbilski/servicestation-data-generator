package app.servicestation.datagenerator.logic;

import app.servicestation.datagenerator.model.Identifier;
import app.servicestation.datagenerator.model.Measurable;

import java.util.List;
import java.util.Map;

public interface Reportable {

    List<Measurable> getMeasurableList();
}
