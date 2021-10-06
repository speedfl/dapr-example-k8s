# dapr-producer Project

The dapr producer project.
It uses [k3d](https://k3d.io/) for local development

## Lifecycle

You can use the `./lifecycle.sh <command>` to work on local

The intersting commands:

- `build`: Build the application
- `deploy`: Deploy to local k3d cluster
- `redeploy`: Reddeploy to local k3d cluster
- `undeploy`: Undeploy from local k3d cluster

## Shoot a request:

```bash
curl --location --request POST 'http://order.127.0.0.1.nip.io/order' \
--header 'Content-Type: application/json' \
--data-raw '{
    "total": {
        "amount": 1000,
        "currency": "EUR"
    }
}'
```