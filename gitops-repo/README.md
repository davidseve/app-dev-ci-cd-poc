# GitOps Repository

This repository is structured to manage the deployment of applications across multiple clusters and environments using GitOps principles. Below is an explanation of the main folders and their purposes, along with examples of their content:

## Folder Structure

### `apps`
This folder contains the list of all applications to be deployed, along with their configurations for each cluster and environment. Each application has a `chart-config.json` file that specifies the version of the Helm chart to deploy for each cluster and environment.

#### Example:
For the application `java-example` in the `cluster1` environment:
- `apps/java-example/cluster1/pre/chart-config.json`:
```json
{
    "chartPath": "helm-chart-java-repo",
    "repoURL": "https://github.com/davidseve/app-dev-ci-cd-poc.git",
    "targetRevision": "main"
}
```
- `apps/java-example/cluster1/dev/chart-config.json`:
```json
{
    "chartPath": "helm-chart-java-repo",
    "repoURL": "https://github.com/davidseve/app-dev-ci-cd-poc.git",
    "targetRevision": "main"
}
```

### `environments`
This folder contains the list of all environments where applications will be deployed. It includes specific configurations for each environment that are shared across all applications.

#### Example:
- `environments/dev/values.yaml`: Contains shared configuration values for the `dev` environment.
- `environments/pre/values.yaml`: Contains shared configuration values for the `pre` environment.

### `clusters`
This folder contains the list of all clusters where applications will be deployed. It includes specific configurations for each environment and cluster, shared across all applications.

#### Example:
- `clusters/cluster1/values.yaml`: Contains shared configuration values for `cluster1`.
- `clusters/cluster2/values.yaml`: Contains shared configuration values for `cluster2`.
- `clusters/cluster1/dev/values.yaml`: Contains shared configuration values for `cluster1` environment `dev`.
- `clusters/cluster2/dev/values.yaml`: Contains shared configuration values for `cluster2` environment `dev`.

### `argo-cd-applications`
This folder contains the ApplicationSet configuration for Argo CD. It uses the placement from ACM (Advanced Cluster Management) to retrieve the list of clusters and iterates over each application to generate an Argo CD application for each application/environment/cluster combination with the correct configuration defined in the values files.

#### Example:
- `argo-cd-applications/bootstrap-application.yaml`:
```yaml
apiVersion: argoproj.io/v1alpha1
kind: ApplicationSet
metadata:
 name: aso-bootstrap-multicluster
 namespace: openshift-gitops
spec:
  goTemplate: true
  generators:
    - matrix:
        generators: 
        - clusterDecisionResource:
            configMapRef: acm-placement
            labelSelector:
              matchLabels:
                cluster.open-cluster-management.io/placement: sescam-placement
            requeueAfterSeconds: 30                                  
        - git:
            repoURL: https://github.com/davidseve/app-dev-ci-cd-poc.git
            revision: HEAD
            files:
            - path: gitops-repo/apps/**/{{.name}}/**/chart-config.json
            pathParamPrefix: app   
  template:
    metadata:
      annotations:
        apps.open-cluster-management.io/ocm-managed-cluster: "{{.name}}"
        apps.open-cluster-management.io/ocm-managed-cluster-app-namespace: "openshift-gitops"
        argocd.argoproj.io/skip-reconcile: "true"
      labels:
        velero.io/exclude-from-backup: "true"
        apps.open-cluster-management.io/pull-to-ocm-managed-cluster: "true"
      name: '{{index .app.path.segments 2}}-{{index .app.path.segments 4}}-{{.name}}'
    spec:
      project: default
      sources:
      - path: '{{.chartPath}}'
        repoURL: '{{.repoURL}}'
        targetRevision: '{{.targetRevision}}'
        helm:
          valueFiles:
            - $values/gitops-repo/clusters/{{.name}}/values.yaml
            - $values/gitops-repo/environments/{{index .app.path.segments 4}}/values.yaml          
            - $values/gitops-repo/clusters/{{.name}}/{{index .app.path.segments 4}}/values.yaml
            - $values/gitops-repo/apps/{{index .app.path.segments 2}}/values.yaml            
            - $values/gitops-repo/apps/{{index .app.path.segments 2}}/{{.name}}/values.yaml
            - $values/gitops-repo/apps/{{index .app.path.segments 2}}/{{.name}}/{{index .app.path.segments 4}}/values.yaml          
      - ref: values
        repoURL: 'https://github.com/davidseve/app-dev-ci-cd-poc.git'
        targetRevision: HEAD
      destination:
        server: '{{.server}}'
        namespace: '{{index .app.path.segments 4}}'
      syncPolicy:
        automated:
          prune: true
          selfHeal: true
```
