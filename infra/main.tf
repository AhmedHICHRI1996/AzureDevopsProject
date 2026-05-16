terraform {
  required_version = ">= 1.0"

  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.0"
    }
  }
}

provider "azurerm" {
  features {}
}

variable "resource_group_name" {
  description = "Nom du groupe de ressources Azure"
  type        = string
  default     = "rg-musee-virtuel-signalement"
}

variable "location" {
  description = "Région Azure pour les ressources"
  type        = string
  default     = "West Europe"
}

variable "app_name" {
  description = "Nom de l'Azure Web App"
  type        = string
  default     = "musee-virtuel-signalement"
}

variable "acr_name" {
  description = "Nom de l'Azure Container Registry"
  type        = string
  default     = "acrmuseevirtuel"
}

variable "app_service_plan_name" {
  description = "Nom du plan App Service"
  type        = string
  default     = "asp-musee-virtuel-signalement"
}

variable "db_connection_string" {
  description = "Chaîne de connexion de la base de données (optionnel)"
  type        = string
  default     = ""
}

variable "db_username" {
  description = "Utilisateur de la base de données (optionnel)"
  type        = string
  default     = ""
}

variable "db_password" {
  description = "Mot de passe de la base de données (optionnel)"
  type        = string
  default     = ""
}

locals {
  webapp_db_settings = {
    for key, value in {
      SPRING_DATASOURCE_URL      = var.db_connection_string
      SPRING_DATASOURCE_USERNAME = var.db_username
      SPRING_DATASOURCE_PASSWORD = var.db_password
    } : key => value if value != ""
  }
}

resource "azurerm_resource_group" "rg" {
  name     = var.resource_group_name
  location = var.location
}

resource "azurerm_container_registry" "acr" {
  name                = var.acr_name
  resource_group_name = azurerm_resource_group.rg.name
  location            = azurerm_resource_group.rg.location
  sku                 = "Basic"
  admin_enabled       = true
}

resource "azurerm_app_service_plan" "asp" {
  name                = var.app_service_plan_name
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  kind                = "Linux"
  reserved            = true

  sku {
    tier = "Standard"
    size = "S1"
  }
}

resource "azurerm_linux_web_app" "webapp" {
  name                = var.app_name
  resource_group_name = azurerm_resource_group.rg.name
  location            = azurerm_resource_group.rg.location
  service_plan_id     = azurerm_app_service_plan.asp.id

  app_settings = merge(
    {
      "WEBSITES_PORT"                 = "8080"
      "DOCKER_REGISTRY_SERVER_URL"    = "https://${azurerm_container_registry.acr.login_server}"
      "DOCKER_REGISTRY_SERVER_USERNAME" = azurerm_container_registry.acr.admin_username
      "DOCKER_REGISTRY_SERVER_PASSWORD" = azurerm_container_registry.acr.admin_password
    },
    local.webapp_db_settings
  )
}

output "resource_group_name" {
  description = "Nom du groupe de ressources créé"
  value       = azurerm_resource_group.rg.name
}

output "container_registry_login_server" {
  description = "URL de connexion ACR"
  value       = azurerm_container_registry.acr.login_server
}

output "web_app_name" {
  description = "Nom de l'App Service Linux"
  value       = azurerm_linux_web_app.webapp.name
}
