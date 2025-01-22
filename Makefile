.PHONY: up down restart logs clean

up:
	docker compose up -d

down:
	docker compose down --remove-orphans

restart:
	docker compose down --remove-orphans
	docker compose up -d

logs:
	docker compose logs -f

clean:
	docker compose down -v --remove-orphans