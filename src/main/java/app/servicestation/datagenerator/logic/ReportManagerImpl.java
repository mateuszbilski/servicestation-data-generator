package app.servicestation.datagenerator.logic;

import app.servicestation.datagenerator.endpoint.Endpoint;

import java.util.ArrayList;
import java.util.List;

public class ReportManagerImpl implements ReportManager {

    private List<Endpoint> endpointList = new ArrayList<>();

    @Override
    public void addEndpoint(Endpoint endpoint) {
        endpointList.add(endpoint);
        endpoint.init();
    }

    @Override
    public void removeEndpoint(Endpoint endpoint) {
        endpointList.remove(endpoint);
        endpoint.close();
    }

    @Override
    public void prepareReport(Reportable reportable) {
        endpointList.stream().forEach(i -> i.prepareReport(reportable));
    }

    @Override
    public void removeAll() {
        endpointList.stream().forEach(i -> i.close());
        endpointList.clear();
    }

    @Override
    public List<Endpoint> getEndpointList() {
        return endpointList;
    }
}
