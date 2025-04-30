# Continuous Integration Repository

This repository contains the configuration and resources required to implement continuous integration pipelines for Java-based applications. Below is an overview of the contents and their purposes:

## Overview

The repository includes the following components:

1. **Tekton Pipelines**: A set of YAML files to define and manage CI/CD pipelines for Java applications.
2. **ArgoCD Application**: Configuration to deploy the pipelines using ArgoCD.

## Contents

### 1. `java-pipeline/`
This directory contains Tekton pipeline configurations for building and deploying Java applications. It includes the following files:

- **`pipeline.yaml`**: Defines the Tekton pipeline for building, packaging, and containerizing Java applications.
- **`trigger-template-java.yaml`**: A Tekton TriggerTemplate that generates PipelineRuns dynamically based on incoming events.
- **`trigger-binding-java.yaml`**: A Tekton TriggerBinding that maps incoming event payloads to parameters for the TriggerTemplate.
- **`trigger-lisener-java.yaml`**: An EventListener that listens for external events and triggers the pipeline using the defined TriggerTemplate and TriggerBinding.

### 2. `argo-cd-applications/`
This directory contains ArgoCD application definitions for managing the CI pipeline resources:

- **`application-pipeline-ci-java.yaml`**: Defines an ArgoCD application to manage the Tekton pipeline resources in the `java-pipeline/` directory.

## Purpose

The repository is designed to:

1. Automate the build and deployment process for Java applications using Tekton pipelines.
2. Integrate with ArgoCD for GitOps-based management of pipeline resources.
3. Provide a scalable and reusable CI pipeline setup for Java projects.

## Prerequisites

- A Kubernetes or OpenShift cluster with Tekton Pipelines and ArgoCD installed.
- Access to a container registry for storing built images.
- Properly configured service accounts and permissions for pipeline execution.

## Setup Instructions

### Configure Image Repository

- Create an image repository in Quay.io.
- Create Robot Accounts in Quay with write privileges to the new image repository.
- Create a Kubernetes secret `quay-pull-secret` with the Robot Account credentials.


### Deploy ArgoCD Application

Apply the ArgoCD application configuration to deploy the pipelines:

```bash
oc apply -f argo-cd-applications/application-pipeline-ci-java.yaml
```

### Configure Webhook

Create a webhook in the Git repository using the Event Listener Route. Set the content type to `application/json`.

## Tekton Pipeline Configuration

The Tekton pipeline is defined in `java-pipeline/pipeline.yaml`. It includes the following tasks:

1. **Git Clone**: Clones the source code from the specified Git repository.
2. **Package**: Builds the application using Maven.
3. **Build and Sign Image**: Builds a container image using Buildah and pushes it to the specified registry.

### Parameters

The pipeline accepts the following parameters:

- `APP_NAME`: The application deployment name.
- `SOURCE_GIT_URL`: The Git repository URL.
- `SOURCE_GIT_REVISION`: The Git repository revision.
- `SOURCE_GIT_CONTEXT_DIR`: The subdirectory in the Git repository.
- `IMAGE`: The image registry.
- `TAG`: The image tag (default: `latest`).
- `DOCKERFILE`: The Dockerfile path (default: `src/main/docker/Dockerfile.jvm`).

### Event Listener

The Event Listener is defined in `java-pipeline/trigger-lisener-java.yaml`. It listens for webhook events and triggers the pipeline. The associated TriggerTemplate and TriggerBinding are defined in `trigger-template-java.yaml` and `trigger-binding-java.yaml`, respectively.

## Conclusion

This repository provides a complete example of setting up CI/CD pipelines for Java applications using Tekton and ArgoCD. It includes all necessary configurations and a Helm chart for easy deployment. Customize the provided files as needed to fit your specific requirements.
