


### 1  kubectl 展示搜索出的pod列表（含pod所在的namespace）

kubectl get pod -A grep <podname>

如：  kubectl get pod -A |grep ingress


####  kubectl 删除pod命令

  kubectl delete pod <podname> -n <namespace>


在进行删除pod命令时，会发现pod并未被真正删除，原因是k8s误认为我们要删除的pod异常挂了，会启用容灾机制，导致重新在拉起一个新的pod。
故，我们想要正常且彻底的删除一个pod，必须要先破坏掉他的容灾机制，即删除deployment机制。


#### 查看deployment信息

  kubectl get deployment -n <namespace>

### 删除deployment配置

kubectl delete deployment <deployment名> -n <namespace>


### 然后进行删除pod命令即可，我删除deployment后，再次查询pod发现，上面的pod已经开始自行删除了（这步可酌情处理）
