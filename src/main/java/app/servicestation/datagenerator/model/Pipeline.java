package app.servicestation.datagenerator.model;

public class Pipeline {

    private Tank tank;
    private Nozzle nozzle;
    private Double throughputCapacity;
    private PipelineFactor pipelineFactor;

    public Pipeline() {
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public Nozzle getNozzle() {
        return nozzle;
    }

    public void setNozzle(Nozzle nozzle) {
        this.nozzle = nozzle;
    }

    public Double getThroughputCapacity() {
        return throughputCapacity;
    }

    public void setThroughputCapacity(Double throughputCapacity) {
        this.throughputCapacity = throughputCapacity;
    }

    public PipelineFactor getPipelineFactor() {
        return pipelineFactor;
    }

    public void setPipelineFactor(PipelineFactor pipelineFactor) {
        this.pipelineFactor = pipelineFactor;
    }

    public static class PipelineFactor {
        private Double temperatureFactor;

        public PipelineFactor(Double temperatureFactor) {
            this.temperatureFactor = temperatureFactor;
        }

        public Double getTemperatureFactor() {
            return temperatureFactor;
        }

    }


    public static final class Builder {
        private Tank tank;
        private Nozzle nozzle;
        private Double throughputCapacity;
        private PipelineFactor pipelineFactor;

        private Builder() {
        }

        public static Builder aPipeline() {
            return new Builder();
        }

        public Builder withTank(Tank tank) {
            this.tank = tank;
            return this;
        }

        public Builder withNozzle(Nozzle nozzle) {
            this.nozzle = nozzle;
            return this;
        }

        public Builder withThroughputCapacity(Double throughputCapacity) {
            this.throughputCapacity = throughputCapacity;
            return this;
        }

        public Builder withPipelineFactor(PipelineFactor pipelineFactor) {
            this.pipelineFactor = pipelineFactor;
            return this;
        }

        public Pipeline build() {
            Pipeline pipeline = new Pipeline();
            pipeline.setTank(tank);
            pipeline.setNozzle(nozzle);
            pipeline.setThroughputCapacity(throughputCapacity);
            pipeline.setPipelineFactor(pipelineFactor);
            return pipeline;
        }
    }
}
