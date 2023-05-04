# Use an official MySQL runtime as the base image
FROM mysql:latest

# Set the environment variables for the MySQL database
ENV MYSQL_DATABASE=HealthCare
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=password

# Copy the SQL script to initialize the database
# COPY init.sql /docker-entrypoint-initdb.d/

# Expose the default MySQL port
EXPOSE 3306
