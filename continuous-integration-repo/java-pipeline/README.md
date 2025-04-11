# Pipelines bootstup


* Create namespace pipelines-ci.
Example:
```
apiVersion: v1
kind: Namespace
metadata:
  name: pipeline-ci
  labels:
     argocd.argoproj.io/managed-by: openshift-gitops
spec:
  finalizers:
  - kubernetes
```
* Creat image repositroy in Quay.
* Cread Robot Accounts in Quay wih write previleges to the new image repository.
* Creat sicret with Robot Accounts credentials.
* Add secret to 'pipeline' ServiceAccout
Example:
```
oc secrets link -n pipeline-ci pipeline quay-pull-secret
```
or
```
---
kind: ServiceAccount
apiVersion: v1
metadata:
  name: pipeline
  namespace: pipeline-ci
  annotations:
    argocd.argoproj.io/sync-options: ServerSideApply=true
secret:
  - name: quay-pull-secret
```
