# EHealth demo

A demo web app that demonstrates authentication with stateful data maintained in a database.

## Installation
### Windows setup
* Install 'Git Bash'
* Install 'choco' the package manager
* *TODO* document hack related to choco install
* Install Virtualbox
* Install minikube
* Install kubectl & helm using 'choco'
* Setup aliases in bashrc

### Starting app
```
# create a virtualenv
python2 -m virtualenv venv
# source your virtualenv
.\venv\Scripts\activate.ps1
# install requirements
python2 -m pip install requirements.txt
# run app
export DB_USERNAME='yourdbusername'
export DB_PASSWORD='yourdbpassword'
python2 ./new_ehealth/backend.py
```

### Deploy helm charts
* Start the apps using the following command
```
./kube/deploy/startup.sh
```

## Architecture
### Old architecture
![Architecture Old](readme/architecture-current.png)

### Future architecture
![Architecture Goal](readme/architecture.png)

## Troubleshooting
### Virtualbox networking issue (Windows)
* While running 'minikube start' if you see errors, like this
```
github.com/spf13/cobra.(*Command).ExecuteC
        /go/pkg/mod/github.com/spf13/cobra@v1.0.0/command.go:950
github.com/spf13/cobra.(*Command).Execute
        /go/pkg/mod/github.com/spf13/cobra@v1.0.0/command.go:887
k8s.io/minikube/cmd/minikube/cmd.Execute
        /app/cmd/minikube/cmd/root.go:106
main.main
        /app/cmd/minikube/main.go:71
runtime.main
        /usr/local/go/src/runtime/proc.go:203
runtime.goexit
        /usr/local/go/src/runtime/asm_amd64.s:1373 Advice:VirtualBox is unable to find its network interface. Try upgrading to the latest release and rebooting. URL: Issues:[6036] ShowIssueLink:false}
* [VBOX_INTERFACE_NOT_FOUND] error provisioning host Failed to start host: driver start: Unable to start the VM: D:\Softwares\Virtualbox\VBoxManage.exe startvm minikube --type headless failed:
VBoxManage.exe: error: Failed to open/create the internal network 'HostInterfaceNetworking-VirtualBox Host-Only Ethernet Adapter #2' (VERR_INTNET_FLT_IF_NOT_FOUND).
VBoxManage.exe: error: Failed to attach the network LUN (VERR_INTNET_FLT_IF_NOT_FOUND)
VBoxManage.exe: error: Details: code E_FAIL (0x80004005), component ConsoleWrap, interface IConsole

Details: 00:00:01.711952 Power up failed (vrc=VERR_INTNET_FLT_IF_NOT_FOUND, rc=E_FAIL (0X80004005))
* Suggestion: VirtualBox is unable to find its network interface. Try upgrading to the latest release and rebooting.
* Related issue: https://github.com/kubernetes/minikube/issues/6036
```
* This is probably because the Virtualbox network adapter might not be functioning correctly
* Search for 'View Network Connections' in the Start page and then find the Virtualbox adapter.
* Then, disable and enable the adapter and then 'minikube start' should work as usual

## TODO
* Document / experiment with dhcp lease deletion
```
https://github.com/kubernetes/minikube/issues/951
C:\Users\trump\.VirtualBox\HostInterfaceNetworking-VirtualBox Host-Only Ethernet Adapter-Dhcpd.leases
```

## License
[License](license.txt)