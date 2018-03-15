package app.servicestation.datagenerator.endpoint;

import app.servicestation.datagenerator.logic.Reportable;
import app.servicestation.datagenerator.logic.ServiceStationManager;

public interface Endpoint {

    void init();
    void prepareReport(Reportable reportable);
    void close();
}
