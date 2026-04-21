FROM jenkins/jenkins:lts

USER root

# Install Docker CLI inside Jenkins
RUN apt-get update && apt-get install -y docker.io

# Allow Jenkins to run docker
RUN usermod -aG docker jenkins

USER jenkins