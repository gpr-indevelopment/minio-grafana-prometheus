# minio-grafana-prometheus
Example of a setup with minio, grafana and prometheus inside a docker compose

## Setup

1. Start docker compose on the project folder with:

```
docker-compose up-d
```

2. MinIO will create a data directory inside the project folder. This directory will be used to physically store objects. Minio GUI will be available on localhost:9000. The credentials for MinIO can be found inside the docker compose configuration file, under its environment variables.
3. Prometheus will be available on localhost:9090.
4. Grafana will be available on localhost:3000.
5. The `grafana-dashboard.json` file can be imported to a Grafana dashboard. It is based on [this documentation](https://github.com/minio/minio/blob/master/docs/metrics/prometheus/grafana/README.md), and adapted to the compose scenario.