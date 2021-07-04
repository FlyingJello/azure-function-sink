## Pulsar IO :: Azure Function Sink

This [pulsar connector](http://pulsar.apache.org/docs/en/next/io-overview/) is able to redirect pulsar messages  to an azure function.

### Configuration

| Name | Description | Required? |
| --- | --- | --- |
| functionUrl | The azure function url including the function name | Yes |
| functionKey | The function key to access the azure function | Yes |

### Setup

1. Create an azure function app with a HTTP post trigger.
2. Configure and deploy the pulsar sink.

### License

[Apache License](https://www.apache.org/licenses/LICENSE-2.0)
