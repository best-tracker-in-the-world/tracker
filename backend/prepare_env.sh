#!/bin/bash
docker-compose down
# Define the directory for MySQL data
MYSQL_DATA_DIR="./mysql_data"


# Check if the directory exists
if [ ! -d "$MYSQL_DATA_DIR" ]; then
  echo "Creating MySQL data directory..."
else
  echo "MySQL data directory already exists."
  sudo rm -rf $MYSQL_DATA_DIR
fi

mkdir -p "$MYSQL_DATA_DIR"
# Set permissions and ownership
MYSQL_UID=0  # Example UID, replace with actual UID
MYSQL_GID=0  # Example GID, replace with actual GID

echo "Setting ownership and permissions for MySQL data directory..."
sudo chown -R $MYSQL_UID:$MYSQL_GID "$MYSQL_DATA_DIR"
sudo chmod -R 777 "$MYSQL_DATA_DIR"

echo "Environment preparation complete. You can now start Docker Compose."

# Optionally, start Docker Compose
docker-compose up -d
# Rostislove, please, use it to frontend tasks
# docker-compose -f docker-compose-web-local up -d