FROM tutum/lamp:latest
# Add volumes for MySQL 
VOLUME  ["/etc/mysql", "/var/lib/mysql" ]

EXPOSE 80 3306
RUN apt-get update && apt-get install -y rsync
CMD ["/run.sh"]
