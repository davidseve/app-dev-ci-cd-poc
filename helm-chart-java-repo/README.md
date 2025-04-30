# Helm Chart for Java Applications

This repository contains a Helm chart designed for deploying Java-based applications on Kubernetes. It provides a flexible and customizable way to manage your application's deployment, scaling, and configuration.

## Features

- **Service Configuration**: Define the type and port of the service.
- **Router Support**: Enable or disable OpenShift routes.
- **Replica Management**: Configure the number of replicas for your application.
- **Image Configuration**: Set the container image repository, tag, and pull policy.
- **Resource Management**: Optionally define resource limits and requests.
- **Autoscaling**: Enable Horizontal Pod Autoscaler (HPA) with customizable thresholds.
- **Pod Customization**: Add annotations, labels, and security contexts to pods.
- **Node Affinity and Tolerations**: Define node selection and toleration rules.

## Files Overview

### `Chart.yaml`
Defines the metadata for the Helm chart, including its name, description, version, and application version.

### `values.yaml`
Contains the default configuration values for the chart. These values can be overridden during deployment. Key sections include:

- `log.level`: Set the logging level (e.g., INFO, DEBUG).
- `service`: Configure the service type and port.
- `router.enabled`: Enable or disable OpenShift routes.
- `replicaCount`: Set the number of replicas.
- `image`: Define the container image repository, tag, and pull policy.
- `resources`: Optionally specify resource limits and requests.
- `autoscaling`: Configure HPA settings.
- `nodeSelector`, `tolerations`, `affinity`: Define node scheduling rules.

### Templates

- `deployment.yaml`: Defines the Deployment resource for the application.
- `service.yaml`: Configures the Service resource.
- `router.yaml`: Configures an OpenShift Route if enabled.
- `configmap-env.yaml`: Creates a ConfigMap for environment variables.
- `_helpers.tpl`: Contains helper templates for generating names and labels.

### `.helmignore`
Specifies files and directories to ignore when packaging the chart.

## Usage

### Prerequisites

- Kubernetes cluster
- Helm CLI installed

### Installation

1. Clone this repository:
   ```bash
   git clone <repository-url>
   cd helm-chart-java-repo
   ```

2. Install the chart using Helm:
   ```bash
   helm install <release-name> .
   ```

### Customization

Override default values by creating a `values.yaml` file or using the `--set` flag. For example:

```bash
helm install <release-name> . \
  --set replicaCount=3 \
  --set image.tag="1.0.0" \
  --set service.type=LoadBalancer
```

### Uninstallation

To uninstall the chart:

```bash
helm uninstall <release-name>
```

### Notes

- Ensure that the `image.repository` points to a valid container image.
- If using a private image repository, configure `imagePullSecrets`.
- For OpenShift users, set `router.enabled=true` to expose the application via a Route.

