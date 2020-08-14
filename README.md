# minio-grafana-prometheus
Example of a setup with minio, grafana and prometheus inside a docker compose

## Prerequisites

This project requires you to have Docker and Docker compose installed. Nothing else.

## Setup

1. Start docker compose on the project folder with:

```
docker-compose up -d
```

2. MinIO will create a data directory inside the project folder. This directory will be used to physically store objects. Minio GUI will be available on localhost:9000. The credentials for MinIO can be found inside the docker compose configuration file, under its environment variables.
3. Prometheus will be available on localhost:9090.
4. Grafana will be available on localhost:3000.
5. The `grafana-dashboard.json` file can be imported to a Grafana dashboard. It is based on [this documentation](https://github.com/minio/minio/blob/master/docs/metrics/prometheus/grafana/README.md), and adapted to the compose scenario.

### Troubleshooting

1. Prometheus may not start correctly alongside the compose. Sometimes it fais with an error informing `prometheus.yml` file doesn't exist. Restarting its container a couple of times should solve the issue.

### Notes

1. Bucket versioning, according to [the docs](https://docs.min.io/docs/minio-bucket-versioning-guide.html), can only be applied through the MinIO SDK.
2. Also, bucket versioning only works with the [erasure code setup](https://docs.min.io/docs/minio-erasure-code-quickstart-guide.html). Please check `docker-compose.yml` configuration.
