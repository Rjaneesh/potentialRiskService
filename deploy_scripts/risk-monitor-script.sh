#!/bin/bash
BASE_PATH=/home/ec2-user/riskmonitor
USER=ec2-user
GROUP=ec2-user
ENV=$DEPLOYMENT_GROUP_NAME
REGION=`curl -s http://169.254.169.254/latest/dynamic/instance-identity/document%7Cgrep region|awk -F\" '{print $4}'`
checkStatus() {
  if [ $? -eq 0 ]; then
    echo "[info] '$moduleName' done"
  else
    echo "[warn] '$moduleName' failed" && exit 1
  fi
}

updatePortalServie() {
  moduleName=$1
      echo "[info] '$moduleName' initiated..."
      pwd
      sudo cp -v $BASE_PATH/deploy_scripts/risk-monitor.service /etc/systemd/system/risk-monitor.service
      sudo kill -9 $(lsof -t -i:5000)
      sudo systemctl daemon-reload
      sudo systemctl enable risk-monitor.service
      sudo systemctl start risk-monitor.service
      checkStatus
}

updatePortalServie "risk-monitor.service started"
