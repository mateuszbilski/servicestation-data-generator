package app.servicestation.datagenerator.endpoint;

import app.servicestation.datagenerator.logic.Reportable;
import app.servicestation.datagenerator.model.Identifier;
import io.vertx.core.json.Json;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleEndpoint implements Endpoint {
    @Override
    public void init() {

    }

    @Override
    public void prepareReport(Reportable reportable) {
        List<MeasureEntity> list = reportable.getMeasurableList().stream()
                .map(i -> new MeasureEntity(i.getIdentifier().getIdentifier(), i.getType(),
                        i.getMeasureTypes().stream().collect(Collectors.toMap(Identifier::getIdentifier, i::getMeasure))))
                .collect(Collectors.toList());
        System.out.println(Json.encode(list));
    }

    @Override
    public void close() {

    }

    static class MeasureEntity {
        private String identifier;
        private String type;
        private Map<String, Object> measureMap;

        public MeasureEntity(String identifier, String type, Map<String, Object> measureMap) {
            this.identifier = identifier;
            this.type = type;
            this.measureMap = measureMap;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getType() {
            return type;
        }

        public Map<String, Object> getMeasureMap() {
            return measureMap;
        }
    }
}
