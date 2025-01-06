# Use the latest version of Ubuntu
FROM ubuntu:latest

# Install basic dependencies, Java 17, Maven, Node.js, and SSH
RUN apt-get update && apt-get install -y \
    curl wget gnupg2 software-properties-common unzip git openssh-server \
    openjdk-17-jdk maven \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && npm install -g @angular/cli@16 \
    && rm -rf /var/lib/apt/lists/*

# Configure JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# SSH configuration (PermitEmptyPasswords enabled)
RUN mkdir /var/run/sshd \
    && sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config \
    && sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config \
    && sed -i 's/^.*PermitEmptyPasswords.*$/PermitEmptyPasswords yes/' /etc/ssh/sshd_config \
    && passwd -d root  # Remove the root password

EXPOSE 22 8080 4200

# Start SSH when the container is run
CMD ["/usr/sbin/sshd", "-D"]
