package app.servicestation.datagenerator.logic;

import app.servicestation.datagenerator.endpoint.Endpoint;

import java.util.List;

public interface ReportManager {

    void addEndpoint(Endpoint endpoint);
    void removeEndpoint(Endpoint endpoint);
    void prepareReport(Reportable reportable);
    void removeAll();
    List<Endpoint> getEndpointList();
}
