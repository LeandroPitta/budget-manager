# Use the latest version of Ubuntu
FROM ubuntu:latest

# Install basic dependencies and Java 17
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    gnupg2 \
    software-properties-common \
    unzip \
    git \
    openssh-server \
    && rm -rf /var/lib/apt/lists/*

# Install JDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Install Maven (latest version)
RUN apt-get install -y maven

# Install Node.js 18 and npm
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs

# Install Angular CLI (version 16)
RUN npm install -g @angular/cli@16

# Configure JAVA_HOME
ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# SSH Setup
RUN mkdir /var/run/sshd \
    && sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config \
    && sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config \
    && sed -i 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' /etc/pam.d/sshd \
    && echo 'root:root' | chpasswd \
    && echo 'root ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers \
    && sed -i 's/^.*PermitEmptyPasswords.*$/PermitEmptyPasswords yes/' /etc/ssh/sshd_config \
    && passwd -d root  # Remove root password

EXPOSE 22 8080 4200

# Start SSH daemon
CMD ["/usr/sbin/sshd", "-D"]
