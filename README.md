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
3. One can add metadata to objects saved on MinIO. That metadata can be retrieved afterwards.
4. The listObjects method on the SDK cannot be used to retrieve versions and user metadata at the same time.
5. The owner of an object (`item.owner()`) is the key of the account that created the object, as stated [here](https://acloud.guru/forums/aws-certified-solutions-architect-associate/discussion/-KPST25Z-1SVLVN7bfDW/owner-of-a-s3-bucket-file-in-s3-bucket).
6. The StatObject method returns some interesting information related to an object such as the date it was created, its bucket, etc...
7. Bucket Lifecycle can be configured so that files under a folder, or older than a certain delete, may be deleted or stored somewhere else. The MinIO client on Java receives a config for the lifecycle as a xml. The documentation for object lifecycle management can be found [here](https://docs.aws.amazon.com/AmazonS3/latest/dev/object-lifecycle-mgmt.html).
8. [Notifications](https://docs.min.io/docs/minio-bucket-notification-guide.html) can be pushed to a PostgreSQL database, or Apache Kafka, on most of the operations available to objects in a bucket (PUT, POST, DELETE...).
9. Caching on MinIO can only be mounted on file systems that support `atime`, according to the [docs](https://github.com/minio/minio/blob/master/docs/disk-caching/DESIGN.md). That support can be mainly found in Unix. I was not able to implement and test caching.

