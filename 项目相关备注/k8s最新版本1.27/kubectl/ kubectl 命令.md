

****

kubectl get - 列出资源
kubectl describe - 显示有关资源的详细信息
kubectl logs - 打印 pod 和其中容器的日志
kubectl exec - 在 pod 中的容器上执行命令



## 查询命名空间 ingreess-nginx的所有pod
kubectl get pods -n ingress-nginx
或者
kubectl get pods --namespace ingress-nginx



kubectl delete pod mynode-2559548524-266xp


####  集群初始化命令

kubeadm init --kubernetes-version=1.27.1 --apiserver-advertise-address=192.168.108.129 --image-repository registry.aliyuncs.com/google_containers --pod-network-cidr=10.244.0.0/16


kubeadm init \
--apiserver-advertise-address=192.168.108.134 \
--image-repository registry.aliyuncs.com/google_containers \
--kubernetes-version=v1.21.9 \
--service-cidr=10.96.0.0/16 \
--pod-network-cidr=10.224.0.0/16





kubeadm init \
--apiserver-advertise-address=192.168.19.100 \
--control-plane-endpoint=cluster-endpoint \
--image-repository registry.cn-hangzhou.aliyuncs.com/lfy_k8s_images \
--kubernetes-version v1.20.9 \
--service-cidr=10.96.0.0/16 \
--pod-network-cidr=192.169.0.0/16



### 集群初始化成功后需执行以下命令
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config



###  生成yaml文件内容
kubectl create deployment tomcat6 --image=tomcat:6.0.53-jre8 --dry-run=client -o yaml > tomcat6.yaml。

apiVersion: apps/v1
kind: Deployment
metadata:
creationTimestamp: null
labels:
app: tomcat6
name: tomcat6
spec:
replicas: 1
selector:
matchLabels:
app: tomcat6
strategy: {}
template:
metadata:
creationTimestamp: null
labels:
app: tomcat6
spec:
containers:
- image: tomcat:6.0.53-jre8
name: tomcat
resources: {}
status: {}

















# get 命令的基本输出
kubectl get services                          # 列出当前命名空间下的所有 services
kubectl get pods --all-namespaces             # 列出所有命名空间下的全部的 Pods
kubectl get pods -o wide                      # 列出当前命名空间下的全部 Pods，并显示更详细的信息
kubectl get deployment my-dep                 # 列出某个特定的 Deployment
kubectl get pods                              # 列出当前命名空间下的全部 Pods
kubectl get pod my-pod -o yaml                # 获取一个 pod 的 YAML
# describe 命令的详细输出
kubectl describe nodes my-node
kubectl describe pods my-pod
# 列出当前名字空间下所有 Services，按名称排序
kubectl get services --sort-by=.metadata.name
# 列出 Pods，按重启次数排序
kubectl get pods --sort-by='.status.containerStatuses[0].restartCount'
# 列举所有 PV 持久卷，按容量排序
kubectl get pv --sort-by=.spec.capacity.storage
# 获取包含 app=cassandra 标签的所有 Pods 的 version 标签
kubectl get pods --selector=app=cassandra -o \
jsonpath='{.items[*].metadata.labels.version}'
# 检索带有 “.” 键值，例： 'ca.crt'
kubectl get configmap myconfig \
-o jsonpath='{.data.ca\.crt}'
# 检索一个 base64 编码的值，其中的键名应该包含减号而不是下划线。
kubectl get secret my-secret --template='{{index .data "key-name-with-dashes"}}'
# 获取所有工作节点（使用选择器以排除标签名称为 'node-role.kubernetes.io/control-plane' 的结果）
kubectl get node --selector='!node-role.kubernetes.io/control-plane'
# 获取当前命名空间中正在运行的 Pods
kubectl get pods --field-selector=status.phase=Running
# 获取全部节点的 ExternalIP 地址
kubectl get nodes -o jsonpath='{.items[*].status.addresses[?(@.type=="ExternalIP")].address}'
# 列出属于某个特定 RC 的 Pods 的名称
# 在转换对于 jsonpath 过于复杂的场合，"jq" 命令很有用；可以在 https://stedolan.github.io/jq/ 找到它。
sel=${$(kubectl get rc my-rc --output=json | jq -j '.spec.selector | to_entries | .[] | "\(.key)=\(.value),"')%?}
echo $(kubectl get pods --selector=$sel --output=jsonpath={.items..metadata.name})
# 显示所有 Pods 的标签（或任何其他支持标签的 Kubernetes 对象）
kubectl get pods --show-labels
# 检查哪些节点处于就绪状态
JSONPATH='{range .items[*]}{@.metadata.name}:{range @.status.conditions[*]}{@.type}={@.status};{end}{end}' \
&& kubectl get nodes -o jsonpath="$JSONPATH" | grep "Ready=True"
# 不使用外部工具来输出解码后的 Secret
kubectl get secret my-secret -o go-template='{{range $k,$v := .data}}{{"### "}}{{$k}}{{"\n"}}{{$v|base64decode}}{{"\n\n"}}{{end}}'
# 列出被一个 Pod 使用的全部 Secret
kubectl get pods -o json | jq '.items[].spec.containers[].env[]?.valueFrom.secretKeyRef.name' | grep -v null | sort | uniq
# 列举所有 Pods 中初始化容器的容器 ID（containerID）
# 可用于在清理已停止的容器时避免删除初始化容器
kubectl get pods --all-namespaces -o jsonpath='{range .items[*].status.initContainerStatuses[*]}{.containerID}{"\n"}{end}' | cut -d/ -f3
# 列出事件（Events），按时间戳排序
kubectl get events --sort-by=.metadata.creationTimestamp
# 列出所有警告事件
kubectl events --types=Warning
# 比较当前的集群状态和假定某清单被应用之后的集群状态
kubectl diff -f ./my-manifest.yaml
# 生成一个句点分隔的树，其中包含为节点返回的所有键
# 在复杂的嵌套JSON结构中定位键时非常有用
kubectl get nodes -o json | jq -c 'paths|join(".")'
# 生成一个句点分隔的树，其中包含为pod等返回的所有键
kubectl get pods -o json | jq -c 'paths|join(".")'
# 假设你的 Pods 有默认的容器和默认的名字空间，并且支持 'env' 命令，可以使用以下脚本为所有 Pods 生成 ENV 变量。
# 该脚本也可用于在所有的 Pods 里运行任何受支持的命令，而不仅仅是 'env'。
for pod in $(kubectl get po --output=jsonpath={.items..metadata.name}); do echo $pod && kubectl exec -it $pod -- env; done
# 获取一个 Deployment 的 status 子资源
kubectl get deployment nginx-deployment --subresource=status













## 当前api支持的资源
kubectl api-resources
## 获取所有已安装的资源
Kubectl get all
##测试开始
## 1.部署一个tomcat
kubectl create deployment tomcat6 --image=tomcat:6.0.53-jre8
## 获取所有的pods详细信息
kubectl get pods -o wide
## 获取所有的pods名称空间信息
kubectl get pods --all-namespaces
## 2.暴露 tomcat6 对外访问
## Pod 的 80 映射容器的 8080；service 会代理 Pod 的 80 由于这么没有指定service的端口，所以会随机生成一个端口
## --type=NodePort 会生成一个对外的service 暴露给外部访问，一个service可以包含多个pod ，一个pod中可以有多个容器
kubectl expose deployment tomcat6 --port=80 --target-port=8080 --type=NodePort
## 查看生成的service
kubectl get service
## 3.动态扩容测试，扩容了多份，所有无论访问哪个 node 的指定端口，都可以访问到 tomcat6
### 查看需要扩容的deployment
kubectl get deployment
### 扩容
kubectl scale --replicas=3 deployment tomcat6
### 查看扩容后的结果
kubectl get pods -o wide
## 部署三份
kubectl scale --replicas=3 deployment tomcat6
## 4.以上操作的 yaml 获取
## 5、删除
Kubectl get all
kubectl delete deploy/nginx
kubectl delete service/nginx-service

